package applikation.client.events;

import pd.zugsystem.Bewegung;
import dienste.automat.Event;

public class ZugEingegebenEvent extends Event {
	public final Bewegung bewegung;
	
	public ZugEingegebenEvent(Bewegung bewegung) {
		this.bewegung = bewegung;
	}
}
