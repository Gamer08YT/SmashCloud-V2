package net.smashmc.cloud.master.utils;

import net.smashmc.cloud.master.Master;

public class Shutdown implements Runnable{
    @Override
    public void run(){
        Master.instance.getServer().close();
        Master.instance.getLogger().log( "Netty-Server stopped" );
        Master.instance.getProxyHandler().stopAllProxies();
        Master.instance.getLogger().log( "Proxy's were stopped" );
    }
}
