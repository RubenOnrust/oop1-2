package util.collection;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class OneToOneMapTest {
	
	@Before
	public OneToOneMap<Integer, Integer> initializeMap() {
        OneToOneMap<Integer, Integer> testMap = new OneToOneMap<Integer, Integer>();
        testMap.put(1, 1);
        testMap.put(2, 2);
        testMap.put(3, 3);
        return testMap;
	}

	@Test
	void testSize() {
		OneToOneMap<?, ?> test = initializeMap();
		test.size();
		

		
	}

	@Test
	void testIsEmpty() {
		OneToOneMap<?, ?> test = initializeMap();
		test.isEmpty();
	}

	@Test
	void testContainsKey() {
		OneToOneMap<?, ?> test = initializeMap();

	}

	@Test
	void testContainsValue() {
		fail("Not yet implemented");
	}

	@Test
	void testGet() {
		fail("Not yet implemented");
	}

	@Test
	void testPut() {
		fail("Not yet implemented");
	}

	@Test
	void testRemove() {
		fail("Not yet implemented");
	}

	@Test
	void testPutAll() {
		fail("Not yet implemented");
	}

	@Test
	void testClear() {
		fail("Not yet implemented");
	}

	@Test
	void testKeySet() {
		fail("Not yet implemented");
	}

	@Test
	void testValues() {
		fail("Not yet implemented");
	}

	@Test
	void testEntrySet() {
		fail("Not yet implemented");
	}

}
