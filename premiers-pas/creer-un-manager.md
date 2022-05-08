---
description: >-
  Les manager représentent l'entièreté de la collection, ils permettent de les
  manipuler.
---

# Créer un manager

Les principales opérations se feront grâce aux managers de collection, \
nous apprendrons sur cette page à créer un manager.\
\
Nous réutiliserons la class de structure de la page précédente pour le tutoriel de cette page.\
\
Vous êtes bien sûr libre de créer autant de méthode à l'intérieur de la class manager, pour appeler les méthodes de la librairie, c'est même nécessaire.

## Sur le serveur par défaut

Lorsque la base de donnée et la collection se trouvent sur le serveur par défaut, et que celui-ci est défini, nous n'avons pas besoin se spécifier d'instance MongoLib:

```java
public class Example extends Manager<T extends Element> {
    public Economy () {
        super("dbName", "collection", StructureExample.class);
    }
}
```

## Sur un serveur choisi

Dans ce cas présent, vous devrez indiquer en premier argument une instance MongoLib.

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
