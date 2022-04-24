package fun.listenia.mongolib.utils;

import org.bson.Document;

public class Sort implements Query {

    public enum SortType {
        ASC, DESC
    }

    private final String field;
    private final SortType sortType;

    public Sort(String field, SortType sortType) {
        this.field = field;
        this.sortType = sortType;
    }

    public String getField() {
        return field;
    }

    public SortType getSortType() {
        return sortType;
    }

    public static Sort ASC (String field) {
        return new Sort(field, SortType.ASC);
    }
    public static Sort DESC (String field) {
        return new Sort(field, SortType.DESC);
    }

    public static Document build (Sort... sorts) {
        Document document = new Document();
        for (Sort sort : sorts) {
            document.append(sort.getField(), sort.getSortType() == SortType.ASC ? 1 : -1);
        }
        return document;
    }
}
