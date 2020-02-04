/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
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

  /**
   * Encoders
   */
  private static Encoder leftEncoder = new Encoder(0, 1, true);
  private static Encoder rightEncoder = new Encoder(2, 3, false);

  /**
   * Positions
   */
  private static double x = 0.0,
                        y = 0.0;
                        
  // Current Rotation
  private double curRot = 0.0;

  /**
   * Creates SpeedControllers
   */
  SpeedController leftSide = new SpeedControllerGroup(frontLeft, backLeft);
  SpeedController rightSide = new SpeedControllerGroup(frontRight, backRight);

  public DifferentialDrive drive;

  public DriveSubsystem() {
    // if a motor is inverted, switch the boolean
    // frontRight.setInverted(false);
    // frontLeft.setInverted(true);
    // backRight.setInverted(false);
    // backLeft.setInverted(true);
    rightSide.setInverted(true);
    // drive is a new DifferentialDrive
    drive = new DifferentialDrive(leftSide, rightSide);

    // Encoder
    /*
     * Defines the number of samples to average when determining the rate.
     * On a quadrature encoder, values range from 1-255;
     * larger values result in smoother but potentially
     * less accurate rates than lower values.
     */
    leftEncoder.setSamplesToAverage(5);
    rightEncoder.setSamplesToAverage(5);

    /*
     * Defines how far the mechanism attached to the encoder moves per pulse. In
     * this case, we assume that a 2048 count encoder is directly
     * attached to a 6 inch diameter or 0.1524 meter wheel,
     * and that we want to measure distance in meter.
     */
    leftEncoder.setDistancePerPulse(1.0 / 2048.0 * Math.PI * 0.1524);
    rightEncoder.setDistancePerPulse(1.0 / 2048.0 * Math.PI * 0.1524);

    /*
     * Defines the lowest rate at which the encoder will
     * not be considered stopped, for the purposes of
     * the GetStopped() method. Units are in distance / second,
     * where distance refers to the units of distance
     * that you are using, in this case meter.
     */
    leftEncoder.setMinRate(1.0);
    rightEncoder.setMinRate(1.0);
  }

  /**
   * Sets the speed of the robot on both sides. Recommended method to use.
   * @param leftY
   * Left Side
   * @param rightX
   * Right Side
   */
  public void driveJoystick(double leftY, double rightX){
    drive.arcadeDrive(rightX, leftY);
    // leftSide.set((leftY*Constants.kDriveFBSpeed)*Constants.kDriveSpeedLimiter-rightX*Constants.kDriveTurn);
    // rightSide.set((leftY*Constants.kDriveFBSpeed)*Constants.kDriveSpeedLimiter+rightX*Constants.kDriveTurn);
    // turnSpeed = rightX*Constants.kDriveTurn;
    // broadcastSpeed();
    updatePos();
  }
  /**
   * Sets the speed of the robot on both sides. Use {@link driveJoystick} instead.
   * @param leftSpeed
   * @param rightSpeed
   */
  public void driveRaw(double leftSpeed, double rightSpeed){
    leftSide.set(leftSpeed);
    rightSide.set(rightSpeed);
    updatePos();
    //broadcastSpeed();
  }


  /**
   * Stops motors
   */
  public void stop(){
    drive.stopMotor();
  }

  /**
   * Reset Encoder
   */
  public void resetEnc() {
    leftEncoder.reset();
    rightEncoder.reset();
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
    // Important for Displacement Calculation
    getRotationRobo();
    // Put up the speeds
    SmartDashboard.putNumber("Left Speed", leftSide.get());
    SmartDashboard.putNumber("Right Speed", rightSide.get());
    SmartDashboard.putNumber("Robot Rotation", curRot); 
    SmartDashboard.putNumber("Total Distance Travelled", 0.5 * (leftEncoder.getDistance() + rightEncoder.getDistance()));
  }

  /**
   * Determines the positions of the robo
   */
  private void updatePos() {
    double initTime = System.nanoTime();
    x += 0.5 * ((leftEncoder.getRate() * ((System.nanoTime() - initTime) * Math.pow(10, -9)) * Math.sin(curRot)) + (rightEncoder.getRate() * ((System.nanoTime() - initTime) * Math.pow(10, -9)) * Math.sin(curRot))) ;
    y += 0.5 * ((leftEncoder.getRate() * ((System.nanoTime() - initTime) * Math.pow(10, -9)) * Math.cos(curRot)) + (rightEncoder.getRate() * ((System.nanoTime() - initTime) * Math.pow(10, -9)) * Math.cos(curRot))) ;
  }

  /**
   * Get Cur Rotation of robo in deg
   * TODO will change rot over time, must fix
   */
  private void getRotationRobo() {
    // calculate robot rotation change
    long startT = System.nanoTime();
    double leftRot = leftEncoder.getRate() * ((System.nanoTime() - startT) * Math.pow(10, -9)) / (Constants.roboDia / 2);
    double rightRot = rightEncoder.getRate() * ((System.nanoTime() - startT) * Math.pow(10, -9)) / (Constants.roboDia / 2);
    // Positive if turn right
    // Negative if turn left
    curRot += 0.5 * (leftRot - rightRot);
  }
}
