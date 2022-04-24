package fun.listenia.mongolib.utils;

import org.bson.Document;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Criteria implements Query {

    private String key;
    private String operation;
    private Object value;

    public Criteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Criteria{" +
                "key='" + key + '\'' +
                ", operation='" + operation + '\'' +
                ", value=" + value +
                '}';
    }


    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria EQUALS (String key, Object value) {
        return new Criteria(key, "$eq", value);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria NOT_EQUALS (String key, Object value) {
        return new Criteria(key, "$ne", value);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria GREATER_THAN (String key, Object value) {
        return new Criteria(key, "$gt", value);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria LESS_THAN (String key, Object value) {
        return new Criteria(key, "$lt", value);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria GREATER_THAN_OR_EQUALS (String key, Object value) {
        return new Criteria(key, "$gte", value);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria LESS_THAN_OR_EQUALS (String key, Object value) {
        return new Criteria(key, "$lte", value);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria IN (String key, Object... value) {
        return new Criteria(key, "$in", value);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria NOT_IN (String key, Object... value) {
        return new Criteria(key, "$nin", value);
    }

    @NotNull
    public static Criteria IS_NULL (String key) {
        return new Criteria(key, "$exists", false);
    }

    @NotNull
    public static Criteria IS_NOT_NULL (String key) {
        return new Criteria(key, "$exists", true);
    }

    @NotNull
    public static Criteria IS_EMPTY (String key) {
        return new Criteria(key, "$size", 0);
    }

    @NotNull
    public static Criteria IS_NOT_EMPTY (String key) {
        return new Criteria(key, "$size", 1);
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Criteria IN_ARRAY (String key, Object... value) {
        return new Criteria(key, "$in", Arrays.asList(value));
    }

    @NotNull
    @Contract("_, _ -> new")
    public static Criteria NOT_IN_ARRAY (String key, Object... value) {
        return new Criteria(key, "$nin", Arrays.asList(value));
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Criteria IS_NULL_ARRAY (String key) {
        return new Criteria(key, "$exists", false);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Criteria IS_NOT_NULL_ARRAY (String key) {
        return new Criteria(key, "$exists", true);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Criteria IS_EMPTY_ARRAY (String key) {
        return new Criteria(key, "$size", 0);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Criteria IS_NOT_EMPTY_ARRAY (String key) {
        return new Criteria(key, "$size", 1);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria SIZE_LIST (String key, int size) {
        return new Criteria(key, "$size", size);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria GREATER_THAN_LIST (String key, int size) {
        return new Criteria(key, "$gt", size);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria GREATER_THAN_OR_EQUALS_LIST (String key, int size) {
        return new Criteria(key, "$gte", size);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria LESS_THAN_LIST (String key, int size) {
        return new Criteria(key, "$lt", size);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Criteria LESS_THAN_OR_EQUALS_LIST (String key, int size) {
        return new Criteria(key, "$lte", size);
    }

    @NotNull
    public static Document build (@NotNull Criteria... criteria) {
        final Document document = new Document();
        for (final Criteria c : criteria) {
            final Document relatedKey;
            if (document.containsKey(c.getKey())) {
                relatedKey = (Document) document.get(c.getKey());
            } else {
                relatedKey = new Document();
                document.append(c.getKey(), relatedKey);
            }
            relatedKey.append(c.getOperation(), c.getValue());
        }
        return document;
    }

}
