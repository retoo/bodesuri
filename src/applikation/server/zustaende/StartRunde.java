package applikation.server.zustaende;

import java.util.List;

import pd.karten.Karte;
import pd.karten.KartenGeber;

import applikation.nachrichten.RundenStart;
import applikation.server.pd.Runde;
import applikation.server.pd.Spieler;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class StartRunde extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		Runde runde = spiel.starteRunde();

		int anzahlKarten = runde.getAnzahlKartenProSpieler();
		KartenGeber kartenGeber = spiel.getKartenGeber();
		for (Spieler spieler : spiel.spielers) {
			List<Karte> karten = kartenGeber.getKarten(anzahlKarten);
			RundenStart rundenStart = new RundenStart(karten);
			spieler.sende(rundenStart);
		}

		return KartenTauschen.class;
	}
}
