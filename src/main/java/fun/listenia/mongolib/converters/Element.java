package fun.listenia.mongolib.converters;

import com.mongodb.client.MongoCollection;
import fun.listenia.mongolib.Cache;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.reflect.Field;

public class Element implements Cloneable {

    private String _id;
    private MongoCollection<Document> collection;

    public void defineID (String id) {
        this._id = id;
    }

    public void defineCollection (MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public String getID () {
        return _id;
    }

    public void update () {
        if (this._id == null)
            throw new IllegalStateException("'_id' is not defined");
        this.collection.replaceOne(new Document("_id", new ObjectId(this._id)), this.toDocument());
    }

    public void remove () {
        if (this._id == null)
            throw new IllegalStateException("'_id' is not defined");
        this.collection.deleteOne(new Document("_id", this._id));
    }

    public void fromDocument (Document document) {
        if (document.containsKey("_id"))
            this._id = document.get("_id").toString();
        try {
            this.fromSubDocument(this, document);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void fromSubDocument (@NotNull Element element, Document document) throws CloneNotSupportedException {
        Cache.ClassCache classCache = Cache.getClassCache(element.getClass());
        for (Field field : classCache.fieldsCache) {
            if (field.getType().isAssignableFrom(Document.class)) {
                Element sub = (Element) this.clone();
                sub.fromSubDocument(sub, document);
                classCache.access.set(element, field.getName(), sub);
            } else {
                Object value = null;
                if (document.containsKey(field.getName()))
                    value = document.get(field.getName());
                if (value == null)
                    continue;
                classCache.access.set(element, field.getName(), value);
            }
        }
    }


    public Document toDocument () {
        return this.toSubDocument(this);
    }

    @NotNull
    private Document toSubDocument (@NotNull Element element) {
        Document document = new Document();
        Cache.ClassCache classCache = Cache.getClassCache(element.getClass());

        for (Field field : classCache.fieldsCache) {

            Class<?> superClazz = field.getType().getSuperclass();
            if (superClazz != null && Element.class.isAssignableFrom(superClazz)) {
                Element sub = (Element) classCache.access.get(element, field.getName());
                if (sub == null)
                    continue;
                document.append(field.getName(), this.toSubDocument(sub));
            } else {
                Object value = classCache.access.get(element, field.getName());
                if (value == null) {
                    for (Field superField : classCache.requiredFieldsCache) {
                        if (superField.getName().equals(field.getName())) {
                            throw new IllegalStateException("Field '" + field.getName() + "' is required");
                        }
                    }
                    continue;
                }
                if (!(value instanceof Serializable)) {
                    throw new IllegalArgumentException("Field '" + field.getName() + "' is not serializable");
                }
                document.append(field.getName(), value);
            }
        }
        return document;
    }


    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
