package util.collection;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import domain.Asset;
import util.IllegalOperationException;
import util.hash.HasherFactory;
import util.hash.IHashable;

// A class which behaves as a Set (only unique objects), and which keeps a stable order
// (items are kept in order they were put it, making the object behave like a list.)
// Deleting (or changing, next part of the assignment) items is not allowed
// The objects are not allowed to decide their uniqueness themselves;
// this is determined by the hash value of the object (IHash interface, not Object interface)

public class StableOrderSet<T extends IHashable> implements List<T>, IHashable {
	private static final long serialVersionUID = -7048063647471174339L;
	private List<T> list = new LinkedList<T>();
	private Set<T> set;
	
	public StableOrderSet() {
		list = new LinkedList<T>();
		set = new HashSet<T>();
	}

	@Override
	public boolean add(T e) {
		return (set.add(e) && list.add(e)); // Will first do the set.add(); if this returned false,
											// will not call list.add().
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return (set.addAll(c) && list.addAll(c)); 
	}

	@Override
	public void clear() {
		throw new IllegalOperationException("Operation clear() not allowed by StableOrderSet.");
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return new StableOrderSetIterator<T>(this); 
	}

	@Override
	public boolean remove(Object o) {
		throw new IllegalOperationException("Operation remove() not allowed by StableOrderSet.");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new IllegalOperationException("Operation removeAll() not allowed by StableOrderSet.");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new IllegalOperationException("Operation retainAll() not allowed by StableOrderSet.");
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public void add(int index, T element) {
		throw new IllegalOperationException("Operation add(int index, T element) not allowed by StableOrderSet.");
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new IllegalOperationException("Operation addAll(int index, Collection c) not allowed by StableOrderSet.");
	}

	@Override
	public T get(int index) {
		return list.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new IllegalOperationException("Operation ListIterator() not allowed by StableOrderSet.");
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		throw new IllegalOperationException("Operation ListIterator(int index) not allowed by StableOrderSet.");

	}

	@Override
	public T remove(int index) {
		throw new IllegalOperationException("Operation remove(int index) not allowed by StableOrderSet.");
	}

	@Override
	public T set(int index, T element) {
		throw new IllegalOperationException("Operation set(int index, T element) not allowed by StableOrderSet.");
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}
	
	@SuppressWarnings("hiding")
	private class StableOrderSetIterator<T extends IHashable> implements Iterator<T> {
		private Iterator<T> iterator;

		public StableOrderSetIterator(StableOrderSet<T> element) {
			super();
			iterator = element.list.iterator();
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public T next() {
			return iterator.next();
		}
		
		@Override
		public void remove() {
			throw new IllegalOperationException("Operation remove() not allowed by StableOrderSet.");
		}
	}
	
	@Override
	public byte[] hash() throws NoSuchAlgorithmException {
		return HasherFactory.hasher().hash(this);
	}
	
	@Override
	public boolean equals(IHashable other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		Asset theOther = (Asset) other;
		// Checks for null values done
			
		BigInteger hashSelf, hashOther;
		try {
			hashSelf = new BigInteger(this.hash());
			hashOther = new BigInteger(theOther.hash());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Oeps. Crypto algorithm unknown.");
		}
		return hashSelf.equals(hashOther);
	}
}
