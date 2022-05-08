package fun.listenia.mongolib.builders;

import org.bson.Document;

import java.util.HashMap;

public class UpdateBuilder {

    protected HashMap<String, Document> updates = new HashMap<>();

    public void set (String key, Object value) {
        this.addToUpdates("$set", new Document(key, value));
    }

    public void unset (String key) {
        this.addToUpdates("$unset", new Document(key, 1));
    }

    public void inc (String key, int value) {
        this.addToUpdates("$inc", new Document(key, value));
    }

    public void decr (String key, int value) {
        this.addToUpdates("$inc", new Document(key, -value));
    }

    public void mul (String key, int value) {
        this.addToUpdates("$mul", new Document(key, value));
    }

    public void div (String key, int value) {
        this.addToUpdates("$div", new Document(key, value));
    }

    public void push (String key, Object... value) {
        this.addToUpdates("$push", new Document(key, value));
    }

    public void pull (String key, Object value) {
        this.pullAll(key, value);
    }

    public void pull (String key, Object... values) {
        this.pull(key, new Document("$in", values));
    }

    public void addToSet (String key, Object... value) {
        this.addToUpdates("$addToSet", new Document(key, value));
    }

    public void pop (String key, int value) {
        this.addToUpdates("$pop", new Document(key, value));
    }

    public void pullAll (String key, Object... values) {
        this.addToUpdates("$pullAll", new Document(key, values));
    }

    public void rename (String key, String newKey) {
        this.addToUpdates("$rename", new Document(key, newKey));
    }

    public void min (String key, Object value) {
        this.addToUpdates("$min", new Document(key, value));
    }

    public void max (String key, Object value) {
        this.addToUpdates("$max", new Document(key, value));
    }

    private void addToUpdates (String op, Document doc) {
        if (!this.updates.containsKey(op)) {
            this.updates.put(op, doc);
        } else {
            Document related = this.updates.get(op);
            related.putAll(doc);
        }
    }

    public Document build () {
        Document update = new Document();
        update.putAll(updates);
        return update;
    }

}
