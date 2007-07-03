package applikation.bot;

import java.util.List;
import java.util.Map;

import pd.regelsystem.ZugEingabe;
import pd.regelsystem.karten.Karte;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.pd.Spiel;

/**
 * Interface eins normalen Bots. Botklassen die dieses Interface impelemntieren müssen
 * fähig sein aus einer Liste möglichen Züge einen intelligenten Zug auszuwählen.
 */
public interface Bot {
	/**
	 * Liefert den gewünschten Zug des Bots zurück.
	 *
	 * @param spiel Das Spiel in welchem der Zug gemacht werden soll
	 * @param moeglich Eine Liste aller möglichen Züge
	 * @param kartenMap Eine Map die alle pd.Karten nach app.client.pd.Karten mapt.
	 * @return Der zu machende Zug oder null sollte kein Zug möglich sein.
	 */
	public ZugErfasstEvent macheZug(Spiel spiel, List<ZugEingabe> moeglich,
			Map<Karte, applikation.client.pd.Karte> kartenMap);
}
