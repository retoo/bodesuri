package dienste.eventqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Die EventQueue ermöglicht die Zwischenspeicherung von zu bearbeitenden
 * Events. Die Queue ist thread-safe und arbeitet nach dem FIFO-Prinzip.
 */
public class EventQueue {
	private BlockingQueue<Event> queue;

	/**
	 * Erstellt eine neue EventQueue.
	 */
	public EventQueue() {
		queue = new LinkedBlockingQueue<Event>();
	}

	/**
	 * Enqueued einen neuen Event. Falls die Queue voll ist blockiert der Aufruf
	 * bis der Event hinzugefügt werden kann.
	 *
	 * @param event
	 *            Event der hinzugefügt werden soll
	 */
	public void enqueue(Event event) {
		try {
			queue.put(event);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Entfernt den ältesten Event aus der EventQueue. Falls die Queue leer ist
	 * blockiert der Aufruf bis ein Event eintrifft.
	 *
	 * @return ältester anstehender Event
	 */
	public Event dequeue() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Prüft ob die EventQueue leer ist. Warnung, ist nich thread-safe
	 * @return ob Queue leer ist
	 */
	public boolean isLeer() {
		return queue.isEmpty();
	}
}
