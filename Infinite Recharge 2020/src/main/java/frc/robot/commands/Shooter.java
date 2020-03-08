/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShootSubsystem;
import frc.robot.subsystems.StorageSubsystem;

public class Shooter extends CommandBase {

  /**
   * All Subsystems
   */
  private ShootSubsystem shoot;
  private StorageSubsystem store;

  /**
   * Conditions
   */
  private boolean isEnd,
                  isInit;

  /**
   * Timer
   */
  private Timer time;

  /**
   * Creates a new Shooter.
   */
  public Shooter(ShootSubsystem shoot, StorageSubsystem store) {
    // Registering Things
    time = new Timer();
    this.shoot = shoot;
    this.store = store;
    addRequirements(shoot, store);
  }

  /**
   * Ending
   */
  public void end() {
    isEnd = true;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Start
    isEnd = false;
    isInit = true;
    time.start();
  }

  @Override
  public void execute() {
    // Only run once
    if(isInit) {
      // Inital Reversal
      shoot.reverse();
      shoot.reverseTransfer();
      store.reverse();
      // Wait
      while(!time.hasPeriodPassed(0.5))
        if(isEnd)
          return;
      // Powerup
      shoot.shoot();
      shoot.transfer();
      store.stop();
      // Wait
      while(!time.hasPeriodPassed(1.5))
        if(isEnd)
          return;
      // Shoot
      store.store();
      isInit = false;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop Timer
    if(!interrupted) {
      time.stop();
      time.reset();
    // Restart Shooter If Pressed Again
    }else
      isInit = true;
    // Stop Everything
    store.stop();
    shoot.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isEnd;
  }
}
