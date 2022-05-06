package fun.listenia.mongolib;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fun.listenia.mongolib.assistants.Query;
import fun.listenia.mongolib.assistants.Update;
import fun.listenia.mongolib.builders.QueryBuilder;
import fun.listenia.mongolib.builders.UpdateBuilder;
import fun.listenia.mongolib.converters.Element;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class Manager <T extends Element> {

    public static MongoCollection<Document> getOrCreate (MongoDatabase db, String name) {
        for (final String candidate : db.listCollectionNames())
            if (candidate.equalsIgnoreCase(name))
                return db.getCollection(name);
        db.createCollection(name);
        return db.getCollection(name);
    }

    private final MongoCollection<Document> collection;
    private T instance;

    public Manager (final String dbName, final String collection, final Class<T> struc) {
        MongoDatabase db = MongoLib.getDatabase(dbName);
        this.collection = getOrCreate(db, collection);
        Cache.registerIndexs(Cache.getClassCache(struc), this.collection);
        try {
            this.instance = struc.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public T getInstance () {
        T inst = (T) instance.clone();
        inst.defineCollection(this.collection);
        return inst;
    }

    public MongoCollection<Document> getCollection () {
        return collection;
    }

    public Query<T> query (Consumer<QueryBuilder> consumer) {
        return new Query<T>(this, consumer);
    }

    public Update<T> update (BiConsumer<QueryBuilder, UpdateBuilder> consumer) {
        return new Update<>(this.collection, consumer);
    }

    public void insert (@NotNull T t) {
        Document doc = t.toDocument();
        this.collection.insertOne(doc);
        t.defineID(doc.getObjectId("_id").toString());
        t.defineCollection(this.collection);
    }



}
