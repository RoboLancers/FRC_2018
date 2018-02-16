package org.usfirst.frc.team321.robot.utilities.controllers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public abstract class Controller {
    Joystick joystick;
    Button[] buttons;
    public POV Dpad_up, Dpad_upRight, Dpad_right, Dpad_downRight, 
    	Dpad_down, Dpad_downLeft, Dpad_left, Dpad_upLeft;

    Controller(int port) {
        this.joystick = new Joystick(port);
        this.initializeButtons();
    }

    private void initializeButtons() {
        buttons = new JoystickButton[13];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JoystickButton(joystick, i);
        }

        Dpad_up = new POV(joystick, 0);
        Dpad_upRight = new POV(joystick, 45);
        Dpad_right = new POV(joystick, 90);
        Dpad_downRight = new POV(joystick, 135);
        Dpad_down = new POV(joystick, 180);
        Dpad_downLeft = new POV(joystick, 225);
        Dpad_left = new POV(joystick, 270);
        Dpad_upLeft = new POV(joystick, 315);
    }
}
