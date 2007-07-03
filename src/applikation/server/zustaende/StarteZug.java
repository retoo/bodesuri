package applikation.server.zustaende;

import pd.zugsystem.Zug;
import applikation.nachrichten.AktuellerSpielerInformation;
import applikation.nachrichten.ZugAufforderung;
import applikation.server.pd.Spieler;
import applikation.server.pd.Spiel;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Ein neuer {@link Zug} wird gestartet. Hierfür bestimmt der Server zuerst wer an der
 * Reihe ist und teilt dies dem betroffenen Spieler mittels der Nachricht
 * {@link ZugAufforderung} mit.
 *
 * Die restlichen Spielern werden mit der Nachricht {@link AktuellerSpielerInformation}
 * darüber informiert wer gerade am Zug ist.
 *
 * Anschliessend wird direkt in den Zustand {@link WarteAufZug} gewechselt.
 */
public class StarteZug extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		Spiel spielers = spiel;

		spielers.runde.rotiereSpieler();
		Spieler naechsterSpieler = spielers.runde.getAktuellerSpieler();

		AktuellerSpielerInformation info = new AktuellerSpielerInformation(naechsterSpieler.spieler);
		spielers.broadcast(info);

		ZugAufforderung aufforderung = new ZugAufforderung();
		naechsterSpieler.sende(aufforderung);

		return WarteAufZug.class;
	}
}
