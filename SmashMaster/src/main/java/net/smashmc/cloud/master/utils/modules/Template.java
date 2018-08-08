package net.smashmc.cloud.master.utils.modules;

/**
 * @author Mike
 * @version 1.0
 */
public class Template {

    private String template;
    private int onlineSize;
    private int minRam;
    private int online;
    private int minOnline;

    /**
     * Create a new Template Object
     * @param template the Template Name
     * @param onlineSize the Onlinesize how many server start of this group
     * @param minRam the min ram;
     * @param online the size of online server
     * @param minOnline
     */
    public Template(String template, int onlineSize, int minRam, int online, int minOnline) {
        this.template = template;
        this.onlineSize = onlineSize;
        this.minRam = minRam;
        this.online = online;
        this.minOnline = minOnline;
    }

    public String getTemplate() {
        return template;
    }

    public int getMinRam() {
        return minRam;
    }

    public int getOnline() {
        return online;
    }

    public int getOnlineSize() {
        return onlineSize;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setMinRam(int minRam) {
        this.minRam = minRam;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public void setOnlineSize(int onlineSize) {
        this.onlineSize = onlineSize;
    }

    public int getMinOnline() {
        return minOnline;
    }

    public void setMinOnline(int minOnline) {
        this.minOnline = minOnline;
    }
}
