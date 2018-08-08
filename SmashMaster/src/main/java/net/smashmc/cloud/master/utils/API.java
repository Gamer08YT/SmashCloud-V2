package net.smashmc.cloud.master.utils;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class API {

    private String name;
    private Channel channel;

}
