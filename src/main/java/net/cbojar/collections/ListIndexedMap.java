package net.cbojar.collections;

import static java.util.Objects.requireNonNull;

import java.util.*;

public class ListIndexedMap<V> extends AbstractMap<Integer, V> implements Map<Integer, V> {
	private final List<V> list;

	public ListIndexedMap(final List<V> list) {
		requireNonNull(list, "List parameter cannot be null");

		this.list = list;
	}

	public static <V> ListIndexedMap<V> of(final List<V> list) {
		return new ListIndexedMap<V>(list);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean containsKey(final Object key) {
		return containsKey(toIntValue(key));
	}

	public boolean containsKey(final int key) {
		return key >= 0 && key < size();
	}

	@Override
	public boolean containsValue(final Object value) {
		return list.contains(value);
	}

	@Override
	public V get(final Object key) {
		return get(toIntValue(key));
	}

	public V get(final int key) {
		if (!containsKey(key)) {
			return null;
		}

		return list.get(key);
	}

	@Override
	public V put(final Integer key, final V value) {
		return put(toIntValue(key), value);
	}

	public V put(final int key, final V value) {
		if (key < 0) {
			throw new IllegalArgumentException("Key must be a non-negative integer");
		}

		if (containsKey(key)) {
			return list.set(key, value);
		}

		while (key > size()) {
			list.add(null);
		}

		list.add(value);

		return null;
	}

	@Override
	public void putAll(final Map<? extends Integer, ? extends V> moreEntries) {
		for (final Map.Entry<? extends Integer, ? extends V> entry : moreEntries.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public V remove(final Object key) {
		return remove(toIntValue(key));
	}

	public V remove(final Integer key) {
		return remove(toIntValue(key));
	}

	public V remove(final int key) {
		if (!containsKey(key)) {
			return null;
		}

		return list.set(key, null);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public List<V> values() {
		return list;
	}

	@Override
	public Set<Map.Entry<Integer, V>> entrySet() {
		return new ListIndexedMapEntrySet<V>(this);
	}

	private static int toIntValue(final Object key) {
		if (key == null) {
			throw new NullPointerException("Key must not be null");
		} else if (!(key instanceof Integer)) {
			throw new ClassCastException("Key must be an Integer");
		}

		return ((Integer)key).intValue();
	}

	@Override
	public int hashCode() {
		return list.hashCode();
	}

	@Override
	public boolean equals(final Object other) {
		if(this == other) {
			return true;
		}

		if(other == null) {
			return false;
		}

		if(!(other instanceof ListIndexedMap<?>)) {
			return false;
		}

		return values().equals(((ListIndexedMap<?>)other).values());
	}

	private static final class ListIndexedMapEntrySet<V> extends AbstractSet<Map.Entry<Integer, V>>
			implements Set<Map.Entry<Integer, V>> {
		private final ListIndexedMap<V> listIndexedMap;

		public ListIndexedMapEntrySet(final ListIndexedMap<V> listIndexedMap) {
			this.listIndexedMap = listIndexedMap;
		}

		@Override
		public int size() {
			return listIndexedMap.size();
		}

		@Override
		public boolean contains(final Object object) {
			if (!(object instanceof Map.Entry<?, ?>)) {
				return false;
			}

			final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)object;

			final Object key = entry.getKey();
			if (!listIndexedMap.containsKey(key)) {
				return false;
			}

			final V value = listIndexedMap.get(key);

			return Objects.equals(value, entry.getValue());
		}

		@Override
		public Iterator<Map.Entry<Integer, V>> iterator() {
			return new ListIndexedMapEntrySetIterator<V>(listIndexedMap);
		}

		@Override
		public boolean add(final Map.Entry<Integer, V> entry) {
			final boolean isAdded = !contains(entry);

			listIndexedMap.put(entry.getKey(), entry.getValue());

			return isAdded;
		}

		@Override
		public boolean remove(final Object object) {
			if (!(object instanceof Map.Entry<?, ?>)) {
				return false;
			}

			if (!contains(object)) {
				return false;
			}

			final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)object;

			if (!(entry.getKey() instanceof Integer)) {
				throw new ClassCastException("Key must be an Integer");
			}

			listIndexedMap.remove(entry.getKey());

			return true;
		}

		@Override
		public void clear() {
			listIndexedMap.clear();
		}
	}

	private static final class ListIndexedMapEntrySetIterator<V> implements Iterator<Map.Entry<Integer, V>> {
		private final ListIndexedMap<V> listIndexedMap;
		private int currentIndex = 0;

		public ListIndexedMapEntrySetIterator(final ListIndexedMap<V> listIndexedMap) {
			this.listIndexedMap = listIndexedMap;
		}

		@Override
		public boolean hasNext() {
			return currentIndex < listIndexedMap.size();
		}

		@Override
		public Map.Entry<Integer, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			return new ListIndexedMapEntry<V>(listIndexedMap, currentIndex++);
		}

		@Override
		public void remove() {
			listIndexedMap.remove(currentIndex);
		}
	}

	private static class ListIndexedMapEntry<V> implements Map.Entry<Integer, V> {
		private final ListIndexedMap<V> listIndexedMap;
		private final int index;

		public ListIndexedMapEntry(final ListIndexedMap<V> listIndexedMap, final int index) {
			this.listIndexedMap = listIndexedMap;
			this.index = index;
		}

		@Override
		public Integer getKey() {
			return Integer.valueOf(index);
		}

		@Override
		public V getValue() {
			return listIndexedMap.get(index);
		}

		@Override
		public V setValue(final V value) {
			return listIndexedMap.put(index, value);
		}
	}
}
