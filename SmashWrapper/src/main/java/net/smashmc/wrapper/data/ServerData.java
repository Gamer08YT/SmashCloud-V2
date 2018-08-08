package net.smashmc.wrapper.data;

/**
 * @author Mike
 * @version 1.0
 */
public class ServerData {

    private String name, template;
    private int port;
    private int processid;
    private Process process;
    private int minram;

    /**
     * Create a new ServerData object
     * @param name server name
     * @param template server template
     * @param port server port
     * @param minram server minram
     */
    public ServerData(String name, String template, int port, int minram) {
        this.name = name;
        this.template = template;
        this.port = port;
        this.minram = minram;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public Process getProcess() {
        return process;
    }

    public int getProcessID() {
        return processid;
    }



    public void setProcessID(int processID) {
        this.processid = processID;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public int getMinram() {
        return minram;
    }

    public String getTemplate() {
        return template;
    }
}
