package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command{
    
    final private IntakeSubsystem intakeSubsystem;
    final private CommandXboxController controller;

    public IntakeCommand(CommandXboxController controller, IntakeSubsystem intakeSubsystem) {
        this.controller = controller;
        this.intakeSubsystem = intakeSubsystem;
        this.addRequirements(intakeSubsystem);
    }
}
