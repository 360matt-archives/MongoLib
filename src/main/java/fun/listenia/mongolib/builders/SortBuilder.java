package fun.listenia.mongolib.builders;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class SortBuilder {

    private boolean changed = false;
    protected Map<String, Object> sort = new HashMap<>();

    public SortBuilder asc (String field) {
        sort.put(field, 1);
        return this;
    }

    public SortBuilder desc (String field) {
        sort.put(field, -1);
        return this;
    }

    // text search similarity
    public SortBuilder text (String field, String search) {
        sort.put(field, new Document("$text", new Document("$search", search)));
        return this;
    }

    public Document build () {
        return new Document(sort);
    }

    public boolean isChanged () {
        return changed;
    }

}
