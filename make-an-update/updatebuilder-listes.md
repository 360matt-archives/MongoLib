---
description: >-
  Lists are very handy in MongoDB for inserting and retrieving at the server
  level
---

# UpdateBuilder - Lists

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

