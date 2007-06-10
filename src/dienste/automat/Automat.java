package dienste.automat;

import java.util.IdentityHashMap;
import java.util.Map;

import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;

/**
 * Zustandsautomat für alle Lebenslagen. Ermöglicht das saubere abarbeiten eines
 * Zustandsautomaten anhand eingehender Events.
 *
 * Die Erstellung eines Automaten besteht aus folgenden Schritten:
 * <ul>
 * <il>Situationsabhänige aktive Zustandsklasse erstellen</li>
 * <il>Situationsabhänige passive Zustandsklasse erstellen</li>
 * <il>Zustände erstellen die die beiden obigen Klassen als Basis-Klasse
 * verwenden</li>
 * <il>Automat erstellen und alle Zustände mit
 * {@link Automat#registriere(Zustand)} registrieren</li>
 * <il>Startzustand mit {@link Automat#setStart(Class)} definieren</li>
 * <il>EvenQuelle implementieren. Zum Beispiel kann die EventQueue als Baack-End
 * verwendet werden.</li>
 * <il>Automat mit run() starten</li>
 * </ul>
 *
 */
public class Automat {
	private Zustand start;
	private EventQuelle eventQuelle;
	private Map<Class<? extends Zustand>, Zustand> zustaende;
	private Zustand aktuellerZustand;
	private final EndZustand endzustand;

	/**
	 * Erstellt einen neuen Automaten
	 */
	public Automat() {
		zustaende = new IdentityHashMap<Class<? extends Zustand>, Zustand>();

		endzustand = new EndZustand();
		registriere(endzustand);
	}

	/**
	 * Registriert einen neuen Zustand
	 *
	 * @param zustand
	 *            hinzuzufügende Zustand
	 */
	public void registriere(Zustand zustand) {
		zustaende.put(zustand.getClass(), zustand);
	}

	/**
	 * Setzt den übergebenen Zustand als Startzustand
	 *
	 * @param zustandsKlasse
	 *            Klasse des Zustandes der als Startzustand verwendet werden
	 *            soll
	 */
	public void setStart(Class<? extends Zustand> zustandsKlasse) {
		start = getZustand(zustandsKlasse);

		aktuellerZustand = start;
	}

	/**
	 * Setzt die zu verwendende EventQuelle
	 *
	 * @param quelle
	 *            Zum lesen der Eventes verwendete Quelle
	 */
	public void setEventQuelle(EventQuelle quelle) {
		eventQuelle = quelle;
	}

	/**
	 * Startet den Zustandsautomaten und führt ihn so lange bis ein
	 * {@link EndZustand} eintritt.
	 */
	public void run() {
		pruefeAutomat();

		boolean isFertig = aktuellerZustand == endzustand;
		while (!isFertig) {
			isFertig = !step();
		}
	}

	/**
	 * Verarbeitet einen einzigen Zustand. Bei Aktiven Zuständen bedeutet dies,
	 * dass auf neue Events gewartetw wird. Passive hingegen werden direkt
	 * ausgeführt.
	 *
	 *
	 * Methode sollte ausser für Testcases nicht direkt verwendet werden. Der
	 * Zustandsuatomat sollte stattdesssen mit {@link Automat#run} abgearbeitet
	 * werden
	 *
	 * @see Zustand
	 * @see PassiverZustand
	 * @see EndZustand
	 *
	 * @return true falls sich der Automat im Endzustand befindet
	 */
	public boolean step() {
		if (aktuellerZustand instanceof PassiverZustand) {
			return stepPassiv();
		} else if (aktuellerZustand instanceof EndZustand) {
			return false;
		} else {
			Event event = eventQuelle.getEvent();
			return stepAktiv(event);
		}
	}

	/**
	 * Ermöglicht das verarbeiten von Zuständen mit extern eingelesenen Events.
	 * Kann z.B. für Unterautomaten verwendet werden
	 *
	 * @param event
	 *            Zu verarbeitender Event
	 * @return true falls sich der Automat im Endzustand befindet
	 */
	public boolean step(Event event) {
		if (aktuellerZustand instanceof PassiverZustand) {
			throw new RuntimeException(
			                           "step(event) in einem passiven Zustande aufgerufen."
			                           + " Zustand: "
			                           + aktuellerZustand);
		}

		boolean res = stepAktiv(event);

		/* Wir brechen hier ab falls wir uns im Endzustand befinden */
		if (!res)
			return false;

		return verarbeitePassiveZustaende();
	}

	/**
	 * Verarbeitet einen 'Aktiven' Zustand.
	 *
	 * @param event
	 *            Zu verarbeitende event
	 * @return t ob sich der Automat im Endzustand befindet
	 */
	private boolean stepAktiv(Event event) {
		aktuellerZustand.exit();
		System.out.println(this.toString() + ": " + aktuellerZustand + " " + event);
		Zustand naechsterZustand = getZustand(aktuellerZustand.handle(event));
		naechsterZustand.entry();

		aktuellerZustand = naechsterZustand;

		return aktuellerZustand != endzustand;
	}

	/**
	 * Verarbeitet einen einzelnen Passiven Zustand
	 *
	 * @return true falls sich der Automat im Endzustand befindet
	 */
	private boolean stepPassiv() {
		PassiverZustand zustand = (PassiverZustand) aktuellerZustand;

		zustand.exit();
		System.out.println(this.toString() + ": " + zustand);
		Zustand naechsterZustand = getZustand(zustand.handle());
		naechsterZustand.entry();

		aktuellerZustand = naechsterZustand;

		return aktuellerZustand != endzustand;
	}

	/**
	 * Verarbeitet alle anstehenden passiven Zustnde
	 *
	 * @return true falls sich der Automat im Endzustand befindet
	 */
	private boolean verarbeitePassiveZustaende() {
		while (aktuellerZustand instanceof PassiverZustand) {
			boolean res = stepPassiv();

			if (!res)
				return false;
		}

		return aktuellerZustand != endzustand;
	}

	/**
	 *
	 * Prüft den Automaten auf Fehler
	 */
	private void pruefeAutomat() {
		if (zustaende.size() <= 1)
			throw new RuntimeException("Keine Zustände registriert");

		if (start == null)
			throw new RuntimeException("Kein Startzustand gesetzt");

		if (eventQuelle == null)
			throw new RuntimeException("Keine EventQuelle definiertt");
	}

	/**
	 * Such die Instanz des Zustandes der der übergebener Klasse angehört und an
	 * diesen Automaten gebunden ist.
	 *
	 * @param klasse
	 *            Klasse des gesuchten Zustandes
	 * @return Instanz eines Zustandes
	 */
	private Zustand getZustand(Class<? extends Zustand> klasse) {
		Zustand zustand = zustaende.get(klasse);

		if (zustand == null)
			throw new RuntimeException("Nichtregistierer Zustand " + klasse);

		return zustand;
	}

	/**
	 * Prüft ob sich der Automat in dem übergebenen Zustand befindet. Diese
	 * Methode sollte nur für Tests verwendet werden.
	 *
	 * @param zustand zu prüfender Zustand
	 * @return true falls sich der Automat zurzeit in diesem Zustand befindet
	 */
	public boolean isZustand(Class<? extends Zustand> zustand) {
		return aktuellerZustand.getClass() == zustand;
	}

	public String toString() {
		return "Automat: ";
	}
}
