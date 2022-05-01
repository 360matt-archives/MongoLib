package fun.listenia.mongolib.builders;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class SortBuilder {

    private boolean changed = false;
    protected Map<String, Object> sort = new HashMap<>();

    public SortBuilder asc (String field) {
        changed = true;
        sort.put(field, 1);
        return this;
    }

    public SortBuilder desc (String field) {
        changed = true;
        sort.put(field, -1);
        return this;
    }

    // text search similarity
    public SortBuilder text (String field, String search) {
        changed = true;
        sort.put(field, new Document("$text", new Document("$search", search)));
        return this;
    }

    public SortBuilder text (String field, String search, double minScore) {
        changed = true;
        sort.put(field, new Document("$text", new Document("$search", search).append("$minScore", minScore)));
        return this;
    }

    public SortBuilder near (String field, double x, double y) {
        changed = true;
        sort.put(field, new Document("$near", new Document("$geometry", new Document("type", "Point").append("coordinates", new double[]{x, y}))));
        return this;
    }

    public SortBuilder near (String field, double x, double y, double maxDistance) {
        changed = true;
        sort.put(field, new Document("$near", new Document("$geometry", new Document("type", "Point").append("coordinates", new double[]{x, y})).append("$maxDistance", maxDistance)));
        return this;
    }

    public Document build () {
        return new Document(sort);
    }

    public boolean isChanged () {
        return changed;
    }

}
