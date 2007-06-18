package applikation.client.zustaende;

import applikation.client.pd.Spieler;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class StarteRunde extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		for (Spieler spieler : spiel.getSpieler()) {
			spieler.setAmZug(false);
		}
		return KarteTauschenAuswaehlen.class;
	}
}
