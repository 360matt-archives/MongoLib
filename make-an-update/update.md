---
description: Updates are very easy to do with Mongo, and even more so with the API
---

# Update

Generally, Update operations are done in a method that you have previously created to simplify the execution and the readability of your code.

The different classes and their uses:

* The Update class concerns the request and all of its actions
* The QueryBuilder class is for filters and criteria, as well as other parameters
* The UpdateBuilder class concerns actions to be performed on documents

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

## Update

```java
public class PlayerManager extends Manager<Player> {

    public void addScore (String name, int ammount) {
        economy.update(((query, update) -> {
            query.equals("name", name);
            update.inc("score", ammount);
        })).execute();
    }
    
    public void removeScore (String name, int ammount) {
        economy.update(((query, update) -> {
            query.equals("name", name);
            update.decr("score", ammount);
        })).execute();
    }

}
```

## Actions et retours

### Execute

```java
this.update([...]).execute();
this.query([...]).delete(() => {
    // do stuff, like log
});
```

### Execute and return a value

```java
Document doc = this.update([...]).executeAndValue();
Update<?> update = this.query([...]).executeAndValue((doc) => {
    // doc : Document
});

Player player = this.update([...]).executeAndElement();
Update<Player> update = this.query([...]).executeAndElement((player) => {
    // player: Player
});
```

### Execute and Return Multiple Values

```java
List<Document> docs = this.update([...]).executeAndValues();
Update<?> update = this.query([...]).executeAndValues((docs) => {
    // doc : List<Document>
});

List<Player> player = this.update([...]).executeAndElements();
Update<Player> update = this.query([...]).executeAndElements((players) => {
    // player: List<Player>
});
```

### Return a value and Execute

```java
Document doc = this.update([...]).valueAndExecute();
Update<?> update = this.query([...]).valueAndExecute((doc) => {
    // doc : Document
});

Player player = this.update([...]).elementAndExecute();
Update<Player> update = this.query([...]).elementAndExecute((player) => {
    // player: Player
});
```

### Execute and Return Multiple Values

```java
List<Document> docs = this.update([...]).valuesAndExecute();
Update<?> update = this.query([...]).valuesAndExecute((docs) => {
    // doc : List<Document>
});

List<Player> player = this.update([...]).elementsAndExecute();
Update<Player> update = this.query([...]).elementsAndExecute((players) => {
    // player: List<Player>
});
```
