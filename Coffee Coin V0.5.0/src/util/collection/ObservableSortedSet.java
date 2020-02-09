package util.collection;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import util.pattern.Observable;

public class ObservableSortedSet<T> extends Observable implements SortedSet<T> {
	private static ObservableSortedSet<?> internalObject = null;
	private SortedSet<T> set;
	
	private  ObservableSortedSet() {
		set = new TreeSet<T>();
	}
	
	public static ObservableSortedSet<?> instance() {
		if (internalObject == null) {
			internalObject = new ObservableSortedSet<Object>();
		}
		return internalObject;
	}

	@Override
	public boolean add(T arg0) {
		notifyObservers();
		return set.add(arg0);
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		notifyObservers();
		return set.addAll(arg0);
	}

	@Override
	public void clear() {
		notifyObservers();
		set.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		return set.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return set.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	// This one is tricky; if the iterator deletes something,
	// the observers should be triggered
	@Override
	public Iterator<T> iterator() {
		return new ObservableSortedSetIterator();
	}

	@Override
	public boolean remove(Object arg0) {
		notifyObservers();
		return set.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		notifyObservers();
		return set.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		notifyObservers();
		return set.retainAll(arg0);
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] arg0) {
		return set.toArray(arg0);
	}

	@Override
	public Comparator<? super T> comparator() {
		return set.comparator();
	}

	@Override
	public T first() {
		return set.first();
	}

	@Override
	public SortedSet<T> headSet(T arg0) {
		return set.headSet(arg0);
	}

	@Override
	public T last() {
		return set.last();
	}

	@Override
	public SortedSet<T> subSet(T arg0, T arg1) {
		return set.subSet(arg0, arg1);
	}

	@Override
	public SortedSet<T> tailSet(T arg0) {
		return set.tailSet(arg0);
	}
	
	private class ObservableSortedSetIterator implements Iterator<T> {
		private Iterator<T> i;
		
		public ObservableSortedSetIterator() {
			i = set.iterator();
		}

		@Override
		public boolean hasNext() {
			return i.hasNext();
		}

		@Override
		public T next() {
			return i.next();
		}
		
		@Override
		public void remove() {
			i.remove();
			notifyObservers();
		}
	}
}
