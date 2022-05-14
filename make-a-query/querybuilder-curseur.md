---
description: The cursor has a few options that are useful or even essential
---

# QueryBuilder - Curseur

### Skip

Allows to ignore the first X documents returned

```java
Query<?> q = this.query(((query) -> {
    query.skip(5);
}));
```

### Limit

Skip next documents, once X documents are returned

```java
Query<?> q = this.query(((query) -> {
    query.limit(25);
}));
```
