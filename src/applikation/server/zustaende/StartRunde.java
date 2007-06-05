package applikation.server.zustaende;

import java.util.List;

import pd.karten.Karte;
import pd.karten.KartenGeber;

import applikation.nachrichten.RundenStart;
import applikation.server.Runde;
import applikation.server.Spieler;
import dienste.automat.zustaende.Zustand;

public class StartRunde extends PassiverServerZustand {
	public Zustand handle() {
		Runde runde = automat.spielerschaft.starteRunde();

		int anzahlKarten = runde.getAnzahlKartenProSpieler();
		KartenGeber kartenGeber = automat.spiel.getKartenGeber();
		for (Spieler spieler : automat.spielerschaft) {
			List<Karte> karten = kartenGeber.getKarten(anzahlKarten);
			RundenStart rundenStart = new RundenStart(karten);
			spieler.sende(rundenStart);
		}

		return automat.getZustand(KartenTauschen.class);
	}
}
