package pd.regelsystem;

import java.util.List;
import java.util.Vector;

import pd.ProblemDomainTestCase;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.spiel.brett.Feld;
import pd.spiel.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

/**
 * Ist Basis für alle TestCases des Regelsystems. Bietet zusätzlich zu
 * den Initialisierungen von <Code>ProblemDomainTestCase</code> noch
 * spezielle Instanzvariablen und Methoden für die Validierung.
 */
public abstract class RegelTestCase extends ProblemDomainTestCase {
	protected Spieler spieler;
	protected Feld start;
	protected Feld ziel;
	protected Regel regel;
	
	protected void setUp() {
		super.setUp();
		spieler = spieler(0);
	}
	
	/**
	 * Validiert die standardmässig definierte Regel oder wirft
	 * bei einem Fehlschlag eine Exception.
	 * @throws RegelVerstoss
	 */
	protected void sollteValidieren() throws RegelVerstoss {
		sollteValidieren(regel);
	}
	
	/**
	 * Validiert die angegebene Regel oder wirft bei einem Fehlschlag
	 * eine Exception.
	 * 
	 * @param regel
	 * 			zu validierende Regel.
	 * @throws RegelVerstoss
	 */
	protected void sollteValidieren(Regel regel) throws RegelVerstoss {
		if (regel == null) {
			throw new RuntimeException("Keine Regel zum Validieren angegeben.");
		}
		ZugEingabe ze = new ZugEingabe(spieler, null, getBewegungen());
		Zug zug = regel.validiere(ze);
		zug.ausfuehren();
    }
	
	/**
	 * Prüft, ob Standardregel einen Verstoss verursacht.
	 */
	protected void sollteVerstossGeben() {
		sollteVerstossGeben(regel);
	}
	
	/**
	 * Prüft, ob eine angegebene Regel einen Verstoss verursacht.
	 * 
	 * @param regel
	 * 			Regel, die den Verstoss auslösen sollte.
	 */
	protected void sollteVerstossGeben(Regel regel) {
		ZugEingabe ze = new ZugEingabe(spieler, null, getBewegungen());
        try {
	        regel.validiere(ze);
	        fail("Sollte RegelVerstoss geben.");
        } catch (RegelVerstoss rv) {
        	assertNotNull(rv);
        }
	}
	
	/**
	 * Erstellt eine Liste mit eienr einzelnen Bewegung.
	 * 
	 * @return Liste mit einer Bewegungen.
	 */
	protected List<Bewegung> getBewegungen() {
		Vector<Bewegung> bewegungen = new Vector<Bewegung>();
		bewegungen.add(new Bewegung(start, ziel));
		return bewegungen;
	}
}
