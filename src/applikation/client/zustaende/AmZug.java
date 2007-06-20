package applikation.client.zustaende;

import pd.zugsystem.ZugEingabe;
import applikation.client.events.FeldAbgewaehltEvent;
import applikation.client.events.FeldGewaehltEvent;
import applikation.client.events.HoverEndeEvent;
import applikation.client.events.HoverStartEvent;
import applikation.client.events.KarteGewaehltEvent;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.zugautomat.ZugAutomat;
import applikation.nachrichten.Aufgabe;
import applikation.nachrichten.ZugInformation;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem der Spieler dran kommt. Erstellt einen {@link ZugAutomat}
 * der sich um das Erfassen und Validieren eines Zuges kümmert. Der Automat
 * sendet eine {@link ZugErfasstEvent} wenn er fertig ist. Diese wird versandt
 * und der Automat geht nach {@link NichtAmZug} über.
 */
public class AmZug extends ClientZustand {
	private boolean hatEndModusErreicht = false;
	
    public void onEntry() {
		spiel.zugAutomat = new ZugAutomat(controller, spiel);
		spiel.zugAutomat.init();
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		spiel.zugAutomat.step(event);
		return this.getClass();
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spiel.zugAutomat.step(event);
		return this.getClass();
	}

	Class<? extends Zustand> hoverStart(HoverStartEvent event) {
		spiel.zugAutomat.step(event);
		event.feld.setHover(true);
		return this.getClass();
	}

	Class<? extends Zustand> hoverEnde(HoverEndeEvent event) {
		spiel.zugAutomat.step(event);
		event.feld.setHover(false);
		return this.getClass();
	}

	Class<? extends Zustand> feldAbgewaehlt(FeldAbgewaehltEvent event) {
		spiel.zugAutomat.step(event);
		return this.getClass();
	}

	Class<? extends Zustand> gezogen(ZugErfasstEvent erfassterZug) {
		erfassterZug.getKarte().setAusgewaehlt(false);
		spiel.spielerIch.getKarten().remove(erfassterZug.getKarte());
		spiel.endpunkt.sende(new ZugInformation(erfassterZug.toZugEingabe()));
		
		// Wenn der Spieler fertig wird, werden dessen Figuren dem Partner hinzugefügt. 
		// Dies wird nur einmalig ausgeführt.
		if ( spiel.spielerIch.spieler.istFertig() && !hatEndModusErreicht ) {
			spiel.spielerIch.spieler.addPartnerFiguren();
			hatEndModusErreicht = true;
		}
		
		return NichtAmZug.class;
	}

	Class<? extends Zustand> aufgegeben() {
		if (spiel.spielerIch.kannZiehen()) {
			int i = 0;

			for (ZugEingabe ze : spiel.zugHistory) {
				System.out.println(ze);

				if (i++ > 30) {
					break;
				}
			}

			controller.zeigeFehlermeldung("Es kann noch nicht aufgegeben "
			                              + "werden, da es noch möglich ist zu "
			                              + "ziehen.");
			return this.getClass();
		} else {
			spiel.endpunkt.sende(new Aufgabe());
			spiel.spielerIch.getKarten().setAktiv(false);
			spiel.spielerIch.getKarten().clear();
			return NichtAmZug.class;
		}
	}
}
