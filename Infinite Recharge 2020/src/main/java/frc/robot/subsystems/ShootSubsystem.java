/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShootSubsystem extends SubsystemBase {

  // The Controller
  private WPI_TalonSRX left = new WPI_TalonSRX(Constants.Shooter.LS);
  private WPI_TalonSRX right = new WPI_TalonSRX(Constants.Shooter.RS);
  private WPI_VictorSPX transfer = new  WPI_VictorSPX(Constants.Shooter.kTransfer); 

  // The Shooter Group
  private SpeedControllerGroup shooter;

  // Status
  private String status = "Off";

  /**
   * Creates a new ShootSubsystem.
   */
  public ShootSubsystem() {
    // Invert Shooter
    right.setInverted(true);
    // Limit Things
    // left.configPeakCurrentLimit(60);
    // right.configPeakCurrentLimit(60);
    left.configOpenloopRamp(0.3);
    right.configOpenloopRamp(0.3);
    // Creates the Shooter Group
    shooter = new SpeedControllerGroup(left, right);
  }

  /**
   * Shoot
   */
  public void shoot() {
    // Activate
    shooter.set(Constants.Shooter.kSpeed);
    // Change Status
    status = "Shooting";
  }

  /**
   * Stop
   */
  public void stop() {
    // Stops the Motor
    shooter.stopMotor();
    transfer.stopMotor();
    // Change Status
    status = "Stopped";
  }

  /**
   * Transfer to Shooter 
   */
  public void transfer() {
    transfer.set(Constants.Shooter.kTransferSpeed);
  }

  /**
   * Dumps Balls from Shooter
   */
  public void dumpShoot(){
    shooter.set(Constants.Shooter.kDumpSpeed);
  }

  /**
   * Dumps Balls with Transfer
   */
  public void dumpTransfer(){
    transfer.set(Constants.Shooter.kDumpTransferSpeed);
  }

  /**
   * Terminate Transfer 
   */
  public void stopTransfer() {
    transfer.stopMotor();
  }

  /**
   * Reverses
   */
  public void reverse() {
    shooter.set(Constants.Shooter.kReverseSpeed);
  }

  /**
   * Reverses Transfer
   */
  public void reverseTransfer() {
    transfer.set(Constants.Shooter.kReverseSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putString("Shoot Status", (left.isAlive() == right.isAlive())? status : "Broken");
    SmartDashboard.putNumber("Shoot Speed", shooter.get());
    SmartDashboard.putNumber("Transfer Speed", transfer.get());
  }
}
