/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  /**
   * Creates a new DriveSubsystem.
   */

  public WPI_VictorSPX frontRight = new WPI_VictorSPX(1);
  public WPI_VictorSPX frontLeft = new WPI_VictorSPX(2);
  public WPI_VictorSPX backRight = new WPI_VictorSPX(3);
  public WPI_VictorSPX backLeft = new WPI_VictorSPX(4);

  SpeedController leftSide = new SpeedControllerGroup(frontLeft, backLeft);
  SpeedController rightSide = new SpeedControllerGroup(frontRight, backRight);

  public DifferentialDrive drive;

  public DriveSubsystem() {
    // if a motor is inverted, switch the boolean
    frontRight.setInverted(false);
    frontLeft.setInverted(true);
    backRight.setInverted(false);
    backLeft.setInverted(true);
    // drive is a new DifferentialDrive
    drive = new DifferentialDrive(leftSide, rightSide);
  }

  public void driveJoystick(double leftY, double rightX){
    leftSide.set((-leftY*Constants.kDriveFBSpeed-rightX*Constants.kDriveTurnSpeed)*Constants.kDriveSpeedLimiter);
    rightSide.set((-leftY*Constants.kDriveFBSpeed+rightX*Constants.kDriveTurnSpeed)*Constants.kDriveSpeedLimiter);
  }

  public void driveRaw(double leftSpeed, double rightSpeed){
    leftSide.set(leftSpeed);
    rightSide.set(rightSpeed);
  }

  public void stop(){
    drive.stopMotor();
  }

  public void setMaxOutput(double maxOutput){
    drive.setMaxOutput(maxOutput);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    //Put up the speeds
    SmartDashboard.putNumber("Left Drive Speed", leftSide.get());
    SmartDashboard.putNumber("Right Drive Speed", rightSide.get());

  }
}
