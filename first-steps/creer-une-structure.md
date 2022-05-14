---
description: >-
  Structures represent the schema of a collection, containing its fields and
  types
---

# Create Structure

{% hint style="info" %}
Information: this is a simple class to create, which must extend the class Element
{% endhint %}

{% hint style="info" %}
Information: the class can contain fields of type derived from Element
{% endhint %}

{% hint style="danger" %}
Warning: The class must not have a defined constructor, or the constructor must not have an argument, since the instantiation is done by reflection.
{% endhint %}

## Examples

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

## Features:

### The class:

* It must be independent. If not, it must be static.
* It must necessarily extend Element.
* It must not have a constructor, except the default constructor with no arguments.

### The fields:

* They can contain default values ​​if necessary.
* They can be of structure class type, for sub-documents, it will be necessary to manually instantiate a new instance to define the value of this field.&#x20;
* The field `_id` already exists, in restricted scope.

### Field annotations:

* The @Required annotation allows to require a defined value, otherwise an error will be caused.
* The @Index annotation makes it possible to require indexing on the database side, it will create the index if it does not exist.
* The `unique` parameter makes it possible to force the unique value for the field at the database level, but also at the library level by causing an error.
* The type parameter is used to define the `type` of index.

### Les méthodes

* Vous pouvez bien sûr créer les méthodes que vous souhaitez, quelque soit l'action qu'elles font, elles peuvent lire ou écrire des fields sans problème.
