package util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OneToOneMap<K, V> implements Map<K, V>{
	private HashMap<K, V> map;
    private static OneToOneMap<?, ?> single_instance = null; 
    
    private OneToOneMap(K key, V value) 
    { 
    	this.map = new HashMap<K, V>();
    } 
  
    // static method to create instance of Singleton class 
    public static <K,V> Map<?,?> getInstance(K key, V value) 
    { 
        if (single_instance == null) 
            single_instance = new OneToOneMap<K, V>(key, value); 
  
        return single_instance; 
    }

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public V put(K key, V value) {
		if (key.equals(null)) {
			throw new NullPointerException("oops a null value");
			
		}
		if (value.equals(null)) {
			throw new NullPointerException("oops a null value");
		}
		if (map.containsValue(value)) {
			for (K key1 : map.keySet()) {
				if (map.get(key1) == value) {
					map.remove(key1);
					return map.put(key, value);
				}
			}
		}
		else {
			return map.put(key, value);
		}
		return map.put(key, value);
	}

	@Override
	public V remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		OneToOneMap<K, V> testMap = (OneToOneMap<K, V>) OneToOneMap.getInstance(null, null);
		for (K key : m.keySet()) {
			testMap.put(key, m.get(key));
		}    
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}
    public static void main(String[] args) {
        OneToOneMap<Integer, String> testMap = (OneToOneMap<Integer, String>) OneToOneMap.getInstance(null, null);
        testMap.put(1, "a");
        testMap.put(2, "b");
        testMap.put(3, "c");
        HashMap<Integer, String> testing = new HashMap<Integer, String>();
        testing.put(4, "b");
        testing.put(5, "a"); 
        testing.put(6, "b");
        testing.put(7, "b");
        testMap.putAll(testing);
        System.out.println(testMap.keySet());
        System.out.println(testMap.values());
}
}
