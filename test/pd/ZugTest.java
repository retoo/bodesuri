package pd;

import pd.karten.Ass;
import pd.karten.KartenFarbe;
import pd.regelsystem.ZugEingabe;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;

/**
 * Testet die Funktionalität des Zugsystems, speziell die der Zugerfassung.
 */
public class ZugTest extends ProblemDomainTestCase {
	/**
	 * Erstellt eine neue Zugeingabe mit einer Bewegung und einer Karte
	 * und versucht diese zu validieren.
	 * @throws RegelVerstoss
	 */
	public void testZugEingabe() throws RegelVerstoss {
		Bewegung bewegung = new Bewegung(lager(0), bank(0));
		Ass ass = new Ass(KartenFarbe.Herz);
		ZugEingabe ze = new ZugEingabe(spieler(0), ass, bewegung);
		
		assertEquals(spieler(0), ze.getSpieler());
		assertEquals(ass, ze.getKarte());
		assertEquals(bewegung, ze.getBewegung());
		
		ze.validiere();
	}

	/**
	 * Prüft, ob verbotene Aktionen auch tatsächlich verhindert werden.
	 */
	public void testAktionAufBesetztesFeld() {
		Aktion aktion = new Aktion(lager(0), lager(1));
		try {
			aktion.ausfuehren();
			fail("Sollte RuntimeException geben.");
		} catch (RuntimeException e) {
			assertNotNull(e);
		}
	}
}
