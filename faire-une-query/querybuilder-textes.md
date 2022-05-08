---
description: Les Query sur les textes sont assez utiles avec MongoDB
---

# QueryBuilder - Textes

### Starts With

```java
Query<?> q = this.query(Query.startsWith("field", "prefix"));

q = this.query((query) -> {
    query.startsWith("field", "prefix");
});
```

### Ends With

```java
Query<?> q = this.query(Query.endsWith("field", "suffix"));

q = this.query((query) -> {
    query.endsWith("field", "suffix");
});
```

### Contains

```java
Query<?> q = this.query(Query.containsText("field", "word"));

q = this.query((query) -> {
    query.containsText("field", "word");
});
```

### Classement de texte

{% content-ref url="querybuilder-classement.md" %}
[querybuilder-classement.md](querybuilder-classement.md)
{% endcontent-ref %}
