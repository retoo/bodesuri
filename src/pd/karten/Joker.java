package pd.karten;

import java.util.List;

import pd.regelsystem.RegelVeroderung;

public class Joker extends Karte {
	public Joker(KartenFarbe farbe) {
		super("Joker", farbe);
		RegelVeroderung regel = new RegelVeroderung();
		List<Karte> karten = Deck.getKartenFuerFarbe(KartenFarbe.Herz);
		for (Karte karte : karten) {
			regel.fuegeHinzu(karte.getRegel());
		}
		setRegel(regel);
	}

	public String toString() {
		return "Joker";
	}

	public String getRegelBeschreibung() {
		return "Als beliebige Karte einsetzbar";
	}
}
