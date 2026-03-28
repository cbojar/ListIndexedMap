package net.cbojar.collections;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import org.hamcrest.Matcher;

import java.util.*;

public class ListIndexedMapTest {
	@Test
	public void shouldBeCreated() {
		final ListIndexedMap<Object> listIndexedMap = ListIndexedMap.of(listOf());

		assertNotNull(listIndexedMap);
	}

	@Test(expected=NullPointerException.class)
	public void shouldNotTakeANullList() {
		ListIndexedMap.of(null);
	}

	@Test
	public void shouldStartAtTheSameSizeAsTheList() {
		final List<String> list = listOf("a", "b");
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(list);

		assertThatInt(listIndexedMap.size(), isInt(list.size()));
	}

	@Test
	public void shouldBeEmptyIfTheListIsEmpty() {
		final ListIndexedMap<Object> listIndexedMap = ListIndexedMap.of(Collections.emptyList());

		assertTrue(listIndexedMap.isEmpty());
	}

	@Test
	public void shouldNotBeEmptyIfTheListIsNotEmpty() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertFalse(listIndexedMap.isEmpty());
	}

	@Test
	public void shouldClearAllValues() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertThatInt(listIndexedMap.size(), isInt(2));

		listIndexedMap.clear();

		assertThatInt(listIndexedMap.size(), isInt(0));
	}

	@Test
	public void shouldContainAValueInTheList() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertTrue(listIndexedMap.containsValue("a"));
	}

	@Test
	public void shouldNotContainAValueNotInTheList() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertFalse(listIndexedMap.containsValue("c"));
	}

	@Test
	public void shouldContainAnIndexLessThanTheListSize() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertTrue(listIndexedMap.containsKey(1));
	}

	@Test
	public void shouldNotContainAnIndexGreaterThanTheListSize() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertFalse(listIndexedMap.containsKey(3));
	}

	@Test
	public void shouldNotContainAnIndexEqualToTheListSize() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertFalse(listIndexedMap.containsKey(2));
	}

	@Test
	public void shouldNotContainANegativeIndex() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertFalse(listIndexedMap.containsKey(-1));
	}

	@Test
	public void shouldAlsoCheckContainmentWithBoxedIntegers() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertTrue(listIndexedMap.containsKey(Integer.valueOf(1)));
	}

	@Test(expected=NullPointerException.class)
	public void shouldNotAcceptANullKeyToCheckForContainment() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		listIndexedMap.containsKey(null);
	}

	@Test(expected=ClassCastException.class)
	@SuppressWarnings("unlikely-arg-type")
	public void shouldNotAcceptANonIntegerKeyToCheckForContainment() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		listIndexedMap.containsKey("1");
	}

	@Test
	public void shouldGetTheValueForTheGivenIndex() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertThat(listIndexedMap.get(0), is("a"));
	}

	@Test
	public void shouldNotGetTheValueForAnIndexGreaterThanTheListSize() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertNull(listIndexedMap.get(3));
	}

	@Test
	public void shouldNotGetTheValueForAnIndexEqualToTheListSize() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertNull(listIndexedMap.get(2));
	}

	@Test
	public void shouldNotGetTheValueForANegativeIndex() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertNull(listIndexedMap.get(-1));
	}

	@Test
	public void shouldAlsoGetTheValueWithBoxedIntegers() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertThat(listIndexedMap.get(Integer.valueOf(1)), is("b"));
	}

	@Test(expected=NullPointerException.class)
	public void shouldNotAcceptANullKeyToGetAValue() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		listIndexedMap.get(null);
	}

	@Test(expected=ClassCastException.class)
	@SuppressWarnings("unlikely-arg-type")
	public void shouldNotAcceptANonIntegerKeyToGetAValue() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		listIndexedMap.get("1");
	}

	@Test
	public void shouldReturnTheListOfValues() {
		final List<String> list = listOf("a", "b");
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(list);

		assertThat(listIndexedMap.values(), is(list));
	}

	@Test
	public void shouldReturnTheSetOfKeys() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		final Set<Integer> expected = new HashSet<>(listOf(Integer.valueOf(0), Integer.valueOf(1)));
		assertThat(listIndexedMap.keySet(), is(expected));
	}

	@Test
	public void shouldIterateOverTheSetOfKeys() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		assertThat(listIndexedMap.keySet(), hasItems(Integer.valueOf(0), Integer.valueOf(1)));
	}

	@Test
	public void shouldUpdateAValue() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		final String oldValue = listIndexedMap.put(Integer.valueOf(0), "c");

		assertThat(listIndexedMap.get(0), is("c"));
		assertThat(oldValue, is("a"));
	}

	@Test
	public void shouldAddAValueAtTheEnd() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		final String oldValue = listIndexedMap.put(Integer.valueOf(2), "c");

		assertThatInt(listIndexedMap.size(), isInt(3));
		assertThat(listIndexedMap.get(2), is("c"));
		assertThat(oldValue, is(nullValue()));
	}

	@Test
	public void shouldExpandTheListToAddAValuePastTheEnd() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		final String oldValue = listIndexedMap.put(Integer.valueOf(4), "c");

		assertThatInt(listIndexedMap.size(), isInt(5));
		assertTrue(listIndexedMap.containsKey(3));
		assertThat(listIndexedMap.get(3), is(nullValue()));
		assertThat(listIndexedMap.get(4), is("c"));
		assertThat(oldValue, is(nullValue()));
	}

	@Test(expected=IllegalArgumentException.class)
	public void shouldNotPutAValueAtANegativeIndex() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		listIndexedMap.put(Integer.valueOf(-1), "c");
	}

	@Test
	public void shouldPutAllNewValuesAndUpdateExistingValues() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		final Map<Integer, String> otherMap = new HashMap<>();
		otherMap.put(Integer.valueOf(1), "c");
		otherMap.put(Integer.valueOf(2), "d");
		otherMap.put(Integer.valueOf(4), "e");

		listIndexedMap.putAll(otherMap);

		assertThatInt(listIndexedMap.size(), isInt(5));
		assertThat(listIndexedMap.get(0), is("a"));
		assertThat(listIndexedMap.get(1), is("c"));
		assertThat(listIndexedMap.get(2), is("d"));
		assertThat(listIndexedMap.get(4), is("e"));
	}

	@Test
	public void shouldRemoveAnEntry() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		final String oldValue = listIndexedMap.remove((Object)Integer.valueOf(0));

		assertThatInt(listIndexedMap.size(), isInt(2));
		assertThat(listIndexedMap.get(0), is(nullValue()));
		assertThat(listIndexedMap.get(1), is("b"));
		assertThat(oldValue, is("a"));
	}

	@Test
	public void shouldNotRemoveAnEntryNotInTheMap() {
		final ListIndexedMap<String> listIndexedMap = ListIndexedMap.of(listOf("a", "b"));

		final String oldValue = listIndexedMap.remove((Object)Integer.valueOf(3));

		assertThatInt(listIndexedMap.size(), isInt(2));
		assertThat(listIndexedMap.get(0), is("a"));
		assertThat(listIndexedMap.get(1), is("b"));
		assertThat(oldValue, is(nullValue()));
	}

	@SafeVarargs
	private static <T> List<T> listOf(final T... values) {
		return new ArrayList<T>(Arrays.asList(values));
	}

	private static void assertThatInt(final int actual, final Matcher<Integer> expected) {
		assertThat(Integer.valueOf(actual), expected);
	}

	private static Matcher<Integer> isInt(final int value) {
		return is(Integer.valueOf(value));
	}
}
