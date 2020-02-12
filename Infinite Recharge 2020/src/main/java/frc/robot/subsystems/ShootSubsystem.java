/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShootSubsystem extends SubsystemBase {

  private WPI_TalonSRX left = new WPI_TalonSRX(Constants.LS);
  private WPI_TalonSRX right = new WPI_TalonSRX(Constants.RS);

  /**
   * Creates a new ShootSubsystem.
   */
  public ShootSubsystem() {
    right.setInverted(true);
  }

  /**
   * Shoot
   */
  public void shoot() {
    left.set(Constants.SHUSPEED);
    right.set(Constants.SHUSPEED);
  }

  /**
   * Stop
   */
  public void stap() {
    left.stopMotor();
    right.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}