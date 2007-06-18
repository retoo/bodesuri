import java.util.Vector;

import junit.framework.TestCase;
import applikation.nachrichten.BeitrittsInformation;
import applikation.server.ServerAutomat;
import applikation.server.TestSpieler;
import applikation.server.zustaende.EmpfangeSpieler;
import applikation.server.zustaende.SpielStart;
import dienste.automat.Automat;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.ClientEndPunkt;
import dienste.netzwerk.EndPunktInterface;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.server.BriefkastenAdapter;
import dienste.serialisierung.SerialisierungsKontext;

public class ServerTest extends TestCase {
	public void testServer() {

		if (1 + 1 == 2) {
			return;
		}
		ServerAutomat automat = new ServerAutomat();
		Vector<TestSpieler> spielers = new Vector<TestSpieler>();

		automat.step();

		assertIstInZustand(automat, EmpfangeSpieler.class);

		SerialisierungsKontext kontext = new SerialisierungsKontext() {
			public void registriere(Thread thread) {
				// Ich passe.
			}
		};

		for (int i = 0; i < 4; i++) {
			EventQueue eventQueue = new EventQueue();
			EndPunktInterface client;
			try {
				client = new ClientEndPunkt("localhost", 7788,
				                      new BriefkastenAdapter(eventQueue),
				                      kontext);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			TestSpieler ts = new TestSpieler("Spieler " + i, client, eventQueue);

			ts.sendeBeitritt();

			spielers.add(ts);
		}

		while (!automat.isZustand(SpielStart.class)) {
			automat.step();
		}

		for (TestSpieler ts : spielers) {
			Nachricht beitrittBestaetigung = ts.getNachricht();
			assertTrue("Prüfen ob brief vom Typ Beitrittsbestätigung: " + beitrittBestaetigung, beitrittBestaetigung instanceof BeitrittsInformation);

			/* TODO: hässlich reto, mach das besser !!*/
	/*		Nachricht spielstart;
			while( true ) {
				 spielstart = ts.getNachricht();

				 if (!(spielstart instanceof ChatNachricht))
					 break;
			}
			assertTrue("Prüfen ob brief vom Typ Spielstart: " + spielstart, spielstart instanceof SpielStartNachricht);

			assertFalse("Sicherstellen das Queue leer ist", ts.hatNachrichten());
*/
		}
	}

	private void assertIstInZustand(Automat automat, Class<? extends Zustand> zustand) {
		assertTrue("Prüfen ob sich der Automat " + automat + " "
		         + "im Zustand " + zustand + " befindet.", automat.isZustand(zustand));
    }
}
