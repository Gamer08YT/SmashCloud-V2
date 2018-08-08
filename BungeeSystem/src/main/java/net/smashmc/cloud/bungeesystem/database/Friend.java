package net.smashmc.cloud.bungeesystem.database;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.smashmc.cloud.bungeesystem.BungeeSystem;
import org.bson.Document;

import java.util.*;

public class Friend {
    public UUID uuid;

    /**
     * Create a new Friend object
     *
     * @param uuid uuid of friendplayer
     */
    public Friend(UUID uuid) {
        this.uuid = uuid;
    }


    public static class FriendHandler {

        /**
         * Create a new player for database
         *
         * @param uuid    uuid of connected player
         * @param name    name of connected player
         * @param friends friend object
         */

        public void create(UUID uuid, String name, List<Friend> friends) {
            friends = new ArrayList();

            Document document = new Document("uuid", uuid);
            document.put("name", name);
            document.put("friends", friends);
            document.put("friend-invites", true);
            document.put("party-invites", true);
            document.put("status", "Active");
            document.put("friend-range", 30);
            long count = BungeeSystem.instance.getDatabaseConnection().getCollection("friends").count(new Document("uuid", uuid));
            if (count == 0) {
                BungeeSystem.instance.getDatabaseConnection().getCollection("friends").insertOne(document);
            }
        }

        /**
         * Add a new Friend to player
         *
         * @param uuid   uuid of player
         * @param friend new Friend object
         */
        public void addFriend(UUID uuid, Friend friend) {
            BungeeSystem.instance.getDatabaseConnection().getCollection("friends").updateOne(Filters.eq("uuid", uuid), new Document("$addToSet", new Document("friends", friend.uuid)));
        }

        /**
         * If is a player friend of another one return true
         *
         * @param player player which you want to check
         * @param uuid   uuid which is friend of another player
         * @return
         */
        public boolean isFriended(ProxiedPlayer player, UUID uuid) {
            Document found = BungeeSystem.instance.getDatabaseConnection().getCollection("friends").find(new BasicDBObject("uuid", player.getUniqueId()).append("friends", uuid)).first();
            return found != null;
        }

        /**
         * Get the size of friends from a player
         *
         * @param player the player which friend size you want
         * @return
         */
        public Integer getFriendSize(ProxiedPlayer player) {
            Document friend = BungeeSystem.instance.getDatabaseConnection().getCollection("friends").aggregate(Arrays.asList(Aggregates.match(Filters.eq("uuid", player.getUniqueId())), Aggregates.project(Projections.computed("friendAmount", Projections.computed("$size", "$friends"))))).first();
            return friend.getInteger("friendAmount");
        }

        /**
         * Get the list of all friends from a player
         *
         * @param player the player which friend size you want
         * @return
         */
        public List<UUID> getFriendList(ProxiedPlayer player) {
            List<UUID> friendUUIDs = (List<UUID>) BungeeSystem.instance.getDatabaseConnection().getCollection("friends").find(Filters.eq("uuid", player.getUniqueId())).first().get("friends");

            return friendUUIDs;
        }

        /**
         * Give you the name of a uuid from a player
         *
         * @param uuid uuid of the player which name you want
         * @return
         */
        public static String getNameFromUUID(UUID uuid) {
            return BungeeSystem.instance.getDatabaseConnection().getCollection("user").find(Filters.eq("uuid", uuid)).first().getString("name");
        }

        /**
         * Get the max player range of player
         *
         * @param uuid uuid of the player which range you want
         * @return
         */
        public Integer getMaxFriendRange(UUID uuid) {
            return BungeeSystem.instance.getDatabaseConnection().getCollection("friends").find(Filters.eq("uuid", uuid)).first().getInteger("friend-range");
        }

        /**
         * Check if a friend is online
         *
         * @param uuid uuid of the player which online state you want
         * @return
         */
        public boolean isOnline(UUID uuid) {
            return BungeeSystem.instance.getDatabaseConnection().getCollection("user").find(Filters.eq("uuid", uuid)).first().getBoolean("onlineState");
        }

        /**
         * Get the status of a player
         *
         * @param uuid uuid of the player which status you want
         * @return
         */
        public String getStatus(UUID uuid) {
            return BungeeSystem.instance.getDatabaseConnection().getCollection("friends").find(Filters.eq("uuid", uuid)).first().getString("status");
        }

        /**
         * Set a friend in online state
         *
         * @param uuid   uuid of player which you want to toggle
         * @param online toggle online state
         */
        public void setOnline(UUID uuid, boolean online) {
            BungeeSystem.instance.getDatabaseConnection().getCollection("user").updateOne(Filters.eq("uuid", uuid), new Document("$set", new Document("onlineState", online)));
        }

        public void getFriendInvites(ProxiedPlayer player) {
            List<String> invites = BungeeSystem.instance.getJedis().lrange("friend-invites", 0, 2);
        }
    }

}

