package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class FeedShooterCommand extends Command {
    private ShooterSubsystem shooterSubsystem;
    private IntakeSubsystem intakeSubsystem;

    public FeedShooterCommand(IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem)
    {
        addRequirements(shooterSubsystem);
        addRequirements(intakeSubsystem);
        
        this.shooterSubsystem = shooterSubsystem;
        this.intakeSubsystem = intakeSubsystem;
    }

    @Override
    public void execute()
    {
        intakeSubsystem.intTakeOn();
        shooterSubsystem.runIntake();
    }

    @Override
    public boolean isFinished()
    {
        return shooterSubsystem.hasRing();
    }
}
