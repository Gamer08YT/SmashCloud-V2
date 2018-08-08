package net.smashmc.modules.mongodb.storage.connection;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import net.smashmc.modules.mongodb.storage.Credential;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseConnection{

    private MongoClient client;
    private MongoDatabase mongoDatabase;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private final Map<String, MongoCollection<Document>> collections;

    public DatabaseConnection(){
        this.collections = new HashMap<>();
    }

    public void openConnection( Credential credential ){
        this.client = new MongoClient( credential.getHostname(), 27017 );
        this.mongoDatabase = this.client.getDatabase( credential.getDatabase() );
        Logger mongoLogger = Logger.getLogger( "org.mongodb" );
        mongoLogger.setLevel( Level.OFF );
    }

    public MongoCollection<Document> getCollection( String collectionName ){
        if ( this.collections.containsKey( collectionName ) ){
            return this.collections.get( collectionName );
        } else{
            registerCollection( collectionName );
            return getCollection( collectionName );
        }
    }

    public void registerCollection( String collectionName ){
        if ( !this.collections.containsKey( collectionName ) ){
            this.collections.put( collectionName, this.mongoDatabase.getCollection( collectionName ) );
        }
    }

    public void insert( Document document, Document search, String collectionName ){
        executorService.execute( () -> {
            long count = getCollection( collectionName ).count( search );
            if ( count == 0 ){
                getCollection( collectionName ).insertOne( document );
            }
        } );
    }

    public String getString( String collectionName, String value, String uuid ){
        FindIterable<Document> find = getCollection( collectionName ).find( Filters.eq( "uuid", uuid ) );
        Document doc = find.first();

        if ( doc == null ){
            return null;
        }
        return doc.getString( value );

    }

    public boolean getBoolean( String collectionName, String value, String uuid, String fieldName ){
        FindIterable<Document> find = getCollection( collectionName ).find( Filters.eq( fieldName, uuid ) );
        Document doc = find.first();

        if ( doc == null ){
            return false;
        }
        return doc.getBoolean( value );

    }

    public void getBooleanAsync( String collectioName, String value, Consumer<Boolean> booleanConsumer, String uuid, String fieldName ){
        executorService.execute( () -> booleanConsumer.accept( getBoolean( collectioName, value, uuid, fieldName ) ) );
    }

    public void getStringAsync( String collectioName, String value, Consumer<String> string, String uuid ){
        executorService.execute( () -> string.accept( getString( collectioName, value, uuid ) ) );
    }

    public String getStringFieldName( String collectionName, String value, String uuid, String field ){
        FindIterable<Document> find = getCollection( collectionName ).find( Filters.eq( field, uuid ) );
        Document doc = find.first();

        if ( doc == null ){
            return null;
        }
        return doc.getString( value );

    }

    public void getStringAsyncField( String collectioName, String value, Consumer<String> string, String uuid, String fieldname ){
        executorService.execute( () -> string.accept( getStringFieldName( collectioName, value, uuid, fieldname ) ) );
    }

    public Integer getInteger( String collectionName, String value, String uuid ){
        System.out.println( collectionName );
        FindIterable<Document> find = getCollection( collectionName ).find( Filters.eq( "uuid", uuid ) );
        Document doc = find.first();
        if ( doc == null ){
            return null;

        }
        return doc.getInteger( value );

    }
    public void insertSettings( UUID uuid ){
        long count = getCollection( "settings" ).count( new Document( "uuid", uuid ) );
        if ( count == 0 )
            getCollection( "settings" ).insertOne( new Document( "uuid", uuid ).append( "nick", false ).append( "loggedin", false ) );
    }
}

