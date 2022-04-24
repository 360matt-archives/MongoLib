package fun.listenia.mongolib;

import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

public class Element {

    private final Manager manager;
    private final Document query;

    private Document document;

    public Element (Manager manager, Document query) {
        this.manager = manager;
        this.query = query;
    }

    public void load () {
        this.document = this.manager.getCollection().find(query).first();
        if (this.document == null)
            throw new RuntimeException("Element not found " + query);
    }

    public Document getDocument () {
        return this.document;
    }

    public void save () {
        this.manager.getCollection().replaceOne(query, document, new UpdateOptions().upsert(true));
    }




}
