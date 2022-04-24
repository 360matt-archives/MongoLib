package fun.listenia.mongolib.utils;

import org.bson.Document;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Update {

    public enum Position {
        FIRST, LAST
    }

    private final String field;
    private final Object value;
    private final String operator;

    public Update (String field, Object value, String operator) {
        this.field = field;
        this.value = value;
        this.operator = operator;
    }

    public String getField () {
        return field;
    }

    public Object getValue () {
        return value;
    }

    public String getOperator () {
        return operator;
    }


    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Update SET (String field, Object value) {
        return new Update(field, value, "set");
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Update UNSET (String field) {
        return new Update(field, null, "unset");
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Update INCREMENT (String field, Number value) {
        return new Update(field, value, "inc");
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Update DECREMENT (String field, Number value) {
        return new Update(field, value, "dec");
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Update MULTIPLY (String field, Number value) {
        return new Update(field, value, "mul");
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Update DIVIDE (String field, Number value) {
        return new Update(field, value, "div");
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Update MODULO (String field, Number value) {
        return new Update(field, value, "mod");
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Update PUSH_LIST (String field, Object value) {
        return new Update(field, value, "push");
    }

    @NotNull
    @Contract("_, _, _ -> new")
    public static Update PUSH_LIST (String field, Position position, Object value) {
        Document document = new Document("$each", new Object[] { value });
        document.append("$position", position == Position.FIRST ? 0 : -1);
        return new Update(field, document,"push");
    }

    @NotNull
    public static Update PUSH_LIST (String field, Object... values) {
        Document document = new Document("$each", values);
        return new Update(field, document, "push");
    }

    @NotNull
    @Contract("_, _, _ -> new")
    public static Update PUSH_LIST (String field, Position position, Object... value) {
        Document document = new Document("$each", value);
        document.append("$position", position == Position.FIRST ? 0 : -1);
        return new Update(field, document, "push");
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Update PULL_LIST (String field, Object value) {
        return new Update(field, value, "pull");
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Update PULL_LIST (String field, Object... values) {
        return new Update(field, values, "pullAll");
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Update POP_LIST (String field, Position type) {
        return new Update(field, type, "pop");
    }



    @NotNull
    public static Document build (@NotNull Update... updates) {
        Document document = new Document();
        for (Update update : updates) {
            document.append("$" + update.getOperator(), new Document(update.getField(), update.getValue()));
        }
        return document;
    }





}
