/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */

 //Drive Values

public final class Constants {

  // Joystick USB Slot
  public static final int JOY = 0;

  // Joystick - Button
  public static final int A = 1,
                          B = 2,
                          X = 3,
                          Y = 4,
                          LB = 5, // Bumper
                          RB = 6, // Bumper
                          M = 7, // menu
                          S = 8, // start
                          LA = 9, // Press Left axis
                          RA = 10; // Press right axis

  // Joystick - Axis
  public static final int LX = 0,
                          LY = 1,
                          LT = 2, // Tigger
                          RT = 3, // Tigger
                          RX = 4,
                          RY = 5, 
                          PX = 6, // D-Pad
                          PY = 7; // D-Pad

  // Drive Limiters
  public static final double kDriveSpeedLimiter = 0.7;
  public static final double kDriveFBSpeed = 1;
  public static final double kDriveTurn = 0.9;

  // Diameter of Robot in meters
  public static final double ROBODIA = 0.582;

  // Encoder
  public static final int LEFT1 = 0,
                          LEFT2 = 1,
                          RIGHT1 = 2,
                          RIGHT3 = 3;
  // Maps Pulse to Distance (m/pulse)
  public static final double RATIO = 1.0 / 2048.0 * Math.PI * 0.1524;
  // Random Error Avg Number
  public static final int AVGNUM = 5;
  // Minimum Rate Cap (m/s)
  public static final double MINRATE = 1.0;

  // Drive Controllers
  public static final int FL = 2, 
                          FR = 7, 
                          BL = 1, 
                          BR = 6;

  // Intake Controller
  public static final int UI = 5, 
                          LI = 10;
  // Speed
  public static final double SUCSPEED = 0.5;

}
