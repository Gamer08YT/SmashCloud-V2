package net.smashmc.cloud.master.utils.signs;

import lombok.Getter;
import net.smashmc.cloud.master.Master;
import net.smashmc.cloud.master.utils.types.Server;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicSign {

    public List<Sign> signs;

    public static final DynamicSign DYNAMIC_SIGN = new DynamicSign();

    public DynamicSign() {
        this.signs = new ArrayList<>();
    }

    public void call() {
        for (Document document : Master.instance.getDatabaseConnection().getCollection("signs").find()) {

        }
    }
}
