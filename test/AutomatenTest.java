import junit.framework.TestCase;

import dienste.automat.Automat;
import dienste.automat.TestAutomat;
import dienste.automat.events.TestEventA;
import dienste.automat.events.TestEventB;
import dienste.automat.events.TestEventC;
import dienste.automat.events.TestExitEvent;
import dienste.automat.testzustaende.AktiverTestZustandAlpha;
import dienste.automat.testzustaende.AktiverTestZustandBeta;
import dienste.automat.testzustaende.DummyZustand;
import dienste.automat.testzustaende.PassiverTestZustandAlpha;
import dienste.automat.testzustaende.PassiverTestZustandBeta;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.KeinUebergangException;
import dienste.automat.zustaende.UnbekannterEventException;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.EventQueue;

/**
 * Ist die Basis aller Tests für den Automaten. Setzt einen <Code>TestCase</Code>
 * für den Automaten auf und bietet allgemeine Testmethoden die den Erfolg des
 * Tests prüfen.
 */
public class AutomatenTest extends TestCase {
	private Automat automat;
	private EventQueue input;
	private EventQueue output;

	public void setUp() throws Exception {
		input = new EventQueue();
		output = new EventQueue();

		automat = new TestAutomat(input, output);
		automat.init();
	}

	/**
	 * Prüft, ob der Automat anhält, wenn er einen EndZustand erreicht hat.
	 */
	public void testStop() {
		input.enqueue(new TestExitEvent());

		automat.step();
		boolean res = automat.step();
		assertFalse("Letzter Schritt Schritt", res);

		assertIstInZustand(automat, EndZustand.class);
	}

	/**
	 * Testet den Automaten, wenn bei ihm keine Zustände registriert wurden.
	 */
	public void testKeineZustaende() {
		Automat autmat = new Automat();

		boolean fehlerAufgetreten = false;

		try {
			autmat.run();
		} catch (Exception e) {
			fehlerAufgetreten = true;
			assertEquals("Keine Zustände registriert", e.getMessage());
		}

		assertTrue("Fehler nicht aufgetreten", fehlerAufgetreten);
	}

	/**
	 * Testet den Automaten, wenn ihm kein StartZustand angegeben wurde.
	 */
	public void testKeinStart() {
		Automat automat = new Automat();
		automat.registriere(new DummyZustand());

		boolean fehlerAufgetreten = false;

		try {
			automat.run();
		} catch (Exception e) {
			fehlerAufgetreten = true;
			assertEquals("Kein Startzustand gesetzt", e.getMessage());
		}

		assertTrue("Fehler nicht aufgetreten", fehlerAufgetreten);
	}

	/**
	 * Prüft das Verhalten des Automaten, wenn ihm keine EventQuelle
	 * angegeben wurde.
	 */
	public void testKeineEventQuelle() {
		Automat automat = new Automat();
		automat.registriere(new DummyZustand());
		automat.setStart(DummyZustand.class);

		boolean fehlerAufgetreten = false;

		try {
			automat.run();
		} catch (Exception e) {
			fehlerAufgetreten = true;
			assertEquals("Keine EventQuelle definiert", e.getMessage());
		}

		assertTrue("Fehler nicht aufgetreten", fehlerAufgetreten);
	}

	/**
	 * Testet, ob unbekannte Zustände als StartZustand misbraucht werden
	 * können.
	 */
	public void testUnbkannterStart() {
		boolean fehlerAufgetreten = false;

		try {
			automat.setStart(DummyZustand.class);
		} catch (Exception e) {
			fehlerAufgetreten = true;
			assertEquals("Nichtregistrierter Zustand class dienste.automat.testzustaende.DummyZustand",
			             e.getMessage());
		}

		assertTrue("Fehler nicht aufgetreten", fehlerAufgetreten);
	}

	/**
	 * Erzeugt eine Reihe von Test-Events und geht diese im Automaten
	 * Schritt für Schritt durch (mittels <Code>step()</Code>).
	 */
	public void testStep() {
		input.enqueue(new TestEventA());
		assertTrue(automat.step());
		assertIstInZustand(automat, AktiverTestZustandAlpha.class);
		
		input.enqueue(new TestEventB());
		assertTrue(automat.step());
		assertIstInZustand(automat, AktiverTestZustandBeta.class);
		
		input.enqueue(new TestEventB());
		assertTrue(automat.step());
		assertIstInZustand(automat, AktiverTestZustandBeta.class);
	}

	/**
	 * Testet, ob unbekannte Events in einer <Code>UnbekannterEventException</Code>
	 * enden.
	 */
	public void testUnbekannterEvent() {
		input.enqueue(new TestEventC());

		boolean fehlerAufgetreten = false;

		try {
			automat.step();
		} catch (UnbekannterEventException e) {
			fehlerAufgetreten = true;
		}

		assertTrue(fehlerAufgetreten);
	}

	/**
	 * Testet, ob ungültige Übergänge korrekt in einer
	 * <Code>KeinUebergangException</Code> enden.
	 */
	public void testKeinUebergang() {
		input.enqueue(new TestEventB());
		input.enqueue(new TestEventC());

		boolean fehlerAufgetreten = false;

		automat.step();

		try {
			automat.step();
		} catch (KeinUebergangException e) {
			fehlerAufgetreten = true;
		}

		assertTrue(fehlerAufgetreten);
	}

	/**
	 * Testet den passiven Zustand des Automaten.
	 */
	public void testPassiverZustand() {
		automat.registriere(new PassiverTestZustandAlpha());
		automat.registriere(new PassiverTestZustandBeta());

		automat.setStart(PassiverTestZustandAlpha.class);

		automat.run();

		assertIstInZustand(automat, EndZustand.class);
	}

	/**
	 * Testet die <Code>run()</Code> Methode des Automaten, indem einige
	 * Test-Events erzeugt und in den Automaten eingespiesen werden.
	 */
	public void testRunMethodeDesAutomaten() {
		input.enqueue(new TestEventA());
		input.enqueue(new TestEventB());
		input.enqueue(new TestEventB());
		input.enqueue(new TestEventA());
		input.enqueue(new TestExitEvent());

		automat.run();

		assertIstInZustand(automat, EndZustand.class);
	}

	/**
	 * Prüft, ob der Automat sich im angegebenen Zustand befindet.
	 * 
	 * @param automat
	 * 			Zu testender Automat.
	 * @param zustand
	 * 			Zustand, in dem sich der Automat befinden sollte.
	 */
	private void assertIstInZustand(Automat automat, Class<? extends Zustand> zustand) {
		assertTrue("Prüfen ob sich der Automat " + automat + " "
		         + "im Zustand " + zustand + " befindet.", automat.isZustand(zustand));
    }
}
