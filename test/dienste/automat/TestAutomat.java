package dienste.automat;
import dienste.automat.testzustaende.TestZustandAlpha;
import dienste.automat.testzustaende.TestZustandBeta;
import dienste.eventqueue.EventQueue;

/**
 * Dieser TestAutomat wird die Tests des Automaten gebraucht. Er
 * registriert bereits einige Zustände und setzt die Eventquellen.
 */
public class TestAutomat extends Automat {
	public EventQueue output;

	/**
	 * Erstellt einen neuen Testautomaten, registriert bei ihm
	 * einige Zustände, setzt den Startzustand die Eventquelle.
	 *
	 * @param input Eingabeevents
	 * @param output Ausgabeevents
	 */
	public TestAutomat(EventQueue input, EventQueue output) {
		super(false);

	    registriere(new TestZustandAlpha());
	    registriere(new TestZustandBeta());

	    setStart(TestZustandAlpha.class);

	    setEventQuelle(new EventQuelleAdapter(input));

	    this.output = output;
    }

	public String toString() {
	    return "Test-Automat";
	}
}