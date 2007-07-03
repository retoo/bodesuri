package applikation.client.zustaende;

import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem sich dem Automat befindet wenn wir das Spiel beenden
 * wollen. In diesem Zustand werden Events, die möglicherweise noch in der Queue
 * warten, konsumiert, das UI heruntergefahren, die Verbindung zum Server
 * geschlossen und in den {@link EndZustand} gewechselt, der den Automaten
 * beendet.
 */
public class SpielEnde extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		controller.herunterfahren();
		if (spiel.endpunkt != null) {
			spiel.endpunkt.ausschalten();
		}
		return EndZustand.class;
	}
}
