package util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class OneToOneMap{
	private HashMap<Integer, String> map;

	public OneToOneMap() {
		map = new HashMap<Integer, String>();
	}


	public boolean equals(Object o) {
		return map.equals(o);
	}


	public int hashCode() {
		return map.hashCode();
	}


	public String toString() {
		return map.toString();
	}


	public int size() {
		return map.size();
	}


	public boolean isEmpty() {
		return map.isEmpty();
	}


	public String get(Object key) {
		return map.get(key);
	}


	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public String put(Integer key, String value) {
		if (key == null) {
			throw new NullPointerException("oops a null value");
			
		}
		if (value == null) {
			throw new NullPointerException("oops a null value");
		}
		if (map.containsValue(value) == true) {
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


	public void putAll(Map<? extends Integer, ? extends String> m) {
		map.putAll(m);
	}


	public String remove(Object key) {
		return map.remove(key);
	}


	public void clear() {
		map.clear();
	}


	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}


	public Set<Integer> keySet() {
		return map.keySet();
	}


	public Collection<String> values() {
		return map.values();
	}


	public Set<Entry<Integer, String>> entrySet() {
		return map.entrySet();
	}


	public String getOrDefault(Object key, String defaultValue) {
		return map.getOrDefault(key, defaultValue);
	}


	public String putIfAbsent(Integer key, String value) {
		return map.putIfAbsent(key, value);
	}


	public boolean remove(Object key, Object value) {
		return map.remove(key, value);
	}


	public boolean replace(Integer key, String oldValue, String newValue) {
		return map.replace(key, oldValue, newValue);
	}


	public String replace(Integer key, String value) {
		return map.replace(key, value);
	}


	public String computeIfAbsent(Integer key, Function<? super Integer, ? extends String> mappingFunction) {
		return map.computeIfAbsent(key, mappingFunction);
	}


	public String computeIfPresent(Integer key,
			BiFunction<? super Integer, ? super String, ? extends String> remappingFunction) {
		return map.computeIfPresent(key, remappingFunction);
	}


	public String compute(Integer key,
			BiFunction<? super Integer, ? super String, ? extends String> remappingFunction) {
		return map.compute(key, remappingFunction);
	}


	public String merge(Integer key, String value,
			BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
		return map.merge(key, value, remappingFunction);
	}


	public void forEach(BiConsumer<? super Integer, ? super String> action) {
		map.forEach(action);
	}


	public void replaceAll(BiFunction<? super Integer, ? super String, ? extends String> function) {
		map.replaceAll(function);
	}


	public Object clone() {
		return map.clone();
	}
}
