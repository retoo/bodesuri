package dienste.automat;

import java.util.IdentityHashMap;
import java.util.Map;

import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.PassiverZustand;
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
	private boolean isInit;

	/**
	 * Erstellt einen neuen Automaten
	 */
	public Automat() {
		zustaende = new IdentityHashMap<Class<? extends Zustand>, Zustand>();

		endzustand = new EndZustand();
		registriere(endzustand);

		isInit = false;
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

	public void init() {
		pruefeAutomat(false);
		start.onEntry();
		verarbeitePassiveZustaende();

		isInit = true;
	}

	/**
	 * Startet den Zustandsautomaten und führt ihn so lange bis ein
	 * {@link EndZustand} eintritt.
	 */
	public void run() {
		pruefeAutomat(true);
		isInit = true;

		boolean isNichtFertig = (aktuellerZustand != endzustand);
		while (isNichtFertig) {
			isNichtFertig = step();
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
	 * @return false falls sich der Automat im Endzustand befindet
	 */
	public boolean step() {
		if (!isInit)
			throw new RuntimeException("Automat ist noch nicht initialisiert, ruf init() auf vor dem ersten step()");

		if (aktuellerZustand instanceof PassiverZustand) {
			return stepPassiv();
		} else if (aktuellerZustand instanceof EndZustand) {
			return false;
		} else {
			Event event = eventQuelle.getEvent();
			if (!event.istLeise())
				System.out.println(this + ": Event - " + event);
			return stepAktiv(event);
		}
	}

	/**
	 * Ermöglicht das verarbeiten von Zuständen mit extern eingelesenen Events.
	 * Kann z.B. für Unterautomaten verwendet werden
	 *
	 * @param event
	 *            Zu verarbeitender Event
	 * @return false falls sich der Automat im Endzustand befindet
	 */
	public boolean step(Event event) {
		if (!isInit)
			throw new RuntimeException("Automat ist noch nicht initialisiert, ruf init() auf vor dem ersten step()");

		if (!event.istLeise())
			System.out.println(this + ": Event - " + event);

		if (aktuellerZustand instanceof PassiverZustand) {
			throw new RuntimeException(
			                           "step(event) in einem passiven Zustande aufgerufen."
			                           + " Zustand: "
			                           + aktuellerZustand);
		}

		boolean isIstNichtFertig = stepAktiv(event);

		/* Wir brechen hier ab falls wir uns im Endzustand befinden */
		if (!isIstNichtFertig)
			return false;

		return verarbeitePassiveZustaende();
	}

	/**
	 * Verarbeitet einen 'Aktiven' Zustand.
	 *
	 * @param event
	 *            Zu verarbeitende event
	 * @return false falls sich der Automat im Endzustand befindet
	 */
	private boolean stepAktiv(Event event) {
		Zustand naechsterZustand = getZustand(aktuellerZustand.handle(event));

		if (naechsterZustand != aktuellerZustand) {
			aktuellerZustand.onExit();
			naechsterZustand.onEntry();
		}

		aktuellerZustand = naechsterZustand;

		if (!event.istLeise())
			System.out.println(this.toString() + ": " + aktuellerZustand);

		return aktuellerZustand != endzustand;
	}

	/**
	 * Verarbeitet einen einzelnen Passiven Zustand
	 *
	 * @return false falls sich der Automat im Endzustand befindet
	 */
	private boolean stepPassiv() {
		PassiverZustand zustand = (PassiverZustand) aktuellerZustand;

		Zustand naechsterZustand = getZustand(zustand.handle());

		if (naechsterZustand != aktuellerZustand) {
			zustand.onExit();
			naechsterZustand.onEntry();
		}

		aktuellerZustand = naechsterZustand;
		System.out.println(this.toString() + ": " + aktuellerZustand);

		return aktuellerZustand != endzustand;
	}

	/**
	 * Verarbeitet alle anstehenden passiven Zustnde
	 *
	 * @return false falls sich der Automat im Endzustand befindet
	 */
	private boolean verarbeitePassiveZustaende() {
		while (aktuellerZustand instanceof PassiverZustand) {
			stepPassiv();
		}

		return aktuellerZustand == endzustand;
	}

	/**
	 *
	 * Prüft den Automaten auf Fehler
	 */
	private void pruefeAutomat(boolean hatQuelle) {
		if (zustaende.size() <= 1)
			throw new RuntimeException("Keine Zustände registriert");

		if (start == null)
			throw new RuntimeException("Kein Startzustand gesetzt");

		if (hatQuelle && eventQuelle == null)
			throw new RuntimeException("Keine EventQuelle definiert");
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
