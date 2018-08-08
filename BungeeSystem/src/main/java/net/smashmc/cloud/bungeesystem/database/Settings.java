package net.smashmc.cloud.bungeesystem.database;

import com.mongodb.client.model.Filters;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import org.bson.Document;

public class Settings{

    public void updateMaintenance( boolean maintenance ){
        BungeeSystem.instance.getDatabaseConnection().getCollection( "cloud" ).updateOne( Filters.eq( "template", "Proxy" ), new Document( "$set", new Document( "maintenance", maintenance ) ) );
    }

}
