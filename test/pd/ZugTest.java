package pd;
import pd.karten.Ass;
import pd.karten.Karte;
import pd.karten.KartenFarbe;
import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;

public class ZugTest extends ProblemDomainTestCase {
	public void testZugEingabe() throws RegelVerstoss {
		Bewegung bewegung = new Bewegung(lager(0), bank(0));
		Ass ass = new Ass(KartenFarbe.Herz, 0);
		ZugEingabe ze = new ZugEingabe(spieler(0), ass, bewegung);
		
		assertEquals(spieler(0), ze.getSpieler());
		assertEquals(ass, ze.getKarte());
		assertEquals(bewegung, ze.getBewegung());
		
		ze.validiere();
	}
	
	public void testKarteOhneRegel() {
		Bewegung bewegung = new Bewegung(lager(0), bank(0));
		Karte karte = new Karte("Ass", KartenFarbe.Herz, 0);
		ZugEingabe ze = new ZugEingabe(spieler(0), karte, bewegung);
        try {
	        ze.validiere();
	        fail("Sollte RegelVerstoss geben.");
        } catch (RegelVerstoss rv) {
        	assertNotNull(rv);
        }
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
