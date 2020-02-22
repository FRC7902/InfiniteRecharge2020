/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// TODO

public class StorageSubsystem extends SubsystemBase {
  // Controllers
  private WPI_VictorSPX left = new  WPI_VictorSPX(Constants.Storage.kLeft);
  private WPI_VictorSPX right = new  WPI_VictorSPX(Constants.Storage.kRight);
  private WPI_VictorSPX transfer = new  WPI_VictorSPX(Constants.Storage.kTransfer);

  private SpeedControllerGroup store;

  /**
   * Creates a new StorageSubsystem.
   */
  public StorageSubsystem() {
    // Set Inverse
    left.setInverted(true);
    // Bind
    store = new SpeedControllerGroup(left, right);
    // WTF such a useful function I was not told about?
    //transfer.set(ControlMode.VELOCITY, velocity);
  }

  /**
   * Store Motors Start
   */
  public void store() {
    store.set(Constants.Storage.kSpeed);
  }

  /**
   * Stop
   */
  public void stop() {
    store.stopMotor();
  }

  /**
   * Transfer to Shooter 
   */
  public void transfer() {
    transfer.set(Constants.Storage.kTransferSpeed);
  }

  /**
   * Terminate Transfer
   */
  public void stopTransfer() {
    transfer.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
