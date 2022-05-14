---
description: Projections are the limitation of fields returned from the query
---

# QueryBuilder - Projection

When we make a request, it happens that we only need certain fields and not all of them. Projections allow you to limit the fields returned.\
By default the field `_id` will always be returned, unless it is set to `0`

### Include

```java
Query<?> q = this.query(((query) -> {
    query.projection().include("field");
}));
```

### Exclude

```java
Query<?> q = this.query(((query) -> {
    query.projection().exclude("field");
}));
```
