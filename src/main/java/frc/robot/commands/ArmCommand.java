package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.subsystems.ArmSubsystem;

public class ArmCommand extends Command {
    
    final private ArmSubsystem armSubsystem;
    final CommandXboxController controller;

    public ArmCommand(CommandXboxController controller, ArmSubsystem armSubsystem) {
        this.controller = controller;
        this.armSubsystem = armSubsystem;
        this.addRequirements(armSubsystem);
    }
}
