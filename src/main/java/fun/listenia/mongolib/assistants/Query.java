package fun.listenia.mongolib.assistants;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import fun.listenia.mongolib.Manager;
import fun.listenia.mongolib.builders.QueryBuilder;
import fun.listenia.mongolib.converters.Element;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Query<T extends Element> {

    private final Manager<?> manager;
    private final MongoCollection<Document> collection;
    private final QueryBuilder queryBuilder;

    public Query (Manager<T> manager, @NotNull Consumer<QueryBuilder> queryConsumer) {
        this.manager = manager;
        this.collection = manager.getCollection();
        this.queryBuilder = new QueryBuilder();
        queryConsumer.accept(queryBuilder);
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

    public void delete () {
        this.collection.deleteMany(this.queryBuilder.build());
    }

    public Query<T> delete (@NotNull Consumer<Void> consumer) {
        consumer.accept(null);
        return this;
    }

}
