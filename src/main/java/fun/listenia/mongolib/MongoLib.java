package fun.listenia.mongolib;


import com.mongodb.ConnectionString;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.jetbrains.annotations.NotNull;

public class MongoLib {

    private static MongoAuth mongoAuth;
    private static MongoClient mongoClient;

    public static void init (@NotNull MongoAuth auth) {
        MongoLib.mongoAuth = auth;
        final MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(auth.getConnectionString()))
                .retryWrites(true)
                .build();

        MongoLib.mongoClient = MongoClients.create(settings);
    }

    @NotNull
    public static MongoClient getClient() {
        if (mongoClient == null)
            throw new IllegalStateException("MongoLib is not initialized");
        return mongoClient;
    }

    @NotNull
    public static MongoDatabase getDatabase (@NotNull String dbName) {
        MongoDatabase database = getClient().getDatabase(dbName);
        return getClient().getDatabase(dbName);
    }

}
