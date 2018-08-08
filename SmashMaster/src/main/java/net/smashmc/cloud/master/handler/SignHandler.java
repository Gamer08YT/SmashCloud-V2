package net.smashmc.cloud.master.handler;

import net.smashmc.cloud.master.Master;
import org.bson.Document;

public class SignHandler {

    public void loadSigns() {
        for (Document document : Master.instance.getDatabaseConnection().getCollection("signs").find()) {
            String template = document.getString("template");
            String world = document.getString("world");
            int cords = document.getInteger("cords");
        }
    }

}
