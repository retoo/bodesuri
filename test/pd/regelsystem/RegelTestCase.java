package pd.regelsystem;

import java.util.List;
import java.util.Vector;

import pd.ProblemDomainTestCase;
import pd.brett.Feld;
import pd.regelsystem.verstoesse.RegelVerstoss;
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
		if (regel == null) {
			throw new RuntimeException("Keine Regel zum Validieren angegeben.");
		}
		ZugEingabe ze = new ZugEingabe(spieler, null, getBewegungen());
		Zug zug = regel.validiere(ze);
		zug.ausfuehren();
    }
	
	protected void sollteVerstossGeben() {
		sollteVerstossGeben(regel);
	}
	
	protected void sollteVerstossGeben(Regel regel) {
		ZugEingabe ze = new ZugEingabe(spieler, null, getBewegungen());
        try {
	        regel.validiere(ze);
	        fail("Sollte RegelVerstoss geben.");
        } catch (RegelVerstoss rv) {
        	assertNotNull(rv);
        }
	}
	
	protected List<Bewegung> getBewegungen() {
		Vector<Bewegung> bewegungen = new Vector<Bewegung>();
		bewegungen.add(new Bewegung(start, ziel));
		return bewegungen;
	}
}
