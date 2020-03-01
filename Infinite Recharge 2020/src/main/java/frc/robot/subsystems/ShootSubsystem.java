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

  private WPI_TalonSRX left = new WPI_TalonSRX(Constants.Shooter.LS);
  private WPI_TalonSRX right = new WPI_TalonSRX(Constants.Shooter.RS);
  private WPI_VictorSPX transfer = new  WPI_VictorSPX(Constants.Shooter.kTransfer); //da transfer

  private SpeedControllerGroup shooter;

  private String status = "Off";

  /**
   * Creates a new ShootSubsystem.
   */
  public ShootSubsystem() {
    // FIX Might need to invert the other guys instead
    right.setInverted(true);
    shooter = new SpeedControllerGroup(left, right);
  }

  /**
   * Shoot
   */
  public void shoot() {
    //transfer();
    //left.set(Constants.Shooter.kSpeed);
    //right.set(Constants.Shooter.kSpeed);
    shooter.set(Constants.Shooter.kSpeed);
    //transfer.set(Constants.Shooter.kTransferSpeed);
    status = "Shooting";
  }

  /**
   * Stop
   */
  public void stap() {
    //stopTransfer();
    //left.stopMotor();
    //right.stopMotor();
    shooter.stopMotor();
    transfer.stopMotor();
    status = "Stopped";
  }

  /**
   * Transfer to Shooter 
   * ~ Dont use this anymore ~
   */
  public void transfer() {
    transfer.set(Constants.Shooter.kTransferSpeed);
  }

  /**
   * Checks if evrything is ready
   */
  public boolean isPowered() {
    return Constants.Shooter.kSpeed == shooter.get();
  }
  public void dumpShoot(){
    shooter.set(Constants.Shooter.kDumpSpeed);
  }
  public void dumpTransfer(){
    transfer.set(Constants.Shooter.kDumpTransferSpeed);
  }

  /**
   * Terminate Transfer 
   * ~ Dont use this anymore ~
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
