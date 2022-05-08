---
description: Les projections sont la limitation des fields retournées de la requête
---

# QueryBuilder - Projection

Lorsqu'on fait une requête, il arrive qu'on ai besoin que de certains fields et pas tous.\
Les projections permettent justement de limiter les fields renvoyés.\
Par défaut le field `_id` sera toujours renvoyé, sauf s'il est mis à valeur `0`

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
