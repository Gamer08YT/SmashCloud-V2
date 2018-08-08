package net.smashmc.modules.mongodb.storage;

import org.bson.Document;

public interface Database {

    void insert(final Document document);
    void updateOne(final Document old, final Document newOne);
    void deleteOne(final Document document);

}
