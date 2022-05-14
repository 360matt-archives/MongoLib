---
description: Queries allow you to select documents using criteria to return them.
---

# Query

Generally, Query operations are done in a method that you have previously created to simplify the execution and the readability of your code.

The Query class is about the query and its actions, while the QueryBuilder class is about filters and criteria, and other parameters.

Shown below are the Manager and Element classes that will be used for this example.

```java
public class Player extends Element {
    @Required
    @Index (unique = true)
    public String name;
    public int score;
    public int power;
}

public class PlayerManager extends Manager<Player> {
    public PlayerManager () {
        super("game", "players", Player.class);
    }
}
```

## Simplified query

Simplified queries are made with the static methods of the Query class. \
Here is a small example:

```java
public class PlayerManager extends Manager<Player> {
    /// ...
    
    public boolean existPlayer (String name) {
        // or: return this.exists(Query.equals("name", name));
        return this.query(Query.equals("name", name)).exists();
    }
    
    public Player getPlayer (String name) {
        return this.query(Query.equals("name", name)).element();
    }
    
    public void deletePlayer (String name) {
        return this.query(Query.equals("name", name)).delete();
    }
    
}
```

## Multiple query

Multiple queries are done with the methods of a QueryBuilder instance, in a Consumer. Here is a small example:

```java
public class PlayerManager extends Manager<Player> {
    /// ...
    
     public List<Player> topScore (int limit) {
        return this.query((query -> {
            query.sort().desc("score");
            query.limit(limit);
        })).elements();
    }
    
}
```

## Actions and returns

Once the criteria and parameters have been specified, all that remains is to call the method that will trigger the desired action. Below is a list of possibilities:

### Return Documents

```java
Document document = this.query([...]).value();
List<Document> documents = this.query([...]).values();

Query<?> query = this.query([...]).value((doc) => {
    // doc: Document
});

Query<?> query = this.query([...]).values((docs) => {
    // docs: List<Document>
});
```

### Return Elements

```java
Player document = this.query([...]).element();
List<Player> documents = this.query([...]).elements();

Query<Player> query = this.query([...]).element((player) => {
    // player: Player
});

Query<Player> query = this.query([...]).elements((players) => {
    // players: List<Player>
});
```

### Return numbers

```java
boolean exist = this.query([...]).count();
long count = this.query([...]).count();
Query<?> query = this.query([...]).count((count) => {
    // count: Long
});
```

### Delete documents

```java
this.query([...]).delete();
this.query([...]).delete(() => {
    // do stuff, like log
});
```



