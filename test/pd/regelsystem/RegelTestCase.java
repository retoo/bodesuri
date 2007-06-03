package pd.regelsystem;

import pd.ProblemDomainTestCase;
import pd.brett.Feld;
import pd.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public abstract class RegelTestCase extends ProblemDomainTestCase {
	protected Spieler spieler;
	protected Feld start;
	protected Feld ziel;
	protected Regel regel;
	
	protected void setUp() {
		super.setUp();
		spieler = spieler(0);
	}
	
	protected void sollteValidieren() throws RegelVerstoss {
		sollteValidieren(regel);
	}
	
	protected void sollteValidieren(Regel regel) throws RegelVerstoss {
		Bewegung bewegung = new Bewegung(start, ziel);
		ZugEingabe ze = new ZugEingabe(spieler, null, bewegung);
		Zug zug = regel.validiere(ze);
		zug.ausfuehren();
    }
	
	protected void sollteVerstossGeben() {
		sollteVerstossGeben(regel);
	}
	
	protected void sollteVerstossGeben(Regel regel) {
		Bewegung bewegung = new Bewegung(start, ziel);
		ZugEingabe ze = new ZugEingabe(spieler, null, bewegung);
        try {
	        regel.validiere(ze);
	        fail("Sollte RegelVerstoss geben.");
        } catch (RegelVerstoss rv) {
        	assertNotNull(rv);
        }
	}
}
