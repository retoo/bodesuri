package pd.regelsystem;

import pd.karten.Ass;
import pd.karten.Karte;
import pd.karten.KartenFarbe;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.regelsystem.verstoesse.Verstoesse;
import pd.regelsystem.verstoesse.WegLaengeVerstoss;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;

/**
 * Testet die Funktionalität eines Regelverstosses.
 */
public class RegelVerstossTest extends RegelTestCase {
	private Karte ass;
	protected void setUp() {
		super.setUp();
		ass = new Ass(KartenFarbe.Herz);
	}

	/**
	 * Testet das Verhalten des RegelVerstoss beim Ass, wo je nach Spezifität
	 * der eine oder der andere WegLaengeVerstoss geworfen werden sollte.
	 */
	public void testRegelVerstossBeiAss() {
		start = bank(0);
		ziel  = bank(0).getNtesFeld(3);
		lager(0).versetzeFigurAuf(start);
		try {
			Bewegung b = new Bewegung(start, ziel);
			ZugEingabe ze = new ZugEingabe(spieler(0), ass, b);
			ze.validiere();
			fail("Sollte RegelVerstoss geben.");
		} catch (RegelVerstoss rv) {
			assertTrue(rv instanceof WegLaengeVerstoss);
			WegLaengeVerstoss wlv = (WegLaengeVerstoss) rv;
			assertEquals(3, wlv.getIstLaenge());
			assertEquals(1, wlv.getSollLaenge());
		}

		ziel  = bank(0).getNtesFeld(8);
		try {
			Bewegung b = new Bewegung(start, ziel);
			ZugEingabe ze = new ZugEingabe(spieler(0), ass, b);
			ze.validiere();
			fail("Sollte RegelVerstoss geben.");
		} catch (RegelVerstoss rv) {
			assertTrue(rv instanceof WegLaengeVerstoss);
			WegLaengeVerstoss wlv = (WegLaengeVerstoss) rv;
			assertEquals(8, wlv.getIstLaenge());
			assertEquals(11, wlv.getSollLaenge());
		}
	}

	public void testRegelVerstossBeiAssMitGeschuetzt() {
		start = bank(0).getVorheriges();
		ziel  = bank(0);
		new Aktion(lager(0, 0), bank(0)).ausfuehren();
		lager(0, 1).versetzeFigurAuf(start);

		try {
			Bewegung b = new Bewegung(start, ziel);
			ZugEingabe ze = new ZugEingabe(spieler(0), ass, b);
			ze.validiere();
			fail("Sollte RegelVerstoss geben.");
		} catch (RegelVerstoss rv) {
			assertTrue(rv instanceof Verstoesse.AufOderUeberGeschuetzteFahren);
		}
	}
}
