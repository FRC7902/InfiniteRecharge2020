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
  public static final int LX = 1,
                          LY = 2,
                          LT = 3, // Tigger
                          RT = 4, // Tigger
                          RX = 5,
                          RY = 6, 
                          PX = 7, // D-Pad
                          PY = 8; // D-Pad

  public static final double kDriveSpeedLimiter = 0.6;
  public static final double kDriveFBSpeed = 1;
  public static final double kDriveTurnSpeed = 0.75;
}
