package net.smashmc.cloud.master.server;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Template {

    private String template;
    private int onlinesize;
    private int minram;

}
