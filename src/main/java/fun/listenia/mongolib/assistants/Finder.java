package fun.listenia.mongolib.assistants;

import com.mongodb.client.MongoCollection;
import fun.listenia.mongolib.utils.Criteria;
import fun.listenia.mongolib.utils.Query;
import fun.listenia.mongolib.utils.Sort;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class Finder {

    private final MongoCollection<Document> collection;
    private final Set<Criteria> criterias;
    private final Set<Sort> sorts;
    private int limit;
    private int skip;

    public Finder (final MongoCollection<Document> collection) {
        this.collection = collection;
        this.criterias = new HashSet<>(0);
        this.sorts = new HashSet<>(0);
        this.limit = 0;
        this.skip = 0;
    }

    public Finder addQuery (final Query... queries) {
        for (final Query query : queries) {
            if (query instanceof Criteria)
                criterias.add((Criteria) query);
            if (query instanceof Sort)
                sorts.add((Sort) query);
        }
        return this;
    }

   public Finder limit (final int limit) {
        this.limit = limit;
        return this;
    }

    public Finder skip (final int skip) {
        this.skip = skip;
        return this;
    }

    @NotNull
    public Document buildQuery () {
        return Criteria.build(criterias.toArray(new Criteria[0]));
    }

    @NotNull
    public Document buildSort () {
        return Sort.build(sorts.toArray(new Sort[0]));
    }

    public Document value () {
        return collection.find(buildQuery()).first();
    }

    public List<Document> values () {
        return collection.find(buildQuery())
                .sort(buildSort())
                .limit(limit)
                .into(new ArrayList<>());
    }

    public long count () {
        return collection.count(buildQuery());
    }

    public boolean exists () {
        return collection.count(buildQuery()) > 0;
    }

    public boolean notExists () {
        return collection.count(buildQuery()) == 0;
    }

    public Finder update (Consumer<Updater> consumer) {
        final Updater u = new Updater(collection, buildQuery());
        consumer.accept(u);
        collection.updateOne(buildQuery(), u.build());
        return this;
    }

    public Document valueAndUpdate (Consumer<Updater> consumer) {
        Document query = buildQuery();
        Document value = collection.find(query).first();

        final Updater u = new Updater(collection, query);
        consumer.accept(u);
        collection.updateOne(buildQuery(), u.build());
        return value;
    }

    public List<Document> valuesAndUpdate (Consumer<Updater> consumer) {
        List<Document> values = values();
        final Updater u = new Updater(collection, buildQuery());
        consumer.accept(u);
        collection.updateOne(buildQuery(), u.build());
        return values;
    }






}
