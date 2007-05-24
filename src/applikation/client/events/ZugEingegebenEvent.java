package applikation.client.events;

import pd.zugsystem.Bewegung;
import dienste.automat.Event;

/**
 * Event der ausgelöst wird wenn der Benutzer eine Bewegung erfasst hat.
 *
 */
public class ZugEingegebenEvent extends Event {
	public final Bewegung bewegung;
	
	/**
	 * Der Benutzer hat eine Bewegung erfasst
	 * @param bewegung Die erfasste Bewegung (Start & Ziel)
	 */
	public ZugEingegebenEvent(Bewegung bewegung) {
		this.bewegung = bewegung;
	}
}
