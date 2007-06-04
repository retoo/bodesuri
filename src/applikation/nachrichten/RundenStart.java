package applikation.nachrichten;

import java.util.List;

import pd.karten.Karte;

import dienste.netzwerk.Nachricht;

public class RundenStart extends Nachricht {
	public final List<Karte> neueKarten;
	
	public RundenStart(List<Karte> neueKarten) {
		this.neueKarten = neueKarten;
	}
}
