package fun.listenia.mongolib.assistants;

import com.mongodb.client.MongoCollection;
import fun.listenia.mongolib.utils.Update;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Updater {

    private final MongoCollection<Document> collection;
    private final Document query;
    private final List<Update> updates;

    public Updater (MongoCollection<Document> collection, Document query) {
        this.collection = collection;
        this.query = query;
        this.updates = new ArrayList<>();
    }

    public void remove () {
        collection.deleteMany(query);
    }

    // recreate all methods from Update class
    public void set (String field, Object value) {
        updates.add(Update.SET(field, value));
    }

    public void unset (String field) {
        updates.add(Update.UNSET(field));
    }

    public void increment (String field, int value) {
        updates.add(Update.INCREMENT(field, value));
    }

    public void decrement (String field, int value) {
        updates.add(Update.DECREMENT(field, value));
    }

    public void multiply (String field, int value) {
        updates.add(Update.MULTIPLY(field, value));
    }

    public void divide (String field, int value) {
        updates.add(Update.DIVIDE(field, value));
    }

    public void modulo (String field, int value) {
        updates.add(Update.MODULO(field, value));
    }

    public void pushList (String field, Object value) {
        updates.add(Update.PUSH_LIST(field, value));
    }

    public void pushList (String field, Object... value) {
        updates.add(Update.PUSH_LIST(field, value));
    }

    public void pushList (String field, Update.Position position, Object... value) {
        updates.add(Update.PUSH_LIST(field, position, value));
    }

    public void pullList (String field, Object value) {
        updates.add(Update.PULL_LIST(field, value));
    }

    public void pullList (String field, Object... value) {
        updates.add(Update.PULL_LIST(field, value));
    }

    public void popList (String field) {
        updates.add(Update.POP_LIST(field, Update.Position.LAST));
    }

    public void popList (String field, Update.Position position) {
        updates.add(Update.POP_LIST(field, position));
    }







    public Document build () {
        return Update.build(updates.toArray(new Update[0]));
    }

    public void execute () {
        collection.updateOne(query, build());
    }

}
