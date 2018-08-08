package net.smashmc.cloud.bungeesystem.handler.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Report {

    private String playerName;
    private String targetName;
    private String reason;


}
