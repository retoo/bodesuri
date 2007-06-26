package pd;

import pd.karten.Ass;
import pd.karten.KartenFarbe;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;

public class ZugTest extends ProblemDomainTestCase {
	public void testZugEingabe() throws RegelVerstoss {
		Bewegung bewegung = new Bewegung(lager(0), bank(0));
		Ass ass = new Ass(KartenFarbe.Herz);
		ZugEingabe ze = new ZugEingabe(spieler(0), ass, bewegung);
		
		assertEquals(spieler(0), ze.getSpieler());
		assertEquals(ass, ze.getKarte());
		assertEquals(bewegung, ze.getBewegung());
		
		ze.validiere();
	}

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
