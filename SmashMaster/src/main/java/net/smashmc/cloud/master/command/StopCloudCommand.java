package net.smashmc.cloud.master.command;

import net.smashmc.cloud.master.command.handler.Command;

/**
 * @author Mike
 * @author 1.0
 */
public class StopCloudCommand implements Command {

    /**
     * Run the Stop Command
     *
     * @param args You write many Arguments and the System split them
     */
    @Override
    public final void execute(String[] args) {
        if (args[0].equalsIgnoreCase("stop")) {
            System.exit(0);
        }

    }
}

