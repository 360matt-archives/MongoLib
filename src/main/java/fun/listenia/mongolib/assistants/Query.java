package fun.listenia.mongolib.assistants;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import fun.listenia.mongolib.builders.ProjectionBuilder;
import fun.listenia.mongolib.builders.QueryBuilder;
import fun.listenia.mongolib.builders.SortBuilder;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class Query {

    private final MongoCollection<Document> collection;
    private final QueryBuilder queryBuilder;

    private final ProjectionBuilder projectionBuilder;
    private final SortBuilder sortBuilder;
    private int skip;
    private int limit;

    public Query (MongoCollection<Document> collection, @NotNull Consumer<QueryBuilder> queryConsumer) {
        this.collection = collection;
        this.queryBuilder = new QueryBuilder();
        queryConsumer.accept(queryBuilder);
        this.projectionBuilder = new ProjectionBuilder();
        this.sortBuilder = new SortBuilder();
    }

    public FindIterable<Document> getQuery () {
        FindIterable<Document> iterable = this.collection.find(this.queryBuilder.build());
        if (this.skip > 0)
            iterable = iterable.skip(this.skip);
        if (this.limit > 0)
            iterable = iterable.limit(this.limit);
        if (this.sortBuilder.isChanged())
            iterable = iterable.sort(this.sortBuilder.build());
        if (this.projectionBuilder.isChanged())
            iterable = iterable.projection(this.projectionBuilder.build());

        return iterable;
    }

    public Query skip (@NotNull Integer skip) {
        this.skip = skip;
        return this;
    }

    public Query limit (@NotNull Integer limit) {
        this.limit = limit;
        return this;
    }

    public SortBuilder sort () {
        return this.sortBuilder;
    }

    public ProjectionBuilder projection () {
        return this.projectionBuilder;
    }

    public Document value () {
        return getQuery().first();
    }

    public Query value (@NotNull Consumer<Document> consumer) {
        consumer.accept(this.value());
        return this;
    }

    public List<Document> values () {
        return getQuery().into(new java.util.ArrayList<>());
    }

    public Query values (@NotNull Consumer<List<Document>> consumer) {
        consumer.accept(this.values());
        return this;
    }

    // count
    public long count () {
        return this.collection.countDocuments(this.queryBuilder.build());
    }

    public Query count (@NotNull Consumer<Long> consumer) {
        consumer.accept(this.count());
        return this;
    }

    public void delete () {
        this.collection.deleteMany(this.queryBuilder.build());
    }

    public Query delete (@NotNull Consumer<Void> consumer) {
        consumer.accept(null);
        return this;
    }

}
