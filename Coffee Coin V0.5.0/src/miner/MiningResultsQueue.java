package miner;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import domain.Block;

public class MiningResultsQueue implements BlockingQueue<Block> {
	private static MiningResultsQueue internalObject = null;
	private BlockingQueue<Block> queue;
	
	private MiningResultsQueue() {
		queue = new ArrayBlockingQueue<Block>(1000, true);
	}
	
	public static MiningResultsQueue instance() {
		if (internalObject == null) {
			internalObject = new MiningResultsQueue();
		}
		return internalObject;
	}

	@Override
	public Block element() {
		return queue.element();
	}

	@Override
	public Block peek() {
		return queue.peek();
	}

	@Override
	public Block poll() {
		return queue.poll();
	}

	@Override
	public Block remove() {
		return queue.remove();
	}

	@Override
	public boolean addAll(Collection<? extends Block> c) {
		return queue.addAll(c);
	}

	@Override
	public void clear() {
		queue.clear();	
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public Iterator<Block> iterator() {
		return (Iterator<Block>) queue.iterator();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return queue.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return queue.retainAll(c);
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public Object[] toArray() {
		return queue.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return queue.toArray(a);
	}

	@Override
	public boolean add(Block e) {
		return queue.add(e);
	}

	@Override
	public boolean contains(Object o) {
		return queue.contains(o);
	}

	@Override
	public int drainTo(Collection<? super Block> c) {
		return queue.drainTo(c);
	}

	@Override
	public int drainTo(Collection<? super Block> c, int maxElements) {
		return queue.drainTo(c, maxElements);
	}

	@Override
	public boolean offer(Block e) {
		return queue.offer(e);
	}

	@Override
	public boolean offer(Block e, long timeout, TimeUnit unit) throws InterruptedException {
		return queue.offer(e, timeout, unit);
	}

	@Override
	public Block poll(long timeout, TimeUnit unit) throws InterruptedException {
		return queue.poll(timeout, unit);
	}

	@Override
	public void put(Block e) throws InterruptedException {
		queue.put(e);
	}

	@Override
	public int remainingCapacity() {
		return queue.remainingCapacity();
	}

	@Override
	public boolean remove(Object o) {
		return queue.remove(o);
	}

	@Override
	public Block take() throws InterruptedException {
		return queue.take();
	}

	
}
