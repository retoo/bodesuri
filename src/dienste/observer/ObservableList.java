package dienste.observer;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.Collections;

import dienste.observer.ListChangeEvent.ListChangeType;

/**
 * An ObservableList is a wrapped list which detects any modification to this
 * list (the actual structure and the content). The class is implemented as a
 * delegator and redirects any calls to the embedded object list.
 * 
 * In addition to changes to the list, the class reports any Observable events
 * of all objects which are stored in this list.
 * 
 * The ObservableList implements a subset of the List<T> functionality.
 * 
 * This class is not meant to be used directly, it should be subclassed.
 * 
 * @param <E>
 *            Class of the contained objects
 */
public class ObservableList<E>
		extends Observable implements Observer, Serializable, Iterable<E> {
	/**
	 * The actual data storage 
	 */
	List<E> list;

	/**
	 * Creates a new OservableList
	 */
	public ObservableList() {
		list = new Vector<E>();
	}

	/* Being observed */

	/**
	 * Internal method which announces that a object has been added
	 * 
	 * @param index index of the added object
	 * @param object object which was added
	 */
	private void announceAdded(int index, E object) {
		if (object instanceof Observable) {
			((Observable) object).addObserver(this);
		}
		setChanged();
		notifyObservers(new ListChangeEvent(ListChangeType.ADDED, index,
		                                    object));
	}

	/**
	 * Internal method which announces that a given object has been removed
	 * 
	 * @param index index of the removed object
	 * @param object object which has been removed
	 */
	private void announceRemoved(int index, E object) {
		if (object instanceof Observable) {
			((Observable) object).deleteObserver(this);
		}
		setChanged();
		notifyObservers(new ListChangeEvent(ListChangeType.REMOVED, index,
		                                    object));
	}

	/**
	 * Internal method which announces that a object has been changed
	 * 
	 * @param index index of the changed object
	 * @param object object which got changed (or has replaced another one)
	 */
	private void announcedChanged(int index, E object) {
		setChanged();
		notifyObservers(new ListChangeEvent(ListChangeType.CHANGED, index,
		                                    object));
	}	

	/** 
	 * Announce that the whole liset has been changed
	 */
	private void announceChanged() {
		setChanged();
		notifyObservers(new ListChangeEvent());
	}


	/* Observing our children */
	
	/**
	 * The update gets called if one of the objects stored in this list have
	 * been changed.
	 * 
	 * After a change has been announced, the list tries to find out which
	 * object got changed. If the changed object can't be found in the list then
	 * you are facing a serious internal error - so beware!
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object obj) {
		int index = list.indexOf(o);

		/*
		 * check if one of our children was removed from our list without
		 * removing the observer
		 */
		if (index == -1) {
			throw new RuntimeException("Unable to find index of " + o);
		}

		setChanged();
		notifyObservers(new ListChangeEvent(ListChangeType.CHANGED, index,
		                                    o));
	}

	/** 
	 * Internal helper method which tears down all observers
	 */
	private void teardownObservers() {
		for (Object o : list) {
			if (o instanceof Observable) {
				((Observable) o).deleteObserver(this);
			}
		}
	}

	/**
	 * @return size of list
	 * @see List#size()
	 */
	public int size() {
		return list.size();
	}

	/**
	 * checks if the given object is stored in the list
	 * 
	 * @param object
	 * @return whether the object is within the list
	 * 
	 * @see List#contains(Object)
	 */
	public boolean contains(E object) {
		return list.contains(object);
	}

	/**
	 * returns the object at the given address
	 * 
	 * @param index
	 *            index of the object which should be retrieved
	 * @return the object
	 * @throws IndexOutOfBoundsException
	 *             if the given index was outside the list boundaries
	 * @see List#get(int)
	 */
	public E get(int index) {
		return list.get(index);
	}

	/**
	 * Adds a given object at the end of the list and announces it
	 * 
	 * @param object object which has to be added
	 * @return whether the operation has been successful
	 * @see List#add(Object)
	 */
	public boolean add(E object) {
		boolean res = list.add(object);

		if (res) {
			announceAdded(list.size() - 1, object);
		}

		return res;
	}

	/**
	 * Removes a given object and announce its removal
	 * 
	 * @param object
	 *            object which has to be removed
	 * @return whether the removal was successful. returns false if the object
	 *         can't be found in the list
	 * @see List#remove(Object)
	 */
	public boolean remove(E object) {
		/*
		 * get the index first, directly using remove(Obj o) is no option as we
		 * need the affected index for the announcement
		 */
		int index = list.indexOf(object);
		
		if (index < 0) {
			return false;
		} else {
			remove(index);
			return true;
		}
	}

	/**
	 * Completely clears a list
	 * 
	 * @see List#clear()
	 */
	public void clear() {
		teardownObservers();
		list.clear();
		announceChanged();
	}

	/**
	 * Adds a object at the given index and announces it 
	 * 
	 * @param index index at which the object should be added
	 * @param element the object which should be added
	 * @see List#add(int, Object)
	 */
	public void add(int index, E element) {
		list.add(index, element);
		announceAdded(index, element);
	}

	/**
	 * Removes the object at a given index and announce it's removal 
	 * 
	 * @param index index of the object to be removed
	 * @return the removed element
	 * @see List#remove(int)
	 */
	public E remove(int index) {
		E e = list.remove(index);
		announceRemoved(index, e);
		return e;
	}

	/**
	 * @see List#iterator()
	 */
	public Iterator<E> iterator() {
		return Collections.unmodifiableList(list).iterator();
	}

	/**
	 * Swaps the objects at indexA and indexB
	 * 
	 * @param indexA
	 * @param indexB
	 */
	public void swap(int indexA, int indexB) {
		E oldA = get(indexA);
		E oldB = get(indexB);
		list.set(indexA, oldB);
		list.set(indexB, oldA);
	
		announcedChanged(indexA, oldB);
		announcedChanged(indexB, oldA);
	}
}
