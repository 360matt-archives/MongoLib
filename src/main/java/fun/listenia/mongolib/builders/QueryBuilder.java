package fun.listenia.mongolib.builders;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class QueryBuilder {

    protected Map<String, Document> query = new HashMap<>();

    public void equals (String field, Object value) {
        this.addToQuery(field, new Document("$eq", value));
    }

    public void notEquals (String field, Object value) {
        this.addToQuery(field, new Document("$ne", value));
    }

    public void greaterThan (String field, Object value) {
        this.addToQuery(field, new Document("$gt", value));
    }

    public void greaterThanOrEquals (String field, Object value) {
        this.addToQuery(field, new Document("$gte", value));
    }

    public void lessThan (String field, Object value) {
        this.addToQuery(field, new Document("$lt", value));
    }

    public void lessThanOrEquals (String field, Object value) {
        this.addToQuery(field, new Document("$lte", value));
    }

    public void in (String field, Object[] values) {
        this.addToQuery(field, new Document("$in", values));
    }

    public void notIn (String field, Object[] values) {
        this.addToQuery(field, new Document("$nin", values));
    }

    public void isNull (String field) {
        this.addToQuery(field, new Document("$exists", false));
    }

    public void isNotNull (String field) {
        this.addToQuery(field, new Document("$exists", true));
    }

    public void isEmpty (String field) {
        this.addToQuery(field, new Document("$size", new Document("$eq", 0)));
    }

    public void isNotEmpty (String field) {
        this.addToQuery(field, new Document("$size", new Document("$gt", 0)));
    }

    public void startsWith (String field, Object value) {
        this.addToQuery(field, new Document("$regex", "^" + value));
    }

    public void endsWith (String field, Object value) {
        this.addToQuery(field, new Document("$regex", value + "$"));
    }

    public void contains (String field, Object value) {
        this.addToQuery(field, new Document("$regex", ".*" + value + ".*"));
    }

    private void addToQuery (String field, Document doc) {
        if (!this.query.containsKey(field)) {
            this.query.put(field, doc);
        } else {
            Document related = this.query.get(field);
            related.putAll(doc);
        }
    }

    public Document build () {
        Document doc = new Document();
        doc.putAll(this.query);
        return doc;
    }



}
