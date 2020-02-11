/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

  // Intake Controllers
  private WPI_VictorSPX upper = new WPI_VictorSPX(Constants.UI);
  private WPI_VictorSPX lower = new WPI_VictorSPX(Constants.LI);

  // Status
  private String status;

  /**
   * Creates a new IntakeSubsystem.
   */
  public IntakeSubsystem() {
    // Invert the bottom motors
    lower.setInverted(true);
    status = "Off";
  }

  /**
   * Sucks the balls in
   */
  public void succ() {
    upper.set(Constants.SUCSPEED);
    lower.set(Constants.SUCSPEED);
    status = "Succing";
  }

  /**
   * Stop Sucking
   */
  public void stap() {
    upper.stopMotor();
    lower.stopMotor();
    status = "Off";
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler 
    SmartDashboard.putString("Intake Status", (upper.isAlive() == lower.isAlive())? status : "Broken");
  }
}
