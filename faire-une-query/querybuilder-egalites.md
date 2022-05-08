---
description: >-
  Les égalités sont les critères les plus indispensables pour rechercher dans
  une base de donnée
---

# QueryBuilder - Égalités

### Exists (and not null)

```java
Query<?> q = this.query(Query.exists("field"));

Query<?> q = this.query((query) -> {
    query.exists("field");
});
```

### Not exists (or null)

```java
Query<?> q = this.query(Query.notExists("field"));

Query<?> q = this.query((query) -> {
    query.notExists("field");
});
```

### Equals

```java
Query<?> q = this.query(Query.equals("field", "value"));

Query<?> q = this.query((query) -> {
    query.equals("field", "value");
});
```

### Not Equals

```java
Query<?> q = this.query(Query.notEquals("field", "value"));

Query<?> q = this.query((query) -> {
    query.notEquals("field", "value");
});
```

### Less Than or Equals

```java
Query<?> q = economy.query(Query.lessThanOrEquals("field", 100));

Query<?> q = economy.query((query) -> {
    query.lessThanOrEquals("field", 100);
});
ja
```

### Grater Than

```java
Query<?> q = economy.query(Query.greaterThan("field", 100));

Query<?> q = economy.query((query) -> {
    query.greaterThan("field", 100);
});
```

### Greater Than or Equals

```java
Query<?> q = economy.query(Query.greaterThanOrEquals("field", 100));

Query<?> q = economy.query((query) -> {
    query.greaterThanOrEquals("field", 100);
});
ja
```

