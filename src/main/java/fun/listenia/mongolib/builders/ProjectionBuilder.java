package fun.listenia.mongolib.builders;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class ProjectionBuilder {

    private boolean changed = false;
    protected Map<String, Object> projection = new HashMap<>();

    public void include (String field) {
        changed = true;
        projection.put(field, 1);
    }

    public void exclude (String field) {
        changed = true;
        projection.put(field, 0);
    }

    public Document build () {
        return new Document(projection);
    }

    public boolean isChanged () {
        return changed;
    }

}
