---
description: Les classements (Sorts) permettent de trier de façon ordonnée
---

# QueryBuilder - Classement

Il est possible de faire des classement sur des fields:

* classer des nombres d'ordre croissant ou décroissant
* classer par ordre décroissant les textes les plus similaires à celui spécifié dans la requête.
* classer par ordre décroissant des points de coordonnée x-y, sur un index `Geometry`.

### &#x20;Ordre croissant

```java
Query<?> q = this.query(((query) -> {
    query.sort().asc("field");
}));
```

### Ordre décroissant

```java
Query<?> q = this.query(((query) -> {
    query.sort().desc("field");
}));
```

### Classement de texte

```java
Query<?> q = this.query(((query) -> {
    query.sort().text("field", "text value");
    
    query.sort().text("field", "text value", 0.50);
    // Minimal score, from 0.0 to 1.0
}
```

### Classement de points de coordonnée

```java
Query<?> q = this.query(((query) -> {
    query.sort().near("field", 100.0, 300.0);
    // closer than position x=100, y=300
    
    query.sort().near("field", 100.0, 300.0, 500.0);
    // closer than position x=100, y=300
    // With maximum distance of 500
}
```
