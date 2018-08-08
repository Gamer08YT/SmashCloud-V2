package net.smashmc.cloud.bungeesystem.database;

import com.mongodb.client.model.Filters;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import org.bson.Document;

import java.util.UUID;
import java.util.function.Consumer;

public class User{


    /**
     * Get the coins of a player
     *
     * @param player the player which you want to get the coins
     * @param coins  the coins as a consumer
     */
    public void getCoins( ProxiedPlayer player, Consumer<Integer> coins ){
        Document find = BungeeSystem.instance.getDatabaseConnection().getCollection( "user" ).find( Filters.eq( "uuid", player.getUniqueId() ) ).first();
        if ( find != null ){
            coins.accept( find.getInteger( "coins" ) );
        }
    }
    public void getCoins( UUID player, Consumer<Integer> coins ){
        Document find = BungeeSystem.instance.getDatabaseConnection().getCollection( "user" ).find( Filters.eq( "uuid", player ) ).first();
        if ( find != null ){
            coins.accept( find.getInteger( "coins" ) );
        }
    }

    /**
     * Set a player new coins
     *
     * @param coins  the coins which you want to set the player
     */
    public void setCoins( UUID uuid, int coins ){
        BungeeSystem.instance.getDatabaseConnection().getCollection( "user" ).updateOne( Filters.eq( "uuid", uuid ), new Document( "$set", new Document( "coins", coins ) ) );
    }

    public void setRank( ProxiedPlayer player, int rank ){
        BungeeSystem.instance.getDatabaseConnection().getCollection( "user" ).updateOne( Filters.eq( "uuid", player.getUniqueId() ), new Document( "$set", new Document( "id", rank ) ) );
    }
    public void setRank( UUID uuid, int rank ){
        BungeeSystem.instance.getDatabaseConnection().getCollection( "user" ).updateOne( Filters.eq( "uuid", uuid ), new Document( "$set", new Document( "id", rank ) ) );
    }
    public void getLanguage( ProxiedPlayer player, Consumer<String> language ){
        Document find = BungeeSystem.instance.getDatabaseConnection().getCollection( "user" ).find( Filters.eq( "uuid", player.getUniqueId() ) ).first();
        if ( find != null ){
            language.accept( find.getString( "language" ) );
        }
    }

    public void getRankID( ProxiedPlayer player, Consumer<Integer> id ){
        Document find = BungeeSystem.instance.getDatabaseConnection().getCollection( "user" ).find( Filters.eq( "uuid", player.getUniqueId() ) ).first();
        if ( find != null ){
            id.accept( find.getInteger( "id" ) );
        }
    }

    public void getRankID( UUID uuid, Consumer<Integer> id ){
        Document find = BungeeSystem.instance.getDatabaseConnection().getCollection( "user" ).find( Filters.eq( "uuid", uuid ) ).first();
        if ( find != null ){
            id.accept( find.getInteger( "id" ) );
        }
    }

    public void getRank( Integer id, Consumer<String> rank ){
        Document document = BungeeSystem.instance.getDatabaseConnection().getCollection( "ranks" ).find( Filters.eq( "id", id ) ).first();
        if ( document != null ){
            rank.accept( document.getString( "rank" ) );
        }
    }

    public void getPrefix( int id, Consumer<String> prefix ){
        Document document = BungeeSystem.instance.getDatabaseConnection().getCollection( "ranks" ).find( Filters.eq( "id", id ) ).first();
        if ( document != null ){
            prefix.accept( document.getString( "prefix" ) );
        }
    }

    public void setLoggedIn( ProxiedPlayer player, boolean loggedIn ){
        BungeeSystem.instance.getDatabaseConnection().getCollection( "settings" ).updateOne( Filters.eq( "uuid", player.getUniqueId() ), new Document( "$set", new Document( "loggedin", loggedIn ) ) );
    }

    public void isLoggedIn( ProxiedPlayer player, Consumer<Boolean> loggedIn ){
        Document find = BungeeSystem.instance.getDatabaseConnection().getCollection( "settings" ).find( Filters.eq( "uuid", player.getUniqueId() ) ).first();
        if ( find != null ){
            loggedIn.accept( find.getBoolean( "loggedin" ) );
        }
    }
}
