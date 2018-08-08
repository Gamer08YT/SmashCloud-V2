package net.smashmc.modules.mongodb.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Credential {
    private final String hostname;
    private final String database;
}
