package fun.listenia.mongolib.assistants;

import com.mongodb.client.MongoCollection;
import fun.listenia.mongolib.builders.QueryBuilder;
import fun.listenia.mongolib.builders.UpdateBuilder;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Update {

    private final MongoCollection<Document> collection;
    private final QueryBuilder queryBuilder;
    private final UpdateBuilder updateBuilder;

    public Update (MongoCollection<Document> collection, @NotNull BiConsumer<QueryBuilder, UpdateBuilder> queryBuilderConsumer) {
        this.collection = collection;
        this.queryBuilder = new QueryBuilder();
        this.updateBuilder = new UpdateBuilder();
        queryBuilderConsumer.accept(queryBuilder, updateBuilder);
    }

    public void execute () {
        collection.updateMany(queryBuilder.build(), updateBuilder.build());
    }

    public Update execute (@NotNull Consumer<Void> consumer) {
        execute();
        consumer.accept(null);
        return this;
    }

    public Document valueAndExecute () {
        return collection.findOneAndUpdate(queryBuilder.build(), updateBuilder.build());
    }

    public Update valueAndExecute (@NotNull Consumer<Document> consumer) {
        Document document = valueAndExecute();
        consumer.accept(document);
        return this;
    }

    public Document executeAndValue () {
        execute();
        return collection.findOneAndDelete(queryBuilder.build());
    }

    public Update executeAndValue (@NotNull Consumer<Document> consumer) {
        Document document = executeAndValue();
        consumer.accept(document);
        return this;
    }

    public List<Document> valuesAndExecute () {
        List<Document> documents = collection.find(queryBuilder.build()).into(new java.util.ArrayList<>());
        this.collection.updateMany(queryBuilder.build(), updateBuilder.build());
        return documents;
    }

    public Update valuesAndExecute (@NotNull Consumer<List<Document>> consumer) {
        List<Document> documents = valuesAndExecute();
        consumer.accept(documents);
        return this;
    }

    public List<Document> executeAndValues () {
        this.collection.updateMany(queryBuilder.build(), updateBuilder.build());
        return collection.find(queryBuilder.build()).into(new java.util.ArrayList<>());
    }

    public Update executeAndValues (@NotNull Consumer<List<Document>> consumer) {
        List<Document> documents = executeAndValues();
        consumer.accept(documents);
        return this;
    }






}
