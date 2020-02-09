package util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import util.pattern.IObserver;
import util.pattern.Observable;

/* This class has the properties of a Sorted Set, with extra functionality:
 * 1. It implements both the Observer and the Sorted Set interfaces
 * 2. It only accepts objects extending the Observable class
 * 3. It can only be instantiated giving it a Comparator object for Observables
 * 4. Whenever an object in the set changes, it has to inform its observers.
 * 5. When either an object in the set changes or the Comparator changes, the set orders itself according to the new situation
 */

public class DynamicSortedSet<T extends Observable> implements IObserver, SortedSet<T>  {
	private SortedSet<T> set;
	private Comparator<T> comparator;
	
	public DynamicSortedSet(Comparator<T> comparator) {
		set = new TreeSet<T>();
		this.comparator = comparator;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
		resort();
	}

	@Override
	public synchronized boolean add(T e) {
		e.addObserver(this);
		return set.add(e);
	}

	@Override
	public synchronized boolean addAll(Collection<? extends T> c) {
		for (T element: c) {
			element.addObserver(this);
		}
		return set.addAll(c);
	}

	@Override
	public synchronized void clear() {
		for (T element: set) {
			element.deleteObserver(this);
		}
		set.clear();
	}

	@Override
	public synchronized boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public synchronized boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public synchronized boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public synchronized Iterator<T> iterator() {
		return set.iterator();
	}

	@Override
	public synchronized boolean remove(Object o) {
		@SuppressWarnings("unchecked")
		T element = (T) o;
		element.deleteObserver(this);
		return set.remove(o);
	}

	@Override
	public synchronized boolean removeAll(Collection<?> c) {
		boolean result = false;
		for (T element: set) {
			result = result || remove(element);
		}
		return result;
	}

	@Override
	public synchronized boolean retainAll(Collection<?> c) {
		boolean result = false;
		Iterator<T> i = set.iterator();
		while (i.hasNext()) {
			T element = i.next();
			if (!c.contains(element)) {
				element.deleteObserver(this);
				i.remove();
				result = true;
			}
		}
		return result;
	}

	@Override
	public synchronized int size() {
		return set.size();
	}

	@Override
	public synchronized Object[] toArray() {
		return set.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public synchronized <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	@Override
	public synchronized Comparator<? super T> comparator() {
		return set.comparator();
	}

	@Override
	public synchronized T first() {
		return set.first();
	}

	@Override
	public synchronized SortedSet<T> headSet(T toElement) {
		return set.headSet(toElement);
	}

	@Override
	public synchronized T last() {
		return set.last();
	}

	@Override
	public synchronized SortedSet<T> subSet(T fromElement, T toElement) {
		return set.subSet(fromElement, toElement);
	}

	@Override
	public synchronized SortedSet<T> tailSet(T fromElement) {
		return set.tailSet(fromElement);
	}

	@Override
	public synchronized void update(Observable o) {
		resort();
	}
	
	private synchronized void resort() {
		Collection<T> temp = new ArrayList<T>(set);
		set = new TreeSet<T>(comparator);
		set.addAll(temp);
	}
}
