package org.usfirst.frc.team321.robot.subsystems;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Arrays;

public class Camera extends Subsystem {

    // Serial Port Constants 
    private static final int BAUD_RATE = 115200;

    // MJPG Streaming Constants 
    private static final int MJPG_STREAM_PORT = 1180;

    // Packet format constants a
    private static final String PACKET_START_CHAR = "{";
    private static final String PACKET_END_CHAR = "}";
    private static final String PACKET_DILEM_CHAR = ",";
    private static final int PACKET_NUM_EXPECTED_FIELDS = 3;
    // When not streaming, use this mapping
    private static final int NO_STREAM_MAPPING = 0;
    // When streaming, use this set of configuration
    private static final int STREAM_WIDTH_PX = 352;
    private static final int STREAM_HEIGHT_PX = 288;
    private static final int STREAM_RATE_FPS = 15;
    //To run the background updater
    Notifier updater;
    // Configure the camera to stream debug images or not.
    private boolean broadcastUSBCam = false;
    // Serial port used for getting target data from JeVois
    private SerialPort visionPort = null;
    // USBCam and server used for broadcasting a web stream of what is seen
    private UsbCamera visionCam = null;
    private MjpegServer camServer = null;
    // Status variables
    private boolean dataStreamRunning = false;
    private boolean camStreamRunning = false;
    private boolean visionOnline = false;
    private double frameNumber = 0;

    // Most recently seen target information
    private boolean tgtVisible = false;
    private double tgtAngleDeg = 0;

    //=======================================================
    //== BEGIN PUBLIC INTERFACE
    //=======================================================
    //Persistent but "local" variables for getBytesPeriodic()
    private String getBytesWork = "";
    private int loopCount = 0;
    // buffer to contain data from the port while we gather full packets
    private StringBuffer packetBuffer = new StringBuffer(100);

    /**
     * Constructor (simple). Opens a USB serial port to the JeVois camera, sends a few test commands checking for error,
     * then fires up the user's program and begins listening for target info packets in the background
     */
    public Camera() {
        this(false); //Default - stream disabled, just run serial.
    }

    /**
     * Constructor (more complex). Opens a USB serial port to the JeVois camera, sends a few test commands checking for error,
     * then fires up the user's program and begins listening for target info packets in the background.
     * Pass TRUE to additionaly enable a USB camera stream of what the vision camera is seeing.
     */
    public Camera(boolean useUSBStream) {
    	
    	Thread packetListenerThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                backgroundUpdate();
            }
        });

        //Runs updater every 0.025 seconds which is perfect because that's how fast CAN bus will update
        updater = new Notifier(packetListenerThread);
    	
        int retry_counter = 0;

        //Retry strategy to get this serial port open.
        //I have yet to see a single retry used assuming the camera is plugged in
        // but you never know.
        while (visionPort == null && retry_counter++ < 10) {
            try {
                System.out.print("Creating JeVois SerialPort...");
                visionPort = new SerialPort(BAUD_RATE, SerialPort.Port.kUSB);
                System.out.println("SUCCESS!!");
            } catch (Exception e) {
                System.out.println("FAILED!!");
                e.printStackTrace();
                sleep(500);
                System.out.println("Retry " + Integer.toString(retry_counter));

                return;
            }
        }

        //Report an error if we didn't get to open the serial port
        if (visionPort == null) {
            DriverStation.reportError("Cannot open serial port to JeVois. Not starting vision system.", false);
            return;
        }

        //Test to make sure we are actually talking to the JeVois
        if (sendPing() != 0) {
            DriverStation.reportError("JeVois ping test failed. Not starting vision system.", false);
            return;
        }

        //Ensure the JeVois is starting with the stream off.
        sendCmdAndCheck("setmapping " + Integer.toString(NO_STREAM_MAPPING));
        stopDataOnlyStream();

        setCameraStreamActive(useUSBStream);
        start();
    }

    public void start() {
        if (broadcastUSBCam) {
            //Start streaming the JeVois via webcam
            //This auto-starts the serial stream
            startCameraStream();
        } else {
            startDataOnlyStream();
        }

        updater.startPeriodic(0.025);
    }

    //Main getters/setters

    public void stop() {
        if (broadcastUSBCam) {
            //Start streaming the JeVois via webcam
            //This auto-starts the serial stream
            stopCameraStream();
        } else {
            stopDataOnlyStream();
        }

        updater.stop();
    }

    /**
     * Send commands to the JeVois to configure it for image-processing friendly parameters
     */
    public void setCamVisionProcMode() {
        if (visionPort != null) {
            sendCmd("setcam autoexp 1"); //Disable auto exposure
            sendCmd("setcam absexp 75"); //Force exposure to a low value for vision processing
        }
    }

    /**
     * Send parameters to the camera to configure it for a human-readable image
     */
    public void setCamHumanDriverMode() {
        if (visionPort != null) {
            sendCmd("setcam autoexp 0"); //Enable AutoExposure
        }
    }

    /**
     * Set to true to enable the camera stream, or set to false to stream serial-packets only.
     * Note this cannot be changed at runtime due to jevois constraints. You must stop whatatever processing
     * is going on first.
     */
    public void setCameraStreamActive(boolean active) {
        if (!dataStreamRunning) {
            broadcastUSBCam = active;
        } else {
            DriverStation.reportError("Attempt to change cal stream mode while JeVois is still running. This is disallowed.", false);
        }
    }

    /**
     * Returns the most recently seen target's angle relative to the camera in degrees
     * Positive means to the right of center, negative means to the left
     */
    public double getTgtAngle_Deg() {
        return tgtAngleDeg;
    }

    //=======================================================
    //== END PUBLIC INTERFACE
    //=======================================================

    /**
     * Returns true when the roboRIO is receiving packets from the JeVois, false if no packets have been received.
     * Other modules should not use the vision processing results if this returns false.
     */
    public boolean isVisionOnline() {
        return visionOnline;
    }

    /**
     * Returns true when the JeVois sees a target and is tracking it, false otherwise.
     */
    public boolean isTgtVisible() {
        return tgtVisible;
    }

    /**
     * Returns current camera frame
     */
    public double getFrameNumber() {
        return frameNumber;
    }

    /**
     * This is the main perodic update function for the Listener. It is intended
     * to be run in a background task, as it will block until it gets packets.
     */
    private void backgroundUpdate() {
        // Grab packets and parse them.
        String packet = blockAndGetPacket(2.0);

        if (packet != null) {
            visionOnline = parsePacket(packet) == 0;
        } else {
            visionOnline = false;
            DriverStation.reportWarning("Cannot get packet from JeVois Vision Processor", false);
        }
    }

    /**
     * Send the ping command to the JeVois to verify it is connected
     *
     * @return 0 on success, -1 on unexpected response, -2 on timeout
     */
    private int sendPing() {
        int retval = -1;
        if (visionPort != null) {
            retval = sendCmdAndCheck("ping");
        }
        return retval;
    }

    private void startDataOnlyStream() {
        //Send serial commands to start the streaming of target info
        sendCmdAndCheck("setmapping " + Integer.toString(NO_STREAM_MAPPING));
        sendCmdAndCheck("streamon");
        dataStreamRunning = true;
    }

    private void stopDataOnlyStream() {
        //Send serial commands to stop the streaming of target info
        sendCmdAndCheck("streamoff");
        dataStreamRunning = false;
    }

    /**
     * Open an Mjpeg streamer from the JeVois camera
     */
    private void startCameraStream() {
        try {
            System.out.print("Starting JeVois Cam Stream...");
            visionCam = new UsbCamera("VisionProcCam", 0);
            visionCam.setVideoMode(PixelFormat.kBGR, STREAM_WIDTH_PX, STREAM_HEIGHT_PX, STREAM_RATE_FPS);
            camServer = new MjpegServer("VisionCamServer", MJPG_STREAM_PORT);
            camServer.setSource(visionCam);
            camStreamRunning = true;
            dataStreamRunning = true;
            System.out.println("SUCCESS!!");
        } catch (Exception e) {
            DriverStation.reportError("Cannot start camera stream from JeVois", false);
            e.printStackTrace();
        }
    }

    /**
     * Cease the operation of the camera stream. Unknown if needed.
     */
    private void stopCameraStream() {
        if (camStreamRunning) {
            camServer.free();
            visionCam.free();
            camStreamRunning = false;
            dataStreamRunning = false;
        }
    }

    /**
     * Sends a command over serial to JeVois and returns immediately.
     *
     * @param cmd String of the command to send (ex: "ping")
     * @return number of bytes written
     */
    private int sendCmd(String cmd) {
        int bytes;
        bytes = visionPort.writeString(cmd + "\n");
        System.out.println("wrote " + bytes + "/" + (cmd.length() + 1) + " bytes, cmd: " + cmd);
        return bytes;
    }

    /**
     * Sends a command over serial to the JeVois, waits for a response, and checks that response
     * Automatically ends the line termination character.
     *
     * @param cmd String of the command to send (ex: "ping")
     * @return 0 if OK detected, -1 if ERR detected, -2 if timeout waiting for response
     */
    public int sendCmdAndCheck(String cmd) {
        int retval = 0;
        sendCmd(cmd);
        retval = blockAndCheckForOK(1.0);
        if (retval == -1) {
            System.out.println(cmd + " Produced an error");
        } else if (retval == -2) {
            System.out.println(cmd + " timed out");
        }
        return retval;
    }

    /**
     * Read bytes from the serial port in a non-blocking fashion
     * Will return the whole thing once the first "OK" or "ERR" is seen in the stream.
     * Returns null if no string read back yet.
     */
    private String getCmdResponseNonBlock() {
        String retval = null;
        if (visionPort != null) {
            if (visionPort.getBytesReceived() > 0) {
                String rxString = visionPort.readString();
                System.out.println("Waited: " + loopCount + " loops, Rcv'd: " + rxString);
                getBytesWork += rxString;
                if (getBytesWork.contains("OK") || getBytesWork.contains("ERR")) {
                    retval = getBytesWork;
                    getBytesWork = "";
                    System.out.println(retval);
                }
                loopCount = 0;
            } else {
                ++loopCount;
            }
        }
        return retval;
    }

    /**
     * Blocks thread execution till we get a response from the serial line
     * or timeout.
     * Return values:
     * 0 = OK in response
     * -1 = ERR in response
     * -2 = No token found before timeout_s
     */
    private int blockAndCheckForOK(double timeout_s) {
        int retval = -2;
        double startTime = Timer.getFPGATimestamp();
        StringBuilder testStr = new StringBuilder();
        if (visionPort != null) {
            while (Timer.getFPGATimestamp() - startTime < timeout_s) {
                if (visionPort.getBytesReceived() > 0) {
                    testStr.append(visionPort.readString());
                    if (testStr.toString().contains("OK")) {
                        retval = 0;
                        break;
                    } else if (testStr.toString().contains("ERR")) {
                        DriverStation.reportError("JeVois reported error:\n" + testStr, false);
                        retval = -1;
                        break;
                    }
                } else {
                    sleep(10);
                }
            }
        }
        return retval;
    }

    /**
     * Blocks thread execution till we get a valid packet from the serial line
     * or timeout.
     * Return values:
     * String = the packet
     * null = No full packet found before timeout_s
     */
    private String blockAndGetPacket(double timeout_s) {
        String retval = null;
        double startTime = Timer.getFPGATimestamp();
        int endIdx = -1;
        int startIdx = -1;

        if (visionPort != null) {

            sendCmd("target");

            while (Timer.getFPGATimestamp() - startTime < timeout_s) {
                // Keep trying to get bytes from the serial port until the timeout expires.
                if (visionPort.getBytesReceived() > 0) {
                    // If there are any bytes available, read them in and 
                    //  append them to the buffer.
                    packetBuffer = packetBuffer.append(visionPort.readString());

                    // Attempt to detect if the buffer currently contains a complete packet
                    if (packetBuffer.indexOf(PACKET_START_CHAR) != -1) {
                        endIdx = packetBuffer.lastIndexOf(PACKET_END_CHAR);
                        if (endIdx != -1) {
                            // Buffer also contains at least one start & end character.
                            // But we don't know if they're in the right order yet.
                            // Start by getting the most-recent packet end character's index


                            // Look for the index of the start character for the packet
                            //  described by endIdx. Note this line of code assumes the 
                            //  start character for the packet must come _before_ the
                            //  end character.
                            startIdx = packetBuffer.lastIndexOf(PACKET_START_CHAR, endIdx);

                            if (startIdx == -1) {
                                // If there was no start character before the end character,
                                //  we can assume that we have something a bit wacky in our
                                //  buffer. For example: ",abc}garbage{1,2".
                                // Since we've started to receive a good packet, discard 
                                //  everything prior to the start character.
                                startIdx = packetBuffer.lastIndexOf(PACKET_START_CHAR);
                                packetBuffer.delete(0, startIdx);
                            } else {
                                // Buffer contains a full packet. Extract it and clean up buffer
                                retval = packetBuffer.substring(startIdx + 1, endIdx - 1);
                                packetBuffer.delete(0, endIdx + 1);
                                break;
                            }
                        } else {
                            // In this case, we have a start character, but no end to the buffer yet.
                            //  Do nothing, just wait for more characters to come in.
                            sleep(5);
                        }
                    } else {
                        // Buffer contains no start characters. None of the current buffer contents can 
                        //  be meaningful. Discard the whole thing.
                        packetBuffer.delete(0, packetBuffer.length());
                        sleep(5);
                    }
                } else {
                    sleep(5);
                }
            }
        }
        return retval;
    }

    /**
     * Private wrapper around the Thread.sleep method, to catch that interrupted error.
     *
     * @param time_ms Amount of time to sleep
     */
    private void sleep(int time_ms) {
        try {
            Thread.sleep(time_ms);
        } catch (InterruptedException e) {
            System.out.println("DO NOT WAKE THE SLEEPY BEAST");
            e.printStackTrace();
        }
    }

    /**
     * Mostly for debugging. Blocks execution forever and just prints all serial
     * characters to the console. It might print a different message too if nothing
     * comes in.
     */
    public void blockAndPrintAllSerial() {
        if (visionPort != null) {
            while (!Thread.interrupted()) {
                if (visionPort.getBytesReceived() > 0) {
                    System.out.print(visionPort.readString());
                } else {
                    System.out.println("Nothing Rx'ed");
                    sleep(100);
                }
            }
        }

    }

    /**
     * Parse individual numbers from a packet
     *
     * @param pkt
     */
    public int parsePacket(String pkt) {
        //Parsing constants. These must be aligned with JeVois code.
        final int TGT_FRAME_TOKEN_IDX = 0;
        final int TGT_VISIBLE_TOKEN_IDX = 1;
        final int TGT_ANGLE_TOKEN_IDX = 2;

        //Split string into many substrings, presuming those strings are separated by commas
        String[] tokens = pkt.split(PACKET_DILEM_CHAR);

        //Check there were enough substrings found
        if (tokens.length < PACKET_NUM_EXPECTED_FIELDS) {
            DriverStation.reportError("Got malformed vision packet. Expected " + PACKET_NUM_EXPECTED_FIELDS + " tokens, but only found " + Integer.toString(tokens.length) + ". Packet Contents: " + pkt, false);
            return -1;
        }

        //Convert each string into the proper internal value
        try {
            //Boolean values should only have T or F characters
            switch (tokens[TGT_VISIBLE_TOKEN_IDX]) {
                case "F":
                    tgtVisible = false;
                    break;
                case "T":
                    tgtVisible = true;
                    break;
                default:
                    DriverStation.reportError("Got malformed vision packet. Expected only T or F in " + Integer.toString(TGT_VISIBLE_TOKEN_IDX) + ", but got " + tokens[TGT_VISIBLE_TOKEN_IDX], false);
                    return -1;
            }

            //Use Java built-in double to string conversion on most of the rest
            tgtAngleDeg = Double.parseDouble(tokens[TGT_ANGLE_TOKEN_IDX]);
            frameNumber = Integer.parseInt(tokens[TGT_FRAME_TOKEN_IDX]);

        } catch (Exception e) {
            DriverStation.reportError("Unhandled exception while parsing Vision packet: " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()), false);
            return -1;
        }

        return 0;
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}