package applikation.client.zustaende;

import pd.regelsystem.RegelVerstoss;
import applikation.server.nachrichten.ChatNachricht;
import applikation.server.nachrichten.ZugInformation;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

public class NichtAmZug extends AktiverClientZustand {
	Zustand chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this;
	}

	Zustand zugAufforderung() {
		return automat.getZustand(AmZug.class);
	}
	
	Zustand zugWurdeGemacht(ZugInformation information) {
		try {
	        information.zug.validiere().ausfuehren();
        } catch (RegelVerstoss e) {
        	System.out.println("Ungültigen Zug (" + e + ") vom Server erhalten!");
        	return automat.getZustand(SchwererFehler.class);		
        }
		return this;
	}
}
