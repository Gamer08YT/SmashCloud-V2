package net.smashmc.cloud.master.command.handler;

@SuppressWarnings("JavaDoc")
public interface Command {

   /**
    * Run a new Command which is split in many arguments
    * @param args
    */
   void execute(String[] args);

}
