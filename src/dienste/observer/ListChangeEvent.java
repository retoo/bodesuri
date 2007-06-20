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
	public ListChangeType changeType;
	/**
	 * Index of the changed object.
	 */
	public int changedIndex;
	/**
	 * Changed object.
	 */
	public Object changedObject;

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
	
	public Object getChangeObject(){
		return changedObject;
	}
}
