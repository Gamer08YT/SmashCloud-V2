package net.smashmc.cloud.master.utils.types;

import lombok.Data;
import net.smashmc.cloud.master.utils.modules.Template;

/**
 * @author Mike
 * @version 1.0
 */

@Data
public class Server {
    private final String name;
    private final Template template;
    private final int port;
    private final int minRam;
    private int onlinePlayer;
    private int maxPlayer;
    private String motd;
    private boolean ingame;
}
