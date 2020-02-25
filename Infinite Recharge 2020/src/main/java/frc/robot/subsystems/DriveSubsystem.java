/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
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
  public WPI_VictorSPX frontRight = new WPI_VictorSPX(Constants.Drive.FR);
  public WPI_VictorSPX frontLeft = new WPI_VictorSPX(Constants.Drive.FL);
  public WPI_VictorSPX backRight = new WPI_VictorSPX(Constants.Drive.BR);
  public WPI_VictorSPX backLeft = new WPI_VictorSPX(Constants.Drive.BL);

  /**
   * Encoders
   */
  private static Encoder leftEncoder = new Encoder(Constants.Drive.kLeft1Enc, Constants.Drive.kLeft2Enc, Constants.Drive.kLeft3Enc, true);
  private static Encoder rightEncoder = new Encoder(Constants.Drive.kRight1Enc, Constants.Drive.kRight2Enc, Constants.Drive.kRight3Enc, false);

  /**
   * Positions
   */
  private static double x = 0.0,
                        y = 0.0;
                        
  // Current Rotation
  private static double curRot = 0.0;

  /**
   * Creates SpeedControllers
   */
  SpeedController leftSide = new SpeedControllerGroup(frontLeft, backLeft);
  SpeedController rightSide = new SpeedControllerGroup(frontRight, backRight);

  public DifferentialDrive drive;

  /**
   * <t>Drive Subsystem</t>
   */
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
    leftEncoder.setSamplesToAverage(Constants.Drive.kAvgNum);
    rightEncoder.setSamplesToAverage(Constants.Drive.kAvgNum);

    /*
     * Defines how far the mechanism attached to the encoder moves per pulse. In
     * this case, we have a 2048 count encoder is directly
     * attached to a 6 inch diameter or 0.1524 meter wheel,
     * and that we want to measure distance in meter.
     */
    // Basically defines how much 1 count of the encoder is equal to in distance
    leftEncoder.setDistancePerPulse(Constants.Drive.kRatio);
    rightEncoder.setDistancePerPulse(Constants.Drive.kRatio);

    /*
     * Defines the lowest rate at which the encoder will
     * not be considered stopped, for the purposes of
     * the GetStopped() method. Units are in distance / second,
     * where distance refers to the units of distance
     * that you are using, in this case meter.
     */
    leftEncoder.setMinRate(Constants.Drive.kMinRate);
    rightEncoder.setMinRate(Constants.Drive.kMinRate);
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
   * Travel distance ~ for auto 
   * *NOTE: IN METERS*
   * Positive DIST = Forward
   * Negative DIST = Backward
   * Will Auto-Stop
   * @param dist
   * Distance you want to travel in meters
   */
  public void travel(double dist) {
    // Gets Desired Dist
    double finDist = 0.5 * (leftEncoder.getDistance() + rightEncoder.getDistance()) + dist;
    // Avoid Dividing by 0
    if(finDist == 0.0)
      finDist += Constants.Drive.kNoZero;
    // Apporach Dist Check
    do{
      // (dist / Math.abs(dist)) = Neg/Pos Check
      driveRaw((dist / Math.abs(dist)) * Constants.Drive.kAutoSpeed, (dist / Math.abs(dist)) * Constants.Drive.kAutoSpeed);
    // Approaching 0 = Arrive at angle
    }while((0.5 * (leftEncoder.getDistance() + rightEncoder.getDistance()) - finDist) / finDist > 0);
    // Stops afterwards
    stop();
  }

  /**
   * Turns Robo ~ for auto
   * *NOTE: IN RADIANS* (Use Math.toRad() to convert if needed) 
   * Positive DEG = Right;
   * Negative DEG = Left;
   * Will Auto-Stop
   * @param rot
   * Rotate this much
   */
  public void turn(double rot) {
    // Get Final Rotation Needed
    double finRot = curRot + rot;
    // Avoid Dividing by 0
    if(finRot == 0.0)
      finRot += Constants.Drive.kNoZero;
    do{
      // (rot / Math.abs(rot)) = Neg/Pos Check
      driveRaw((rot / Math.abs(rot)) * Constants.Drive.kAutoTurnSpeed, -(rot / Math.abs(rot)) * Constants.Drive.kAutoTurnSpeed);
    // Approaching 0 = Arrive at angle
    }while((curRot - finRot) / finRot > 0);
    // Stop Motor
    stop();
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
   * Calculate Displacement
   */
  public double getDisplacement() {
    // Someguy's Theorm
    return Math.sqrt(x * x + y * y);
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
    // For the Humans
    SmartDashboard.putNumber("Robot Rotation", Math.toDegrees(curRot)); 
    // Displacement from Spawn
    SmartDashboard.putNumber("Displacement", getDisplacement());
    // AVG Dist
    SmartDashboard.putNumber("Total Distance Travelled", 0.5 * (leftEncoder.getDistance() + rightEncoder.getDistance()));
  }

  /**
   * Determines the positions (coords) of the robo
   */
  private void updatePos() {
    long initTime = System.nanoTime();
    x += 0.5 * ((leftEncoder.getRate() * ((System.nanoTime() - initTime) * Math.pow(10, -9)) * Math.sin(curRot)) + (rightEncoder.getRate() * ((System.nanoTime() - initTime) * Math.pow(10, -9)) * Math.sin(curRot))) ;
    y += 0.5 * ((leftEncoder.getRate() * ((System.nanoTime() - initTime) * Math.pow(10, -9)) * Math.cos(curRot)) + (rightEncoder.getRate() * ((System.nanoTime() - initTime) * Math.pow(10, -9)) * Math.cos(curRot))) ;
  }

  /**
   * Get Cur Rotation of robot
   * TODO will change rot over time, must fix
   * FIX nah
   */
  private void getRotationRobo() {
    // calculate robot rotation change
    long startT = System.nanoTime();
    // Uses the equation (length of Arc) = (Angle (RAD)) * (Radius)
    /*                                       //Get the change in Time//      //Convert to Seconds///Radius Calculation//*/
    double leftRot  =  leftEncoder.getRate() * ((System.nanoTime() - startT) * Math.pow(10, -9)) / (Constants.Drive.kRobotDiameter / 2);
    double rightRot = rightEncoder.getRate() * ((System.nanoTime() - startT) * Math.pow(10, -9)) / (Constants.Drive.kRobotDiameter / 2);
    // Positive if turn right
    // Negative if turn left
    curRot += 0.5 * (leftRot - rightRot);
  }
}