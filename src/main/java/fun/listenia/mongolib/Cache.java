package fun.listenia.mongolib;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import fun.listenia.mongolib.converters.Index;
import fun.listenia.mongolib.converters.Required;
import org.bson.Document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class Cache {

    public static class ClassCache {
        public Field[] fieldsCache;
        public Field[] requiredFieldsCache;
        public FieldAccess access;

        public ClassCache (Class<?> clazz) {
            this.access = FieldAccess.get(clazz);
            final List<Field> fieldCacheList = new ArrayList<>();
            for (final Field field : clazz.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    fieldCacheList.add(field);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.fieldsCache = fieldCacheList.toArray(new Field[0]);
            Cache.registerRequired(this);
        }
    }

    public static class FieldCache {
        protected Field field;
       // protected String name;
      //  protected Class<?> type;
    }

     private static final HashMap<Class<?>, ClassCache> classCaches = new HashMap<>();


    public static ClassCache getClassCache (Class<?> clazz) {
        ClassCache classCache = classCaches.get(clazz);
        if (classCache == null) {
            classCache = new ClassCache(clazz);
            classCaches.put(clazz, classCache);
        }
        return classCache;
    }

    public static void registerIndexs (ClassCache classCache, MongoCollection<Document> collection) {
        for (final Field field : classCache.fieldsCache) {
            final AtomicReference<Index.Type> already = new AtomicReference<>();
            // check if index is already created
            collection.listIndexes().forEach((Consumer<? super Document>) index -> {
                Document keys = (Document) index.get("key");
                if (keys.containsKey(field.getName())) {
                    if (keys.get(field.getName()).equals(1)) {
                        already.set(Index.Type.SINGLE);
                    } else if (keys.get(field.getName()).equals("text")) {
                        already.set(Index.Type.TEXT);
                    } else if (keys.get(field.getName()).equals("2d")) {
                        already.set(Index.Type._2D);
                    } else if (keys.get(field.getName()).equals("2dsphere")) {
                        already.set(Index.Type._2DSPHERE);
                    }
                }
            });

            boolean isRequired = field.isAnnotationPresent(Required.class);

            if (field.isAnnotationPresent(Index.class)) {
                Index index = field.getAnnotation(Index.class);
                Index.Type type = index.type();
                boolean unique = index.unique();

                IndexOptions options = new IndexOptions().unique(unique).sparse(isRequired);
                if (type == Index.Type.SINGLE) {
                    if (already.get() != null && already.get() != Index.Type.SINGLE)
                        collection.dropIndex(field.getName());
                    collection.createIndex(new Document(field.getName(), 1), options);
                } else if (type == Index.Type.TEXT) {
                    if (already.get() != null && already.get() != Index.Type.TEXT)
                        collection.dropIndex(field.getName());
                    collection.createIndex(new Document(field.getName(), "text"), options);
                } else if (type == Index.Type._2D) {
                    if (already.get() != null && already.get() != Index.Type._2D)
                        collection.dropIndex(field.getName());
                    collection.createIndex(new Document(field.getName(), "2d"), options);
                } else if (type == Index.Type._2DSPHERE) {
                    if (already.get() != null && already.get() != Index.Type._2DSPHERE)
                        collection.dropIndex(field.getName());
                    collection.createIndex(new Document(field.getName(), "2dsphere"), options);
                }

            }
        }
    }

    public static void registerRequired (ClassCache classCache) {
        List<Field> fields = new ArrayList<>();
        for (final Field field : classCache.fieldsCache) {
            if (field.isAnnotationPresent(Required.class)) {
                fields.add(field);
            }
        }
        classCache.requiredFieldsCache = fields.toArray(new Field[0]);
    }

}
