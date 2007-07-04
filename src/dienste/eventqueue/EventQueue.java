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
