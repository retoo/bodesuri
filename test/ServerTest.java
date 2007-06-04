import java.util.Vector;

import junit.framework.TestCase;
import applikation.nachrichten.BeitrittsBestaetigung;
import applikation.nachrichten.ChatNachricht;
import applikation.nachrichten.SpielStartNachricht;
import applikation.server.BodesuriServer;
import applikation.server.TestSpieler;
import applikation.server.zustaende.EmpfangeSpieler;
import applikation.server.zustaende.StarteSpiel;
import dienste.automat.Automat;
import dienste.automat.EventQueue;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.BriefkastenAdapter;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;

public class ServerTest extends TestCase {
	public void testServer() {
		BodesuriServer automat = new BodesuriServer();
		Vector<TestSpieler> spielers = new Vector<TestSpieler>();

		automat.step();

		assertIstInZustand(automat, EmpfangeSpieler.class);

		for (int i = 0; i < 4; i++) {
			EventQueue eventQueue = new EventQueue();
			EndPunkt client;
			try {
				client = new EndPunkt("localhost", 7788,
				                      new BriefkastenAdapter(eventQueue));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			TestSpieler ts = new TestSpieler("Spieler " + i, client, eventQueue);

			ts.sendeBeitritt();

			spielers.add(ts);
		}

		while (!automat.isZustand(StarteSpiel.class)) {
			automat.step();
		}

		for (TestSpieler ts : spielers) {
			Nachricht beitrittBestaetigung = ts.getNachricht();
			assertTrue("Prüfen ob brief vom Typ Beitrittsbestätigung: " + beitrittBestaetigung, beitrittBestaetigung instanceof BeitrittsBestaetigung);

			/* FIXME: hässlich reto, mach das besser !!*/
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

	private void assertIstInZustand(Automat automat, Class<? extends Zustand> name) {
		assertEquals(automat.getZustand(name), automat.getAktuellerZustand());

    }
}
