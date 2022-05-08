---
description: Le curseur possède quelques options qui s'avèrent utilises voir indispensable
---

# QueryBuilder - Curseur

### Skip

Permet d'ignorer les X premiers documents renvoyés

```java
Query<?> q = this.query(((query) -> {
    query.skip(5);
}));
```

### Limit

Permet d'ignorer les prochains documents, une fois les X documents renvoyés

```java
Query<?> q = this.query(((query) -> {
    query.limit(25);
}));
```
