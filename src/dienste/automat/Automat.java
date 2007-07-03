package dienste.automat;

import java.util.IdentityHashMap;
import java.util.Map;

import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;

/**
 * Zustandsautomat für alle Lebenslagen. Ermöglicht das saubere Abarbeiten eines
 * Zustandsautomaten anhand eingehender Events.
 *
 * Die Erstellung eines Automaten besteht aus folgenden Schritten:
 * <ul>
 * <il>Situationsabhänige Zustandsklasse erstellen</li>
 * <il>Zustände erstellen die die obige Klassen als Basis-Klasse
 * verwenden</li>
 * <il>Automat erstellen und alle Zustände mit
 * {@link Automat#registriere(Zustand)} registrieren</li>
 * <il>Startzustand mit {@link Automat#setStart(Class)} definieren</li>
 * <il>EvenQuelle implementieren. Zum Beispiel kann die EventQueue als Baack-End
 * verwendet werden.</li>
 * <il>Automat mit run() starten</li>
 * </ul>
 *
 *
 */
public class Automat {
    private Zustand start;
    private EventQuelle eventQuelle;
    private Map<Class<? extends Zustand>, Zustand> zustaende;
    private Zustand aktuellerZustand;
    private final EndZustand endzustand;
    private boolean isInit;
	private boolean debug;

    /**
     * Erstellt einen neuen Automaten
     * @param debug flag ob debug-meldung ausgegeben werden sollen
     */
    public Automat(boolean debug) {
    	this.debug = debug;
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

    /**
	 * Initiiert den Automaten. Diese Methode muss unbedingt vor dem Aufruf von
	 * run() oder dem ersten step(Event) durchgeführt werden.
	 */
    public void init() {
        pruefeAutomat(false);
        start.onEntry();
        verarbeitePassiveZustaende();

        isInit = true;
    }

    /**
	 * Startet den Zustandsautomaten und führt ihn so lange aus bis ein
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
     * Verarbeitet einen einzigen Zustand. Bei aktiven Zuständen bedeutet dies,
     * dass auf neue Events gewartetw wird. Passive hingegen werden direkt
     * ausgeführt.
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
                debug(this + ": Event - " + event);
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
            debug(this + ": Event - " + event);

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
     * Verarbeitet einen 'aktiven' Zustand.
     *
     * @param event
     *            Zu verarbeitende event
     * @return false falls sich der Automat im Endzustand befindet
     */
    private boolean stepAktiv(Event event) {
    	try {
	        Zustand naechsterZustand = getZustand(aktuellerZustand.handle(event));

	        boolean zustandAenderte = naechsterZustand != aktuellerZustand;

	        if (zustandAenderte) {
	            aktuellerZustand.onExit();
	        }

	        aktuellerZustand = naechsterZustand;

	        if (zustandAenderte) {
	        	aktuellerZustand.onEntry();
	        }

	        if (!event.istLeise())
	            debug(this.toString() + ": " + aktuellerZustand);

	        return aktuellerZustand != endzustand;
    	} catch (RuntimeException e) {
    		/* Fehlerbehandlung, falls noch möglich. AktuellerZustand sollte
			 * immer auf den 'verantworltichen' Zustand zeigen, egal wo genau
			 * der Fehler Auftritt
			 */
    		aktuellerZustand.handleException(e);

    		/* Nach einem Fehler beendet sich der Automat zwangsläufig.
			 * Idealerweise würde der Automat noch die Chance kriegen irgendwas
			 * zu machen. Für unsere Zwecke wäre das aber zu kompliziert.
			 */
    		return false;
    	}
    }

    /**
     * Verarbeitet einen einzelnen passiven Zustand
     *
     * @return false falls sich der Automat im Endzustand befindet
     */
    private boolean stepPassiv() {
    	try {
	        PassiverZustand zustand = (PassiverZustand) aktuellerZustand;

	        Zustand naechsterZustand = getZustand(zustand.handle());

	        boolean zustandAenderte = naechsterZustand != aktuellerZustand;

	        if (zustandAenderte) {
	        	aktuellerZustand.onExit();
	        }

	        aktuellerZustand = naechsterZustand;

	        if (zustandAenderte) {
	            aktuellerZustand.onEntry();
	        }

	        debug(this.toString() + ": " + aktuellerZustand);

	        return aktuellerZustand != endzustand;
    	} catch (RuntimeException e) {
    		/* Fehlerbehandlung, falls noch möglich. AktuellerZustand sollte
			 * immer auf den 'verantworltichen' Zustand zeigen, egal wo genau
			 * der Fehler Auftritt
			 */
    		aktuellerZustand.handleException(e);

    		/* Nach einem Fehler beendet sich der Automat zwangsläufig.
			 * Idealerweise würde der Automat noch die Chance kriegen irgendwas
			 * zu machen. Für unsere Zwecke wäre das aber zu kompliziert.
			 */
    		return false;
    	}
    }

    /**
     * Verarbeitet alle anstehenden passiven Zustände
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
            throw new RuntimeException("Nichtregistrierter Zustand " + klasse);

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

    private void debug(String meldung) {
    	if (debug)
    		System.out.println(meldung);
	}

    public String toString() {
        return "Automat: ";
    }

}
