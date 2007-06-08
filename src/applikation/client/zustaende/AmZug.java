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
public class AmZug extends ClientZustand {
	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		spielDaten.zugAutomat.step(event);
		return this.getClass();
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spielDaten.zugAutomat.step(event);
		return this.getClass();
	}

	Class<? extends Zustand> gezogen(ZugEingabe zugEingabe) {
		spielDaten.endpunkt.sende(new ZugInformation(zugEingabe));
		return NichtAmZug.class;
	}
}
