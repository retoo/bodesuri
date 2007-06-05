package applikation.client.zustaende;

import pd.zugsystem.ZugEingabe;
import applikation.client.zugautomat.ZugAutomat;
import applikation.events.FeldGewaehltEvent;
import applikation.events.KarteGewaehltEvent;
import applikation.nachrichten.ZugInformation;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem der Spieler dran kommt. Erstellt einen {@link ZugAutomat}
 * der sich um das Erfassen und Validieren eines Zuges kümmert. Der Automat
 * sendet eine {@link ZugEingabe} wenn er fertig ist. Diese wird versandt und
 * der Automat geht nach {@link NichtAmZug} über.
 */
public class AmZug extends AktiverClientZustand {
	Zustand feldGewaehlt(FeldGewaehltEvent event) {
		automat.zugAutomat.step(event);
		return this;
	}

	Zustand karteGewaehlt(KarteGewaehltEvent event) {
		automat.zugAutomat.step(event);
		return this;
	}

	Zustand gezogen(ZugEingabe zugEingabe) {
		automat.endpunkt.sende(new ZugInformation(zugEingabe));
		return automat.getZustand(NichtAmZug.class);
	}
}
