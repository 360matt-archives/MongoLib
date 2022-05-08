---
description: >-
  Les query permettent sélectionner des documents via des critères pour les
  retourner.
---

# Query

Généralement, les opérations de Query se font dans une méthode que vous avez préalablement créé pour simplifier l'exécution et la lisibilité de votre code.

La class Query concerne la requête et ses actions, tandis que la class QueryBuilder concerne les filtres et critères, ainsi que d'autres paramètres.

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

## Query simplifiée

Les Query simplifiées se font avec les méthodes statics de la class Query\
Voici donc un petit exemple:

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

## Query multiple

Les query multiples se font avec les méthodes d'une instance QueryBuilder, dans un Consumer.\
Voici donc un petit exemple:

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

## Actions et retours

Une fois les critères et paramètres spécifiés, il reste à appeler la méthode qui déclenchera l'action voulu. Ci-dessous une liste des possibilités:

### Retourner les Document

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

### Retourner des Element

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

### Retourner le nombre

```java
boolean exist = this.query([...]).count();
long count = this.query([...]).count();
Query<?> query = this.query([...]).count((count) => {
    // count: Long
});
```

### Supprimer des documents

```java
this.query([...]).delete();
this.query([...]).delete(() => {
    // do stuff, like log
});
```



