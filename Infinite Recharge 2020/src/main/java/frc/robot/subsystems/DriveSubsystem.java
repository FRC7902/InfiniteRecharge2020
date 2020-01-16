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
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {

  /**
   * Creates a new DriveSubsystem.
   */
  public WPI_VictorSPX frontRight = new WPI_VictorSPX(4);
  public WPI_VictorSPX frontLeft = new WPI_VictorSPX(2);
  public WPI_VictorSPX backRight = new WPI_VictorSPX(3);
  public WPI_VictorSPX backLeft = new WPI_VictorSPX(1);

  public static double turnSpeed = 0;

  /**
   * Creates SpeedControllers
   */
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

  /**
   * Sets the speed of the robot on both sides. Recommended method to use.
   * @param leftY
   * Left Side
   * @param rightX
   * Right Side
   */
  public void driveJoystick(double leftY, double rightX){
    leftSide.set((leftY*Constants.kDriveFBSpeed)*Constants.kDriveSpeedLimiter-rightX*Constants.kDriveTurn);
    rightSide.set((leftY*Constants.kDriveFBSpeed)*Constants.kDriveSpeedLimiter+rightX*Constants.kDriveTurn);
    turnSpeed = rightX*Constants.kDriveTurn;
    broadcastSpeed();
  }

  /**
   * Sets the speed of the robot on both sides. Use {@link driveJoystick} instead.
   * @param leftSpeed
   * @param rightSpeed
   */
  public void driveRaw(double leftSpeed, double rightSpeed){
    leftSide.set(leftSpeed);
    rightSide.set(rightSpeed);
    broadcastSpeed();
  }

  private void broadcastSpeed() {
    SmartDashboard.putNumber("Speed", leftSide.get());
    SmartDashboard.putNumber("Turn Speed", turnSpeed);
  }

  /**
   * Stops motors
   */
  public void stop(){
    drive.stopMotor();
  }

  /**
   * Set max output 
   */
  public void setMaxOutput(double maxOutput){
    drive.setMaxOutput(maxOutput);
  }

  /**
   * This method will be called once per scheduler run
   */
  @Override
  public void periodic() {
    // Put up the speeds
    SmartDashboard.putNumber("Drive Speed", leftSide.get());
    SmartDashboard.putNumber("Turn Speed", turnSpeed);
  }
}
