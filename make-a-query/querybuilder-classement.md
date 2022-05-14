---
description: Les classements (Sorts) permettent de trier de façon ordonnée
---

# QueryBuilder - Sort

It is possible to make classification on fields:

* sort numbers in ascending or descending order.
* rank in descending order the texts most similar to the one specified in the query.
* rank in descending order of x-y coordinate points, on a `Geometry` index.

### &#x20;Ascending Order

```java
Query<?> q = this.query(((query) -> {
    query.sort().asc("field");
}));
```

### Descending Order

```java
Query<?> q = this.query(((query) -> {
    query.sort().desc("field");
}));
```

### Text classification

```java
Query<?> q = this.query(((query) -> {
    query.sort().text("field", "text value");
    
    query.sort().text("field", "text value", 0.50);
    // Minimal score, from 0.0 to 1.0
}
```

### Ranking of coordinate points

```java
Query<?> q = this.query(((query) -> {
    query.sort().near("field", 100.0, 300.0);
    // closer than position x=100, y=300
    
    query.sort().near("field", 100.0, 300.0, 500.0);
    // closer than position x=100, y=300
    // With maximum distance of 500
}
```
