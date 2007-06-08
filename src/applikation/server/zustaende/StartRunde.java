package applikation.server.zustaende;

import java.util.List;

import pd.karten.Karte;
import pd.karten.KartenGeber;

import applikation.nachrichten.RundenStart;
import applikation.server.Runde;
import applikation.server.Spieler;
import dienste.automat.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class StartRunde extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		Runde runde = spielDaten.spielerschaft.starteRunde();

		int anzahlKarten = runde.getAnzahlKartenProSpieler();
		KartenGeber kartenGeber = spielDaten.spiel.getKartenGeber();
		for (Spieler spieler : spielDaten.spielerschaft) {
			List<Karte> karten = kartenGeber.getKarten(anzahlKarten);
			RundenStart rundenStart = new RundenStart(karten);
			spieler.sende(rundenStart);
		}

		return KartenTauschen.class;
	}
}
