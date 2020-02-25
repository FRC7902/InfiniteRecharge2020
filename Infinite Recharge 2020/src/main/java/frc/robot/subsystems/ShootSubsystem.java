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
  private WPI_VictorSPX transfer = new  WPI_VictorSPX(Constants.Storage.kTransfer);

  private SpeedControllerGroup shooter;

  private String status = "Off";

  /**
   * Creates a new ShootSubsystem.
   */
  public ShootSubsystem() {
    // FIX Might need to invert the other guys instead
    right.setInverted(true);
    // TODO Attempting to put all controller (including transfer)
    shooter = new SpeedControllerGroup(left, transfer, right);
  }

  /**
   * Shoot
   */
  public void shoot() {
    //transfer();
    //left.set(Constants.Shooter.kSpeed);
    //right.set(Constants.Shooter.kSpeed);
    shooter.set(Constants.Shooter.kSpeed);
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
    status = "Stopped";
  }

  /**
   * Transfer to Shooter 
   * ~ Dont use this anymore ~
   */
  @Deprecated
  public void transfer() {
    transfer.set(Constants.Storage.kTransferSpeed);
  }

  /**
   * Terminate Transfer 
   * ~ Dont use this anymore ~
   */
  @Deprecated
  public void stopTransfer() {
    transfer.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putString("Shoot Status", (left.isAlive() == right.isAlive())? status : "Broken");
  }
}
