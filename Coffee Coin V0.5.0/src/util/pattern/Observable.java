package util.pattern;

import java.util.LinkedList;
import java.util.List;

public class Observable {
	private List<IObserver> observers;	
	
	public Observable() {
		observers  = new LinkedList<>();
	}
	
	public synchronized boolean addObserver(IObserver observer) {
		return observers.add(observer);
	}
	
	public synchronized boolean deleteObserver(IObserver observer) {
		return observers.remove(observer);
	}
	
	public synchronized void notifyObservers() {
		for (IObserver observer: observers) {
			observer.update(this);
		}
	}
}
