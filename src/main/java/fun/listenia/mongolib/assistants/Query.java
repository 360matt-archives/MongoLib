package fun.listenia.mongolib.assistants;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import fun.listenia.mongolib.Manager;
import fun.listenia.mongolib.builders.QueryBuilder;
import fun.listenia.mongolib.converters.Element;
import org.bson.Document;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Query<T extends Element> {

    private final Manager<?> manager;
    private final MongoCollection<Document> collection;
    private final QueryBuilder queryBuilder;

    public Query (@NotNull Manager<T> manager, @NotNull Consumer<QueryBuilder> queryConsumer) {
        this.manager = manager;
        this.collection = manager.getCollection();
        this.queryBuilder = new QueryBuilder();
        queryConsumer.accept(queryBuilder);
    }

    public Query (@NotNull Manager<T> manager, @NotNull Document document) {
        this.manager = manager;
        this.collection = manager.getCollection();
        this.queryBuilder = new QueryBuilder(document);
    }

    public FindIterable<Document> getQuery () {
        FindIterable<Document> iterable = this.collection.find(this.queryBuilder.build());
        if (this.queryBuilder.getSkip() > 0)
            iterable = iterable.skip(this.queryBuilder.getSkip());
        if (this.queryBuilder.getLimit() > 0)
            iterable = iterable.limit(this.queryBuilder.getLimit());
        if (this.queryBuilder.sort().isChanged())
            iterable = iterable.sort(this.queryBuilder.sort().build());
        if (this.queryBuilder.projection().isChanged())
            iterable = iterable.projection(this.queryBuilder.projection().build());

        return iterable;
    }

    public Document value () {
        return getQuery().first();
    }

    public Query<T> value (@NotNull Consumer<Document> consumer) {
        consumer.accept(this.value());
        return this;
    }

    public T element () {
        T element = (T) manager.getInstance();
        element.fromDocument(this.value());
        return element;
    }

    public Query<T> element (@NotNull Consumer<T> consumer) {
        consumer.accept(this.element());
        return this;
    }

    public List<Document> values () {
        return getQuery().into(new java.util.ArrayList<>());
    }

    public Query<T> values (@NotNull Consumer<List<Document>> consumer) {
        consumer.accept(this.values());
        return this;
    }

    public List<T> elements () {
        List<T> elements = new ArrayList<>();
        getQuery().forEach((Consumer<Document>) document -> {
            T element = (T) manager.getInstance();
            element.fromDocument(document);
            elements.add(element);
        });
        return elements;
    }

    public Query<T> elements (@NotNull Consumer<List<T>> consumer) {
        consumer.accept(this.elements());
        return this;
    }

    // count
    public long count () {
        return this.collection.countDocuments(this.queryBuilder.build());
    }

    public Query<T> count (@NotNull Consumer<Long> consumer) {
        consumer.accept(this.count());
        return this;
    }

    // exists
    public boolean exists () {
        return this.collection.countDocuments(this.queryBuilder.build()) > 0;
    }

    public void delete () {
        this.collection.deleteMany(this.queryBuilder.build());
    }

    public Query<T> delete (@NotNull Consumer<Void> consumer) {
        consumer.accept(null);
        return this;
    }






    @NotNull
    @Contract("_, _ -> new")
    public static Document equals (String field, Object value) {
        return new Document(field, new Document("$eq", value));
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Document notEquals (String field, Object value) {
        return new Document(field, new Document("$ne", value));
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Document greaterThan (String field, Object value) {
        return new Document(field, new Document("$gt", value));
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Document greaterThanOrEquals (String field, Object value) {
        return new Document(field, new Document("$gte", value));
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Document lessThan (String field, Object value) {
        return new Document(field, new Document("$lt", value));
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Document lessThanOrEquals (String field, Object value) {
        return new Document(field, new Document("$lte", value));
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Document in (String field, Object... values) {
        return new Document(field, new Document("$in", values));
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Document notIn (String field, Object... values) {
        return new Document(field, new Document("$nin", values));
    }

    @NotNull
    @Contract("_ -> new")
    public static Document notExists (String field) {
        return new Document(field, new Document("$exists", false));
    }

    @NotNull
    @Contract("_ -> new")
    public static Document exists (String field) {
        return new Document(field, new Document("$exists", true));
    }

    @NotNull
    @Contract("_ -> new")
    public static Document isEmpty (String field) {
        return new Document(field, new Document("$size", new Document("$eq", 0)));
    }

    @NotNull
    @Contract("_ -> new")
    public static Document isNotEmpty (String field) {
        return new Document(field, new Document("$size", new Document("$gt", 0)));
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Document startsWith (String field, Object value) {
        return new Document(field, new Document("$regex", "^" + value));
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Document endsWith (String field, Object value) {
        return new Document(field, new Document("$regex", value + "$"));
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Document containsText (String field, Object value) {
        return new Document(field, new Document("$regex", ".*" + value + ".*"));
    }

}
