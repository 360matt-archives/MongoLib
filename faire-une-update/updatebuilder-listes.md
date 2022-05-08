---
description: >-
  Les listes sont très pratiques dans MongoDB pour insérer et récupérer au
  niveau du serveur
---

# UpdateBuilder - Listes

## Push

```java
economy.update(((query, update) -> {
    // [...]
    update.push("field", "value", "value2", "value3"......);
})).execute();
```

## addToSet

```java
economy.update(((query, update) -> {
    // [...]
    update.addToSet("field", "value", "value2", "value3"......);
})).execute(
```

## Pull

```java
economy.update(((query, update) -> {
    // [...]
    update.pull("field", "value", "value2", "value3"......);
})).execute(
```

## Pop

```java
economy.update(((query, update) -> {
    // [...]
    update.pop("field", 1);
    update.pop("field", -1);
})).execute(
```

