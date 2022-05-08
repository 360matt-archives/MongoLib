---
description: >-
  Les listes sont pratiques dans MongoDB lors des Query, tout s'opère sur le
  serveur
---

# QueryBuilder - Listes

### Is Empty

```java
Query<?> q = this.query(Query.isEmpty("field"));

q = this.query((query) -> {
    query.isEmpty("field");
});
```

### Is Not Empty

```java
Query<?> q = this.query(Query.isNotEmpty("field"));

q = this.query((query) -> {
    query.isNotEmpty("field");
});
```

### In

```java
Query<?> q = this.query(Query.in("field", "value", "variadic", "value3"));

Query<?> q = this.query((query) -> {
    query.in("field", "value", "variadic", "value3");
});
```

### Not In

```java
Query<?> q = this.query(Query.notIn("field", "value", "variadic", "value3"));

Query<?> q = this.query((query) -> {
    query.notIn("field", "value", "variadic", "value3");
});
```
