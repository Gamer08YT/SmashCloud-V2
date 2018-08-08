package net.smashmc.cloud.master.handler;

import lombok.Getter;
import lombok.Setter;
import net.smashmc.cloud.master.Master;
import net.smashmc.cloud.master.packets.PacketOutAddServer;
import net.smashmc.cloud.master.utils.modules.Template;
import net.smashmc.cloud.master.utils.types.Server;
import net.smashmc.cloud.master.utils.types.Wrapper;
import org.bson.Document;

import java.util.*;

/**
 * @author Mike
 * @version 1.0
 */

@Setter
@Getter
public class CloudHandler{

    /**
     * Load the templates from Database and write the outgoing packets
     */
    public void loadGroups(){
        for ( Document all : Master.instance.getDatabaseConnection().getCollection( "cloud" ).find() ){
            if ( !all.getString( "template" ).equalsIgnoreCase( "Proxy" ) ){
                Template template = new Template( all.getString( "template" ), all.getInteger( "onlineSize" ), all.getInteger( "minRam" ), 0, all.getInteger( "minOnline" ) );
                Master.instance.getTemplates().add( template );
            }
        }
        Master.instance.getTemplates().forEach( template -> {
            for ( int i = 0; i < template.getOnlineSize(); i++ ){

                startServer( template );
            }
        } );
    }

    /**
     * Start a new Server with Template name
     *
     * @param template Create the Template object
     */
    public void startServer( Template template ){
        Optional<Wrapper> optional = Master.instance.getWrapperHandler().wrappers.stream().filter( wrapper -> wrapper.getUsableRam() >= template.getMinRam() ).sorted( Comparator.comparingInt( Wrapper::getUsableRam ) ).findFirst();
        if ( optional.isPresent() ){
            Wrapper wrapper = optional.get();
            int id = 1;

            loop:
            while ( true ){
                for ( Wrapper w : Master.instance.getWrapperHandler().wrappers ){
                    for ( Server server : w.getServers() ){
                        if ( server.getTemplate().equals( template ) ){
                            if ( server.getName().equals( template.getTemplate() + "-" + id ) ){
                                id++;
                                continue loop;
                            }
                        }
                    }
                }

                break;
            }

            Server server = new Server( template.getTemplate() + "-" + id, template, wrapper.getFreePort(), template.getMinRam());
            Master.instance.getServers().add( server );
            wrapper.getServers().add( server );
            wrapper.setUsableRam( wrapper.getUsableRam() - template.getMinRam() );
            template.setOnline( template.getOnline() + 1 );
            wrapper.getChannel().writeAndFlush( new PacketOutAddServer( server ), wrapper.getChannel().voidPromise() );
            Master.instance.getProxyHandler().bungeecords.forEach( bungeecord -> bungeecord.getChannel().writeAndFlush( new PacketOutAddServer( server ) ) );
            Master.instance.getLogger().log( String.format( "Group [%s, %s, %s] bound on Wrapper (%s)", server.getName(), server.getMinRam(), server.getPort(), wrapper.getName() ) );
        }
    }
}
