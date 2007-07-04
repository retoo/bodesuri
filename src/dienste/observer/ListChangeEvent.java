/*
 * Copyright (C) 2007  Danilo Couto, Philippe Eberli,
 *                     Pascal Hobus, Reto Schüttel, Robin Stocker
 *
 * This file is part of Bodesuri.
 *
 * Bodesuri is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * Bodesuri is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bodesuri; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


package dienste.observer;

/**
 * This class encapsulates the event information for the communication between
 * the ObservableList and different adapters like the TableModel.
 *
 * Note: Getters and setters have been intentionally omitted as this class is a
 * struct-like data structure.
 */
public class ListChangeEvent {
	/**
	 * Type of the change. This can be ADDED, REMOVED, CHANGED or EVERYTHING.
	 *
	 *  @see ListChangeEvent.ListChangeType
	 */
	public final ListChangeType changeType;
	/**
	 * Index of the changed object.
	 */
	public final int changedIndex;
	/**
	 * Changed object.
	 */
	public final Object changedObject;

	public enum ListChangeType {
	/**
	 * A new object has been added.
	 */
	ADDED,
	/**
	 * An object was removed.
	 */
	REMOVED,
	/**
	 * A given object has been changed or replaced.
	 */
	CHANGED,
	/**
	 * The whole list may have been changed.
	 */
	EVERYTHING}

	/**
	 * Creates a new ListChangeEvent which reports about a partial change of a
	 * list. All relevant data has to be passed to the constructor.
	 * @param changeType
	 *            Kind of change, like ADDED or CHANGED
	 * @param changedIndex
	 *            Index of the object which was changed
	 * @param changedObject
	 *            Object which was changed
	 */
	public ListChangeEvent(ListChangeType changeType,
			int changedIndex, Object changedObject) {
		this.changeType = changeType;
		this.changedIndex = changedIndex;
		this.changedObject = changedObject;
	}

	/**
	 * Creates a ListChangeEvent which announces a full change (all rows are
	 * effected).
	 */
	public ListChangeEvent() {
		changeType = ListChangeType.EVERYTHING;
		changedIndex = 0;
		changedObject = null;
	}

	public String toString() {
		return "" + changeType + " #" + changedIndex + " " + changedObject;
	}
}
