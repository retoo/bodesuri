package applikation.client.zustaende;

import pd.zugsystem.ZugEingabe;
import applikation.client.Controller;
import applikation.nachrichten.ZugInformation;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem der Spieler dran kommt. Erstellt einen ZugAutomat der sich
 * um das erfassen, versenden & validieren eines Zuges kümmert. Geht danach in
 * {@link NichtAmZug} über.
 */
public class AmZug extends AktiverClientZustand {
	public AmZug(Controller controller) {
		this.controller = controller;
	}
	
	public void entry() {
		controller.starteZugerfassung();
	}
	
	Zustand gezogen(ZugEingabe zugEingabe) {
		automat.endpunkt.sende(new ZugInformation(zugEingabe));
		return automat.getZustand(NichtAmZug.class);
	}
}
