package net.smashmc.cloud.master.utils.signs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Sign {

    private int x, y, z;
    private String world;
    private String name;
    private boolean used;
    private String serverName;
    private String template;

    public Sign(int x, int y, int z, String world, String serverName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.serverName = serverName;
    }
}
