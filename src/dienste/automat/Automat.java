package dienste.automat;

import java.util.IdentityHashMap;
import java.util.Map;

public class Automat {
	private Zustand start;
	private EventQuelle eventQuelle;
	private Map<Class<? extends Zustand>, Zustand> zustaende;

	public Automat() {
		zustaende = new IdentityHashMap<Class<? extends Zustand>, Zustand>();
	}

	/**
	 * Registriert einen neuen Zustand
	 * 
	 * @param zustand
	 *            hinzuzufügende Zustand
	 */
	public void registriere(Zustand zustand) {
		zustand.setAutomat(this);
		zustaende.put(zustand.getClass(), zustand);
	}

	public void setStart(Class<? extends Zustand> klasse) {
		start = getZustand(klasse);

		if (start == null) {
			throw new RuntimeException("Unbekannter Start-Zustand" + klasse);
		}
	}

	public void setEventQuelle(EventQuelle quelle) {
		eventQuelle = quelle;
	}

	/**
	 * Such die Instanz des Zustandes der der übergebener Klasse angehört und an
	 * diesen Automaten gebunden ist.
	 * 
	 * @param klasse
	 *            Klasse des gesuchten Zustandes
	 * @return Instanz eines Zustandes
	 */
	public Zustand getZustand(Class<? extends Zustand> klasse) {
		Zustand zustand = zustaende.get(klasse);

		if (zustand == null)
			throw new RuntimeException("Nichtregistierer Zustand " + klasse);

		return zustand;
	}

	/**
	 * Startet den Mainloop
	 */
	public void run() {
		pruefeAutomat();
			
		
		Zustand aktuellerZustand = start;

		while (true) {
			Zustand neuerZustand;

			System.out.println("Uebergang nach: " + aktuellerZustand);

			aktuellerZustand.init();

			if (aktuellerZustand instanceof AktiverZustand) {
				AktiverZustand zustand = (AktiverZustand) aktuellerZustand;

				Event event = eventQuelle.getEvent();

				neuerZustand = zustand.handle(event);
			} else {
				PassiverZustand zustand = (PassiverZustand) aktuellerZustand;
				neuerZustand = zustand.execute();
			}
			aktuellerZustand = neuerZustand;
		}
	}

	/**
	 * Prüft den Automaten auf Fehler
	 */
	private void pruefeAutomat() {
	    if (zustaende.isEmpty())
			throw new RuntimeException("Keine Zustände registriert");

		if (start == null)
			throw new RuntimeException("Kein Startzustand gesetzt");
		
		if (eventQuelle == null) 
			throw new RuntimeException("Keine EventQuelle definiertt");
    }
}
