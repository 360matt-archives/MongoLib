package fun.listenia.mongolib.assistants;

import com.mongodb.client.MongoCollection;
import fun.listenia.mongolib.Manager;
import fun.listenia.mongolib.builders.QueryBuilder;
import fun.listenia.mongolib.builders.UpdateBuilder;
import fun.listenia.mongolib.converters.Element;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Update<T extends Element> {

    private final Manager<T> manager;
    private final MongoCollection<Document> collection;
    private final QueryBuilder queryBuilder;
    private final UpdateBuilder updateBuilder;

    public Update (Manager<T> manager, @NotNull BiConsumer<QueryBuilder, UpdateBuilder> queryBuilderConsumer) {
        this.manager = manager;
        this.collection = manager.getCollection();
        this.queryBuilder = new QueryBuilder();
        this.updateBuilder = new UpdateBuilder();
        queryBuilderConsumer.accept(queryBuilder, updateBuilder);
    }

    public void execute () {
        collection.updateMany(queryBuilder.build(), updateBuilder.build());
    }

    public Update<T> execute (@NotNull Consumer<Void> consumer) {
        execute();
        consumer.accept(null);
        return this;
    }

    public Document valueAndExecute () {
        return collection.findOneAndUpdate(queryBuilder.build(), updateBuilder.build());
    }

    public Update<T> valueAndExecute (@NotNull Consumer<Document> consumer) {
        Document document = valueAndExecute();
        consumer.accept(document);
        return this;
    }

    public Document executeAndValue () {
        execute();
        return collection.findOneAndDelete(queryBuilder.build());
    }

    public Update<T> executeAndValue (@NotNull Consumer<Document> consumer) {
        Document document = executeAndValue();
        consumer.accept(document);
        return this;
    }

    public List<Document> valuesAndExecute () {
        List<Document> documents = collection.find(queryBuilder.build()).into(new java.util.ArrayList<>());
        this.collection.updateMany(queryBuilder.build(), updateBuilder.build());
        return documents;
    }

    public Update<T> valuesAndExecute (@NotNull Consumer<List<Document>> consumer) {
        List<Document> documents = valuesAndExecute();
        consumer.accept(documents);
        return this;
    }

    public List<Document> executeAndValues () {
        this.collection.updateMany(queryBuilder.build(), updateBuilder.build());
        return collection.find(queryBuilder.build()).into(new java.util.ArrayList<>());
    }

    public Update<T> executeAndValues (@NotNull Consumer<List<Document>> consumer) {
        List<Document> documents = executeAndValues();
        consumer.accept(documents);
        return this;
    }


    public Element elementAndExecute () {
        T element = manager.getInstance();
        element.fromDocument(valueAndExecute());
        return element;
    }

    public Update<T> elementAndExecute (@NotNull Consumer<Element> consumer) {
        Element element = elementAndExecute();
        consumer.accept(element);
        return this;
    }

    public Element executeAndElement () {
        T element = manager.getInstance();
        element.fromDocument(executeAndValue());
        return element;
    }

    public Update<T> executeAndElement (@NotNull Consumer<Element> consumer) {
        Element element = executeAndElement();
        consumer.accept(element);
        return this;
    }

    public List<Element> valuesAndElement () {
        List<Element> elements = new ArrayList<>();
        valuesAndExecute().forEach(document -> {
            T element = manager.getInstance();
            element.fromDocument(document);
            elements.add(element);
        });
        return elements;
    }

    public Update<T> elementsAndExecute (@NotNull Consumer<List<Element>> consumer) {
        List<Element> elements = valuesAndElement();
        consumer.accept(elements);
        return this;
    }

    public List<Element> executeAndElements () {
        List<Element> elements = new ArrayList<>();
        executeAndValues().forEach(document -> {
            T element = manager.getInstance();
            element.fromDocument(document);
            elements.add(element);
        });
        return elements;
    }

    public Update<T> executeAndElements (@NotNull Consumer<List<Element>> consumer) {
        List<Element> elements = executeAndElements();
        consumer.accept(elements);
        return this;
    }







}
