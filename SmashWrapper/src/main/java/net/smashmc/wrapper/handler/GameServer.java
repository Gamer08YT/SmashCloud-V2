package net.smashmc.wrapper.handler;

import net.smashmc.wrapper.Wrapper;
import net.smashmc.wrapper.data.ServerData;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer{

    private ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Start a new gameserver
     *
     * @param serverData create a new server object
     */
    public void startGameServer( ServerData serverData ){
        executorService.execute( () -> {


            try{
                FileUtils.deleteDirectory( new File( "local/Servers/" + serverData.getName() ) );
                FileUtils.copyDirectory( new File( "local/Templates/" + serverData.getTemplate() ), new File( "local/Servers/" + serverData.getName() ) );
                FileUtils.copyDirectory( new File( "local/jars/spigot_1.8/" ), new File( "local/Servers/" + serverData.getName() ) );
            } catch (IOException e){
                e.printStackTrace();
            }
            copyMap( serverData );
            try{

                runProcess( serverData );

            } catch (IOException e){
                e.printStackTrace();
            }
            Wrapper.servers.put( serverData.getName(), serverData );
        } );
    }

    /**
     * Kill all Server and destroy the process
     */
    public void kill(){
        if ( !Wrapper.servers.isEmpty() ){
            Iterator<String> stringIterator = Wrapper.servers.keySet().iterator();
            while ( stringIterator.hasNext() ){
                String name = stringIterator.next();
                stopByName( name );
                stringIterator.remove();
            }
        }
    }

    /**
     * Stop a server by with name
     *
     * @param serverName the servername which you want to stop
     * @throws IOException
     */
    public void stopByName( String serverName ){
        if ( Wrapper.servers.containsKey( serverName ) ){
            ServerData serverData = Wrapper.servers.get( serverName );

            Wrapper.servers.remove( serverName );

            try{
                if ( serverData.getProcess().isAlive() ){
                    serverData.getProcess().getOutputStream().write( "stop\n".getBytes() );
                    serverData.getProcess().getOutputStream().flush();
                    serverData.getProcess().waitFor();
                }
            } catch (InterruptedException | IOException e){
                e.printStackTrace();
            }

            try{
                FileUtils.deleteDirectory( new File( "local/Servers/" + serverData.getName() ) );
            } catch (IOException e){
                e.printStackTrace();
            }
            System.out.println( "Server named " + serverName + "stopped by Wrapper (" + Wrapper.instance.getWrapperName() + ")" );

        }
    }

    /**
     * Run the gameserver process
     *
     * @param serverData create a new server object
     * @throws IOException
     */
    private void runProcess( ServerData serverData ) throws IOException{
        ProcessBuilder processBuilder = new ProcessBuilder( "java", "-Xms" + serverData.getMinram() + "M", "-Xmx" + 1024 + "M", "-jar", "spigot.jar", "--port", String.valueOf( serverData.getPort() ) );
        processBuilder.directory( new File( "local/Servers/" + serverData.getName() ) );
        Process process = processBuilder.start();
        serverData.setProcess( process );
    }

    private void copyMap( ServerData serverData ){
        if ( !serverData.getTemplate().equalsIgnoreCase( "Lobby" ) ){
            File[] files = new File( "local/maps/" + serverData.getTemplate() ).listFiles();
            Random random = new Random();
            File file = files[ random.nextInt( files.length ) ];
            File server = new File( "local/Servers/" + serverData.getName() + "/" + file.getName() );
            try{
                FileUtils.copyDirectory( file, server );
            } catch (IOException e){
                e.printStackTrace();
            }
            System.out.println( "copy " + file + " to " + server );
        }
    }
}
