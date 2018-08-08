package net.smashmc.cloud.master.command.handler;

import lombok.Getter;
import net.smashmc.cloud.master.Master;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Mike
 * @version 1.0
 */
@Getter
public class CommandHandler {

    /**
     * List with you can fill with Commands
     */
    public final List<Command> registeredCommands = new ArrayList<>();

    /**
     * Put an new Command in the List
     *
     * @param command Your Command
     */
    public final void addCommand(final Command command) {
        registeredCommands.add(command);
    }

    /**
     * Start a new Task and run the Buffered reader
     */
    public final void startTask() {
        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.length() != 0) {
                        for (Command command : registeredCommands) {
                            command.execute(line.split(" "));
                        }
                    }
                }
            } catch (Exception ex) {
                Master.instance.getLogger().log(ex.getMessage());
            }
        }).start();

    }

}

