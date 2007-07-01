package applikation.nachrichten;

import java.util.List;

import pd.regelsystem.karten.Karte;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht mit der Server dem Client mitteilt wenn eine neue Runde beginnt.
 */
public class RundenStart extends Nachricht {
	public final List<Karte> neueKarten;

	public RundenStart(List<Karte> neueKarten) {
		this.neueKarten = neueKarten;
	}
}
