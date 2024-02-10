package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.LimelightHelpers;

public class DefaultDriveCommand extends Command {
    
    final private DriveSubsystem driveSubsystem; 
    final CommandXboxController controller;

    public DefaultDriveCommand(CommandXboxController controller, DriveSubsystem driveSubsystem) {
        this.controller = controller;
        this.driveSubsystem = driveSubsystem;
        this.addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {

        double left = controller.getLeftY();
        double right = controller.getRightY();
        
        driveSubsystem.drive(left, right);
        
    }
}
