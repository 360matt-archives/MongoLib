---
description: Updates can also contain Math operations
---

# UpdateBuilder - Math

## Increment (Add)

```java
economy.update(((query, update) -> {
    // [...]
    update.inc("field", 100);
})).execute();
```

## Decrement (Remove)

```java
economy.update(((query, update) -> {
    // [...]
    update.decr("field", 100);
})).execute();
```

## Multiply

```java
economy.update(((query, update) -> {
    // [...]
    update.mul("field", 100);
})).execute();
```

## Divide

```java
economy.update(((query, update) -> {
    // [...]
    update.div("field", 100);
})).execute();
```

## Increment

```java
economy.update(((query, update) -> {
    // [...]
    update.inc("field", 100);
})).execute();
```

## Min

```java
economy.update(((query, update) -> {
    // [...]
    update.min("field", 100);
})).execute();
```

## Max

```java
economy.update(((query, update) -> {
    // [...]
    update.max("field", 100);
})).execute();
```

