---
description: >-
  Les updates sont très facile à être réalisées avec Mongo, et encore plus avec
  l'API
---

# Update

Généralement, les opérations de Update se font dans une méthode que vous avez préalablement créé pour simplifier l'exécution et la lisibilité de votre code.

Les différents class et leur utilités:

* La class Update concerne la requête et l'ensemble de ses actions
* La class QueryBuilder concerne les filtres et critères, ainsi que d'autres paramètres.
* La class UpdateBuilder concerne les actions à effectuer sur les documents

Ci-dessous sont montrée les class Manager et Element qui seront utilisées pour cet exemple.

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

### Executer

```java
this.update([...]).execute();
this.query([...]).delete(() => {
    // do stuff, like log
});
```

### Executer et Retourner une valeur

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

### Executer et Retourner plusieurs valeurs

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

### Retourner une valeur et Executer

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

### Executer et Retourner plusieurs valeurs

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
