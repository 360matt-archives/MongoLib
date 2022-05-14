---
description: Fields can be modified quite easily, especially with MongoDD
---

# UpdateBuilder - Field

## Set

```java
economy.update(((query, update) -> {
    // [...]
    update.set("field", "value");
})).execute();
```

## UnSet

```java
economy.update(((query, update) -> {
    // [...]
    update.unset("field", "value");
})).execute();
```

## Rename

```java
economy.update(((query, update) -> {
    // [...]
    update.rename("field", "newField");
})).execute();
```
