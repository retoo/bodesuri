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


package ch.bodesuri.dienste.automat.zustaende;

import ch.bodesuri.dienste.eventqueue.Event;

/**
 * Zustand in einem Zustands-Automaten. Jedem Zustand gewisse Tätigkeiten beim Betreten
 * bzw. Verlassen zugewiesen werden.
 *
 * Die Klasse sollte nicht direkt abgeleitet werden, stattessen sollten
 * die Klassen {@link Zustand} und {@link PassiverZustand} erweitert werden.
 */
public abstract class Zustand implements ZustandsInterface {
	/* (non-Javadoc)
	 * @see dienste.automat.zustaende.ZustandsInterface#onEntry()
	 */
	public void onEntry() {
	}

	/* (non-Javadoc)
	 * @see dienste.automat.zustaende.ZustandsInterface#onExit()
	 */
	public void onExit() {
	}

	/**
     * Verarbeitet einen übergebenen Event und teilt dem Zustandsautomaten mit
     * welcher nächster Zustand ausgeführt werden soll. Diese Methode führt
     * hierzu je nach Event spezialisierte handler Methoden aus welche von den
     * verschiedenen Zuständen implementiert werden können
     *
     * @param event
     *            Ausführender Event
     * @return Den nächsten Zustand
     */
    public Class<? extends Zustand> handle(Event event) {
    	String msg = "Zustand " + this + " hat keinen Übergang für den Event "
    	             + event;
    	throw new UnbekannterEventException(msg);
    }

	protected Class<? extends Zustand> keinUebergang() {
    	throw new KeinUebergangException("Kein Übergang definiert in Zustand "
    	                                 + this);
    }

	/**
	 * Wird dieser Übergang verwendet wird der Event ignoriert und eine kleine Meldung
	 * ausgegeben
	 *
	 * @param event String der ausgegben werden soll.
	 * @return immer die aktuelle Klase
	 */
	protected Class<? extends Zustand> ignoriereEvent(String event) {
		System.out.println("Ignoriere Event: " + event);

		return this.getClass();
    }
	/**
	 * Dieser Übergang ignoriert den aktuellen Event komplett.
	 *
	 * @return immer die aktuelle Klasse
	 */
	protected Class<? extends Zustand> ignoriereEvent() {
		return this.getClass();
    }

	/**
	 * Sollte der Automat eine Exception erhalten wird diese
	 * an diesen Handler weitergereicht. Wird diese Methode überschrieben
	 * kann ein alternatives Handling implementiert werden. Z.B. könnte
	 * man sihc überlegen dem User eine Fehlermeldung zukommen zu lassen.
	 *
	 * @param exception die ursprüngliche Exception
	 */
	public void handleException(RuntimeException exception) {
		throw exception;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
