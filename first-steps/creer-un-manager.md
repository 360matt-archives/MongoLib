---
description: Managers represent the entire collection, they allow you to manipulate them.
---

# Create a Manager

The main operations will be carried out thanks to the collection managers, we will learn on this page how to create a manager.

We will reuse the structure class from the previous page for the tutorial on this page.

You are of course free to create as many methods inside the class manager, to call the methods of the library, it is even necessary.

## On the default server

When the database and the collection are on the default server, and this one is defined, we don't need to specify a MongoLib instance:

```java
public class Example extends Manager<T extends Element> {
    public Economy () {
        super("dbName", "collection", StructureExample.class);
    }
}
```

## On a chosen server

In this case, you will have to indicate in first argument a MongoLib instance.

```java
public class Main {
    public static MongoLib serverAlpha = new MongoLib();
    public void main (String[] args) {
        serverAlpha.connect("some url");
    }
}

public class Example extends Manager<T extends Element> {
    public Economy () {
        super(Main.serverAlpha, "dbName", "collection", StructureExample.class);
    }
}
```
