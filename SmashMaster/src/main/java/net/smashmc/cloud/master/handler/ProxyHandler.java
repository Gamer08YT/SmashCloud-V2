package net.smashmc.cloud.master.handler;

import lombok.Getter;
import net.smashmc.cloud.master.Master;
import net.smashmc.cloud.master.utils.types.Bungeecord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Mike
 * @version 1.0
 */
public class ProxyHandler {

    @Getter
    public final List<Bungeecord> bungeecords;

    /**
     * Constructor with ProxyList
     */
    public ProxyHandler() {
        this.bungeecords = new ArrayList<>();
    }

    /**
     * Start the Proxy and add to proxylist
     *
     * @param name the proxyname
     * @param ram  proxyram
     * @param file bungeecord folder
     */
    public final void startProxies(String name, int ram, File file) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(file);
            processBuilder.command("java", "-Xms512M", "-Xmx" + ram + "M", "-Dfile.encoding=UTF-8", "-Djline.terminal=jline.UnsupportedTerminal", "-jar", "BungeeCord.jar");
            Process process = processBuilder.start();

            bungeecords.add(new Bungeecord(ram, UUID.randomUUID(), name, process));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Master.instance.getLogger().log(String.format("One new Proxy started (%s)", name));
    }

    /**
     * Stop all proxys and destroy the process
     */
    public void stopAllProxies() {
        bungeecords.forEach(bungeecord -> {
            try {
                bungeecord.getProcess().getOutputStream().write("end\n".getBytes());
                bungeecord.getProcess().getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
