package fun.listenia.mongolib;


import com.mongodb.ConnectionString;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

public class MongoLib implements Closeable {

    public static MongoLib DEFAULT = new MongoLib();

    private MongoClient mongoClient;

    public void connect (@NotNull MongoAuth auth) {
        connect(auth.getConnectionString());
    }

    public void connect (@NotNull String auth) {
        final MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(auth))
                .retryWrites(true)
                .build();
        mongoClient = MongoClients.create(settings);
    }

    public boolean isConnected () {
        return mongoClient != null;
    }

    @Override
    public void close () {
        mongoClient.close();
        mongoClient = null;
    }

    @NotNull
    public MongoClient getClient() {
        if (mongoClient == null)
            throw new IllegalStateException("MongoLib is not initialized");
        return mongoClient;
    }

    @NotNull
    public MongoDatabase getDatabase (@NotNull String dbName) {
        return getClient().getDatabase(dbName);
    }

}
