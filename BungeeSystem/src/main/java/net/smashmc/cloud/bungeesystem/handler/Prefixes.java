package net.smashmc.cloud.bungeesystem.handler;

public enum Prefixes{

    PREFIX( " §e§lSMASHMC§8.§6§lEU §8» " ), REPORT( " §c§lReport §8| " ), CLOUD(" §c§lCLOUD §8§l| "), FRIEND(" §c§lFreunde §8§l| "), CLAN(" §e§lClan §8§l| ");

    private final String text;

    Prefixes( final String text ){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
