---
description: Se connecter au serveur de base de données MongoDB
---

# Initialiser une connexion

La class permettant d'ouvrir une connexion à un serveur MongoDB est `MongoLib`, il faut logiquement l'instancier pour créer un nouveau client.

Cependant, dans la majorité des cas, une connexion à un seul serveur suffit, le champs ci-dessous est une instance de `MongoLib` déjà crée:

```java
MongoLib.DEFAULT
```

## Usage des méthodes&#x20;

### Se connecter au serveur via MongoAuth

```java
MongoAuth mongoAuth = new MongoAuth();
mongoAuth.setUser("françois");
mongoAuth.setPassword("French_Nationalist");
mongoAuth.setHost("127.0.0.1");
mongoAuth.setPort(27017);

MongoLib.DEFAULT.connect(mongoAuth);
```

### Se connecter au serveur via URL

```java
// mongodb://[username:password@]host1[:port1][,...hostN[:portN]][/[defaultauthdb][?options]]
MongoLib.DEFAULT.connect("mongodb://........");
```

### Se déconnecter

```java
// implements Closeable
MongoLib.DEFAULT.close();
```

### Récupérer l'instance MongoClient

```java
MongoClient mongoClient = MongoLib.DEFAULT.getClient();
```

### Récupérer l'instance MongoDatabase

```java
MongoDatabase mongoDatabase = MongoLib.DEFAULT.getDatabase("userProfiles");
```
