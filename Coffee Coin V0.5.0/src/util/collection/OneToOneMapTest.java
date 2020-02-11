package util.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class OneToOneMapTest {
	
	@Test
	void singleton() {
		OneToOneMap<Integer, String> map1 = (OneToOneMap<Integer, String>) OneToOneMap.getInstance(null, null);
		OneToOneMap<String, Integer> map2 = (OneToOneMap<String, Integer>) OneToOneMap.getInstance(null, null);
		map1.put(1, "a");
		map2.put("b", 2);
		assertEquals(map1, map2);
		
	}

	@Test
	void testPut() {
		OneToOneMap<Integer, String> testMap = (OneToOneMap<Integer, String>) OneToOneMap.getInstance(null, null);
		testMap.put(1, "a");
		testMap.put(2, "b");
		testMap.put(3, "c");
		testMap.put(3, "b");
		testMap.put(4, "b");
		assertNotEquals(testMap.get(1), null);
		assertEquals(testMap.get(1), "a");
		assertNotEquals(testMap.get(2), "b");
		assertEquals(testMap.get(2), null);
		assertNotEquals(testMap.get(3), "c");
		assertEquals(testMap.get(3), null);
		assertNotEquals(testMap.get(4), null);
		assertEquals(testMap.get(4), "b");
	}

	@Test
	void testPutAll() {
        OneToOneMap<Integer, String> testMap = (OneToOneMap<Integer, String>) OneToOneMap.getInstance(null, null);
        testMap.put(1, "a");
        testMap.put(2, "b");
        testMap.put(3, "c");
        HashMap<Integer, String> testing = new HashMap<Integer, String>();
        testing.put(4, "b");
        testing.put(5, "a");
        testing.put(6, "b"); 
        testMap.putAll(testing);
        assertEquals(testMap.get(1), null);
        assertNotEquals(testMap.get(1), "a");
        assertEquals(testMap.get(2), null);
        assertNotEquals(testMap.get(2), "b");
        assertEquals(testMap.get(3), "c");
        assertNotEquals(testMap.get(3), null);
        assertEquals(testMap.get(4), null);
        assertNotEquals(testMap.get(4), "b");
        assertEquals(testMap.get(5), "a");
        assertNotEquals(testMap.get(5), null);
        assertEquals(testMap.get(6), "b");
        assertNotEquals(testMap.get(6), null);
	}

}
