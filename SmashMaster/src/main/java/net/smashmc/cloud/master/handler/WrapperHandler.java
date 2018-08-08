package net.smashmc.cloud.master.handler;

import lombok.Getter;
import net.smashmc.cloud.master.Master;
import net.smashmc.cloud.master.utils.types.Wrapper;
import org.bson.Document;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Mike
 * @version 1.0
 */

@Getter
public class WrapperHandler {

    public final List<Wrapper> wrappers;

    /**
     * Constructor with wrapperlist
     */
    public WrapperHandler() {
        this.wrappers = new CopyOnWriteArrayList<>();
    }

    /**
     * Add a Wrapper from database to wrapperlist
     */
    public void addWrapper() {
        for (Document document : Master.instance.getDatabaseConnection().getCollection("wrapper").find()) {
            int ram = document.getInteger("maxRam");
            Wrapper wrapper = new Wrapper(document.getString("name"), new LinkedList<>(), new LinkedList<>(), ram, ram);
            wrappers.add(wrapper);
        }
    }


}
