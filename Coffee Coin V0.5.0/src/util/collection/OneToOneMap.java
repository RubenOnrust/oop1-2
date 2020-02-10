package util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OneToOneMap<K, V> implements Map<K, V>{
	private HashMap<K, V> map = new HashMap<K, V>();

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
			for (int i = 0; i < map.size(); i++) {
				if (map.get(i) == value) {
					map.remove(i);
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
		for (K key : m.keySet()) {
			if (map.containsValue(m.get(key))) {
				for (int i = 0; i < map.size(); i++) {
					if (map.get(i) == m.get(key)) {
						map.remove(i);
						map.put(key, m.get(key));
					}
				}
			}
			map.put(key, m.get(key));
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
        OneToOneMap<Integer, Integer> testMap = new OneToOneMap<Integer, Integer>();
        testMap.put(1, 1);
        testMap.put(2, 2);
        testMap.put(3, 3);
        HashMap<Integer, Integer> testing = new HashMap<Integer, Integer>();
        testing.put(4, 5);
        testing.put(6, 4);
        testing.put(5, 20); 
        testMap.putAll(testing);
        System.out.println(testMap.keySet());
        System.out.println(testMap.values());
}
}
