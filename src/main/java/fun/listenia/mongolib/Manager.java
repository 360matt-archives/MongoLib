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

    @NotNull
    private static MongoCollection<Document> getOrCreate (@NotNull MongoDatabase db, @NotNull String name) {
        for (final String candidate : db.listCollectionNames())
            if (candidate.equalsIgnoreCase(name))
                return db.getCollection(name);
        db.createCollection(name);
        return db.getCollection(name);
    }

    private final MongoCollection<Document> collection;
    private T instance;

    public Manager (final MongoLib mongoLib, String dbName, final String collection, final Class<T> struc) {
        MongoDatabase db = mongoLib.getDatabase(dbName);
        this.collection = getOrCreate(db, collection);
        Cache.registerIndexs(Cache.getClassCache(struc), this.collection);
        try {
            this.instance = struc.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public Manager (String dbName, final String collection, final Class<T> struc) {
        this(MongoLib.DEFAULT, dbName, collection, struc);
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
        return new Query<>(this, consumer);
    }

    public Query<T> query (Document doc) {
        return new Query<>(this, doc);
    }

    public Update<T> update (BiConsumer<QueryBuilder, UpdateBuilder> consumer) {
        return new Update<>(this, consumer);
    }

    public boolean exists (Document doc) {
        return collection.countDocuments(doc) > 0;
    }

    public boolean exists (@NotNull Consumer<QueryBuilder> consumer) {
        Query<T> query = new Query<>(this, consumer);
        return query.exists();
    }

    public void insert (@NotNull T t) {
        Document doc = t.toDocument();
        this.collection.insertOne(doc);
        t.defineID(doc.getObjectId("_id").toString());
        t.defineCollection(this.collection);
    }

    public T insert (@NotNull Consumer<T> consumer) {
        T t = getInstance();
        consumer.accept(t);
        this.insert(t);
        return t;
    }

    public void delete (@NotNull Consumer<QueryBuilder> consumer) {
        Query<T> query = new Query<>(this, consumer);
        query.delete();
    }

    public void deleteAll () {
        this.collection.deleteMany(new Document());
    }



}
