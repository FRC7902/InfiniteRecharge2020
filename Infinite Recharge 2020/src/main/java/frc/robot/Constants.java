/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpiutil.math.MathUtil;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  /**
   * Constants for Drive
   */
  public static final class Drive {
    // Drive Controllers
    public static final int FL = 4, 
                            FR = 9, 
                            BL = 3, 
                            BR = 8;
    // PDP Numbers
    public static final int FLP = 12, 
                            FRP = 3, 
                            BLP = 13, 
                            BRP = 2;
    // Drive Limiters
    public static final double kLimit = 1.0;
    public static final double kDriveSpeed = 1.0;
    public static final double kTurnSpeed = 0.7;
    // Travel Speed
    public static final double kAutoSpeed = 0.75;
    // Turn Speed
    public static final double kAutoTurnSpeed = 0.5;
    // Avoid Zero Error 
    public static final double kNoZero = 0.001;

    public static final double kIntakeDriveSpeed = 0.85;

    // Diameter of Robot in meters
    public static final double kRobotDiameter = 0.582;

    // Encoder
    public static final int kLeft1Enc = 0,
                            kLeft2Enc = 1,
                            kRight1Enc = 2,
                            kRight2Enc = 3;
    // Maps Pulse to Distance (m/pulse)
    public static final double kRatio = (Math.PI * 0.1524) / 2048.0;
    // Random Error Avg Number
    public static final int kAvgNum = 5;
    // Minimum Rate Cap (m/s)
    public static final double kMinRate = 0.05;
    public static final double kDriveLimit = 0.2;
  }

  /**
   * Constants for Intake
   */
  public static final class Intake {
    // CAN for the Intake Controller
    public static final int kIntake = 5;
    // Solenoid
    public static final int kFrontSolenoid = 0,
                            kBackSolenoid = 1;
    // Speed
    public static final double kSpeed = 0.4;
  }

  /**
   * Constants for Storage
   */
  public static final class Storage{
    // CAN for the Storage Controllers
    public static final int kLeft = 2,
                            kRight = 7;
    // Speed
    public static final double kSpeed = 0.9;
    public static final double kReverseSpeed = -0.1;

    public static final double kIntakeSpeed = 0.6;
  }

  /**
   * Constants for Shooter
   */
  public static final class Shooter{
    // Shooting
    public static final int LS = 11, 
                            RS = 12;
    // The transfer wheel
    public static final int kTransfer = 10;
    // Transfer Speed (Should be equal or less than shooter speed)
    // No longer needed
    public static final double kTransferSpeed = 1.0;
    // Speed
    public static final double kSpeed = 1.0;
    public static final double kReverseSpeed = -0.5;
    //Dump Speed
    public static final double kDumpSpeed = 0.1;
    public static final double kDumpTransferSpeed = 0.1;
    // Reversal Timeout
    public static final double kTimeout = 0.5;
  }

  /**
   * Constants for Climber
   */
  public static final class Climber{
    // Climbing Controllers
    public static final int CL = 1, 
                            CR = 6;
    // Speed
    public static final double kSpeed = 0.8;
  }

  /**
   * Constants for Colour 
   */
  public static final class Colour{
    // Colour Spinner
    public static final int kSpinner = 13;
    // Speed
    public static final double kSpinSpeed = 0.5;
    // Solenoid
    public static final int kFrontSolenoid = 2,
                            kBackSolenoid = 3;
    // Confidence
    public static final double kMinConfidence = 0.8;
  }

  public static final class Camera {
    // GRIP Table Key
    public static final NetworkTable kGrip = NetworkTableInstance.getDefault().getTable(NetworkTable.PATH_SEPARATOR + "GRIP" + NetworkTable.PATH_SEPARATOR + "contours");
    // Desired Coordinate
    public static final String kXKey = "",
                               kYKey = "";
    // MAX = 640
    public static final double kX = MathUtil.clamp(320.0, 0.0, 640.0),
    // MAX = 480
                               kY = MathUtil.clamp(240.0, 0.0, 640.0);
    // Adjust Speed
    public static final double kSpeed = 0.25;
    // Adjustment Timeout
    public static final double kTimeout = 5;
    // Range End Condition
    public static final double kRange = 1.5;
  }

  /*
   * Other stuff
   */

  // Joystick USB Slot
  public static final int JOY = 0,
                          OP = 1;

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

}
