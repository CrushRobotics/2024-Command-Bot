package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class ShootingPositionCommand extends Command {
    private ArmSubsystem armSubsystem;

    public ShootingPositionCommand (ArmSubsystem armSubsystem)
    {
        this.armSubsystem = armSubsystem;
        addRequirements(armSubsystem);
    }

    @Override
    public void execute()
    {
        armSubsystem.moveToShootPosition();
    }

    @Override
    public void end(boolean isInterrupted)
    {
        armSubsystem.setTarget(0);
    }
}
