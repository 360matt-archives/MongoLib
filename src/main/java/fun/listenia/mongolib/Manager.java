package fun.listenia.mongolib;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fun.listenia.mongolib.assistants.Query;
import fun.listenia.mongolib.assistants.Update;
import fun.listenia.mongolib.builders.QueryBuilder;
import fun.listenia.mongolib.builders.UpdateBuilder;
import org.bson.Document;

import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class Manager {

    public static MongoCollection<Document> getOrCreate (MongoDatabase db, String name) {
        for (final String candidate : db.listCollectionNames())
            if (candidate.equalsIgnoreCase(name))
                return db.getCollection(name);
        db.createCollection(name);
        return db.getCollection(name);
    }

    private final MongoCollection<Document> collection;

    public Manager (final String dbName, final String collection) {
        MongoDatabase db = MongoLib.getDatabase(dbName);
        this.collection = getOrCreate(db, collection);
    }

    public MongoCollection<Document> getCollection () {
        return collection;
    }

    public Query query (Consumer<QueryBuilder> consumer) {
        return new Query(this.collection, consumer);
    }

    public Update update (BiConsumer<QueryBuilder, UpdateBuilder> consumer) {
        return new Update(this.collection, consumer);
    }



}
