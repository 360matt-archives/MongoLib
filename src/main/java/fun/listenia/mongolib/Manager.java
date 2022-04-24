package fun.listenia.mongolib;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fun.listenia.mongolib.assistants.Finder;
import fun.listenia.mongolib.utils.Query;
import org.bson.Document;


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

    public Finder finder () {
        return new Finder(collection);
    }

    public Finder finder (Query... criterias) {
        return new Finder(collection).addQuery(criterias);
    }


}
