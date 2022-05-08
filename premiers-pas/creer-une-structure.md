---
description: >-
  Les structures représentent le schéma d'une collection, contenant ses fields
  et ses types
---

# Créer une structure

{% hint style="info" %}
Information: il s'agit d'une simple class à créer, qui doit étendre la class Element
{% endhint %}

{% hint style="info" %}
Information: la class peut contenir des fields de type dérivés de Element
{% endhint %}

{% hint style="danger" %}
Attention: La class ne doit pas comporter de constructeur défini, ou alors le constructeur ne doit pas avoir d'argument, puisque l'instanciation se fait par reflection.
{% endhint %}

## Exemples

```java
public static class VillageScore extends Element {
    public int security;
    public int livingComfort;
    public int decoration;
}

public static class Village extends Element {
    @Required
    @Index(unique = true, type = Index.Type.SINGLE)
    public String name;
    public int members;
    public VillageScore score = new VillageScore(); // must be instancied !!
}
```

## Caractéristiques:

### La class:

* Elle doit être indépendante. Si elle ne l'est pas, elle doit être static.
* Elle doit obligatoirement étendre Element
* Elle ne doit pas avoir de constructeur, sauf le constructeur par défaut sans argument.

### Les fields:

* Ils peuvent contenir des valeurs par défauts si nécessaire.
* Ils peuvent être de type class de structure, pour les sous-document, il sera nécessaire d'instancier manuellement une nouvelle instance pour définir la valeur de ce field.
* Le field \_id existe déjà, en portée restreinte.

### Les annotations des fields:

* L'annotation @Required permet d'exiger une valeur définie, dans le cas contraire une erreur sera provoquée.
* L'annotation @Index permet d'exiger une indexation côté base de donnée, elle créera l'index s'il n'existe pas. \
  Le paramètre `unique` permet de forcer la valeur unique pour le field au niveau base de donnée, mais aussi au niveau librairie en provoquant une erreur. \
  Le paramètre `type` permet de définir le type d'index.

### Les méthodes

* Vous pouvez bien sûr créer les méthodes que vous souhaitez, quelque soit l'action qu'elles font, elles peuvent lire ou écrire des fields sans problème.
