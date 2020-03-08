/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CameraSubsystem extends SubsystemBase {

  /**
   * Coordinates
   */
  private double curX = Constants.Camera.kGrip.getEntry(Constants.Camera.kXKey).getDouble(-1.0), 
                 curY = Constants.Camera.kGrip.getEntry(Constants.Camera.kYKey).getDouble(-1.0);

  /**
   * Creates a new CameraSubsystem.
   */
  public CameraSubsystem() {
    
  }

  /**
   * Distance from X
   */
  public double getDistX() {
    return Constants.Camera.kX - curX;
  }

  /**
   * Distance from Y
   */
  public double getDistY() {
    return Constants.Camera.kY - curY;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Returns the Current Coordinate of the detected Green
    SmartDashboard.putNumber("Current X", curX);
    SmartDashboard.putNumber("Current Y", curY);
  }
}