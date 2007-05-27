import pd.karten.Ass;
import pd.karten.Karte;
import pd.karten.KartenFarbe;
import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;

public class ZugTest extends ProblemDomainTestCase {
	public void testZugEingabe() throws RegelVerstoss {
		Bewegung bewegung = new Bewegung(lagerFeld1, bankFeld1);
		Ass ass = new Ass(KartenFarbe.Herz, 0);
		ZugEingabe ze = new ZugEingabe(spieler1, ass, bewegung);
		
		assertEquals(spieler1, ze.getSpieler());
		assertEquals(ass, ze.getKarte());
		assertEquals(bewegung, ze.getBewegung());
		
		ze.validiere();
	}
	
	public void testKarteOhneRegel() {
		Bewegung bewegung = new Bewegung(lagerFeld1, bankFeld1);
		Karte karte = new Karte("Ass", KartenFarbe.Herz, 0);
		ZugEingabe ze = new ZugEingabe(spieler1, karte, bewegung);
        try {
	        ze.validiere();
	        fail("Sollte RegelVerstoss geben.");
        } catch (RegelVerstoss rv) {
        	assertNotNull(rv);
        }
	}
	
	public void testAktionAufBesetztesFeld() {
		Aktion aktion = new Aktion(lagerFeld1, lagerFeld2);
		try {
			aktion.ausfuehren();
			fail("Sollte RuntimeException geben.");
		} catch (RuntimeException e) {
			assertNotNull(e);
		}
	}
}
