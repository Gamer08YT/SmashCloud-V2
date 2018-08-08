package net.smashmc.cloud.bungeesystem.handler.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author Mike
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public class Server {

    private String template;
    private String name;
    private int port;

}
