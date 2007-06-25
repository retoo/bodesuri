package applikation.client.zustaende;

import applikation.client.events.KarteGewaehltEvent;
import applikation.client.pd.Karte;
import applikation.client.pd.SteuerungsZustand;
import applikation.nachrichten.KartenTausch;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in dem wir warten bis der Spieler die Karte die er tauschen möchte
 * ausgewählt hat. Tritt {@link KarteGewaehltEvent} ein gehen wir in den Zustand
 * {@link KarteTauschenBekommen} über.
 */
public class KarteTauschenAuswaehlen extends ClientZustand {
	public void onEntry() {
		spiel.setHinweis("Zu tauschende Karte wählen.");
		spiel.spielerIch.getKarten().setAktiv(true);
		spiel.spielerIch.setAmZug(true);
		controller.karteTauschenAuswaehlen();
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		if (spiel.ausgewaehlteKarte != null && spiel.ausgewaehlteKarte.istAusgewaehlt()) {
			spiel.ausgewaehlteKarte.setAusgewaehlt(false);
		}
		spiel.ausgewaehlteKarte = event.karte;
		spiel.ausgewaehlteKarte.setAusgewaehlt(true);
		spiel.setSteuerungsZustand(SteuerungsZustand.TAUSCHEN);
		return this.getClass();
	}

	Class<? extends Zustand> kartenTauschBestaetigt() {
		Karte karte = spiel.ausgewaehlteKarte;
		if (karte == null) {
			return this.getClass();
		}
		spiel.ausgewaehlteKarte = null;
		karte.setAusgewaehlt(false);
		spiel.spielerIch.getKarten().remove(karte);
		spiel.sende(new KartenTausch(karte.getKarte()));
		return KarteTauschenBekommen.class;
	}

	public void onExit() {
		spiel.spielerIch.getKarten().setAktiv(false);
		spiel.setSteuerungsZustand(SteuerungsZustand.NICHTS);
	}
}
