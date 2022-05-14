---
description: Connect to MongoDB database server
---

# Initialize a connection

The class used to open a connection to a MongoDB server is `MongoLib`, it must logically be instantiated to create a new client.

However, in most cases, a connection to a single server is sufficient, the field below is an instance of `MongoLib` already created:

```java
MongoLib.DEFAULT
```

## Use of methods&#x20;

### Connect to the server via MongoAuth

```java
MongoAuth mongoAuth = new MongoAuth();
mongoAuth.setUser("françois");
mongoAuth.setPassword("French_Nationalist");
mongoAuth.setHost("127.0.0.1");
mongoAuth.setPort(27017);

MongoLib.DEFAULT.connect(mongoAuth);
```

### Connect to server via URL

```java
// mongodb://[username:password@]host1[:port1][,...hostN[:portN]][/[defaultauthdb][?options]]
MongoLib.DEFAULT.connect("mongodb://........");
```

### Sign Out

```java
// implements Closeable
MongoLib.DEFAULT.close();
```

### Retrieve the MongoClient instance

```java
MongoClient mongoClient = MongoLib.DEFAULT.getClient();
```

### Get the MongoDatabase instance

```java
MongoDatabase mongoDatabase = MongoLib.DEFAULT.getDatabase("userProfiles");
```
