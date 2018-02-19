# ListIndexedMap

A simple adapter class for making a `Map` view over an underlying `List`. The
`ListIndexedMap` wraps the `List` and presents it as a `Map` with `Integer` keys
and values of the type of the list. The `Integer` keys correspond to the indexes
of the values in the list.

```java
List<String> list = new ArrayList<>();
list.add("a");
list.add("b");
list.add("c");

ListIndexedMap listIndexedMap = ListIndexedMap.of(list);
listIndexedMap.size(); // 3
listIndexedMap.get(0); // "a"
listIndexedMap.get(1); // "b"
listIndexedMap.get(2); // "c"
```

The `ListIndexedMap` offers the ability to present a `List` as a `Map` without
needing to allocate an entirely new block of storage and copy all of the value
references. Changes to the `ListIndexedMap` write through to the underlying list
and the `ListIndexedMap` is constrained by the invariants of the list. Passing
an unmodifiable `List` will result in an unmodifiable `ListIndexedMap`.
(Unfortunately, this does not apply to synchronization.)

## Implementation Details and Quirks

The current implementation makes some assumptions about how to handle edge
cases. For example, adding far past the end of the list will expand the list
with `null` values in between:

```java
List<String> list = new ArrayList<>();
list.add("a");
list.add("b");
list.add("c");

ListIndexedMap listIndexedMap = ListIndexedMap.of(list);
listIndexedMap.put(9, "d");
listIndexedMap.size(); // 10
listIndexedMap.get(6); // null
listIndexedMap.containsKey(6); // true
```

When removing, the `ListIndexedMap` will nullify the value at a given position
instead of removing it from the `List` and shifting all values.

```java
List<String> list = new ArrayList<>();
list.add("a");
list.add("b");
list.add("c");

ListIndexedMap listIndexedMap = ListIndexedMap.of(list);
listIndexedMap.remove(1);
listIndexedMap.size(); // 3
listIndexedMap.get(1); // null
listIndexedMap.containsKey(1); // true
```

The `ListIndexedMap` assumes that a key exists so long as it is positive and
less than the size of the `List`. Keys that are removed and indexes with `null`
values are still returned as valid keys, and will be iterated over by the key,
value, and entry `Iterator`s