import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dienste.automat.Automat;
import dienste.automat.EndZustand;
import dienste.automat.EventQueue;
import dienste.automat.KeinUebergangException;
import dienste.automat.TestAutomat;
import dienste.automat.UnbekannterEventException;
import dienste.automat.events.TestEventA;
import dienste.automat.events.TestEventB;
import dienste.automat.events.TestEventC;
import dienste.automat.events.TestExitEvent;
import dienste.automat.testzustaende.DummyZustand;
import dienste.automat.testzustaende.PassiverTestZustandAlpha;
import dienste.automat.testzustaende.PassiverTestZustandBeta;
import dienste.automat.testzustaende.AktiverTestZustandAlpha;
import dienste.automat.testzustaende.AktiverTestZustandBeta;

public class AutomatenTest extends TestCase {
	private Automat automat;
	private EventQueue input;
	private EventQueue output;

	@Before
	public void setUp() throws Exception {
		input = new EventQueue();
		output = new EventQueue();

		automat = new TestAutomat(input, output);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testStop() {
		input.enqueue(new TestExitEvent());

		boolean res = automat.step();
		assertFalse("Endender Schritt", res);

		assertEquals(automat.getZustand(EndZustand.class),
		             automat.getAktuellerZustand());
	}

	@Test
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

	@Test
	public void testKeineSource() {
		Automat automat = new Automat();

		automat.registriere(new DummyZustand());
		automat.setStart(DummyZustand.class);

		boolean fehlerAufgetreten = false;

		try {
			automat.run();
		} catch (Exception e) {
			fehlerAufgetreten = true;
			assertEquals("Keine EventQuelle definiertt", e.getMessage());
		}

		assertTrue("Fehler nicht aufgetreten", fehlerAufgetreten);
	}

	@Test
	public void testUnbkeannterStart() {
		boolean fehlerAufgetreten = false;

		try {
			automat.setStart(DummyZustand.class);
		} catch (Exception e) {
			fehlerAufgetreten = true;
			assertEquals("Nichtregistierer Zustand class DummyZustand",
			             e.getMessage());
		}

		assertTrue("Fehler nicht aufgetreten", fehlerAufgetreten);
	}

	@Test
	public void testStep() {
		input.enqueue(new TestEventA());

		assertTrue(automat.step());

		assertEquals(automat.getZustand(AktiverTestZustandAlpha.class),
		             automat.getAktuellerZustand());

		input.enqueue(new TestEventB());

		assertTrue(automat.step());

		assertEquals(automat.getZustand(AktiverTestZustandBeta.class),
		             automat.getAktuellerZustand());

		input.enqueue(new TestEventB());

		assertTrue(automat.step());

		assertEquals(automat.getZustand(AktiverTestZustandBeta.class),
		             automat.getAktuellerZustand());
	}

	@Test
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

	@Test
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

	@Test
	public void testPassiverZustand() {
		automat.registriere(new PassiverTestZustandAlpha());
		automat.registriere(new PassiverTestZustandBeta());
		
		automat.setStart(PassiverTestZustandAlpha.class);
		
		automat.run();

		assertEquals(automat.getZustand(EndZustand.class),
		             automat.getAktuellerZustand());
	}

	@Test
	public void testRun() {
		input.enqueue(new TestEventA());
		input.enqueue(new TestEventB());
		input.enqueue(new TestEventB());
		input.enqueue(new TestEventA());
		input.enqueue(new TestExitEvent());

		automat.run();

		assertEquals(automat.getZustand(EndZustand.class),
		             automat.getAktuellerZustand());
	}
}
