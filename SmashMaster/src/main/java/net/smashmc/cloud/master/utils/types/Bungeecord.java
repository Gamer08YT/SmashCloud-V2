package net.smashmc.cloud.master.utils.types;

import io.netty.channel.Channel;
import lombok.Data;

import java.util.UUID;

/**
 * @author Mike
 * @version 1.0
 */

@Data
public class Bungeecord {

    private final int minram;
    private final UUID uuid;
    private final String name;
    private Channel channel;
    private final Process process;

}
