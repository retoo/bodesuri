package pd;

import java.util.List;

import junit.framework.TestCase;
import pd.Spiel;
import pd.brett.BankFeld;
import pd.brett.Brett;
import pd.brett.HimmelFeld;
import pd.brett.LagerFeld;
import pd.karten.KartenGeber;
import pd.spieler.Spieler;

/**
 * Ist Basis aller TestCases, welche die Problem-Domain abdecken. Initialisiert
 * weite Teile der PD und bietet einige Hilfsmethoden an.
 */
public abstract class ProblemDomainTestCase extends TestCase {
	protected Spiel spiel;
	protected Brett brett;
	protected KartenGeber kartenGeber;
	
	protected void setUp() {
		ProblemDomain problemDomain = new ProblemDomain();

		spiel = problemDomain.getSpiel();
		kartenGeber = problemDomain.getKartenGeber();

		brett = spiel.getBrett();

		for (int i = 0; i < 4; ++i) {
			spiel.fuegeHinzu("Spieler" + i);
		}

		List<Spieler> spieler = spiel.getSpieler();
		spieler.get(0).setPartner(spieler.get(2));
		spieler.get(2).setPartner(spieler.get(0));
		spieler.get(1).setPartner(spieler.get(3));
		spieler.get(3).setPartner(spieler.get(1));
	}
	
	/**
	 * Liefert den Spieler anhand der Nummer.
	 * 
	 * @param nummer
	 * 			Nummer des Spielers im Array/Vektor
	 * @return Spezifizierten Spieler
	 */
	protected Spieler spieler(int nummer) {
		return spiel.getSpieler().get(nummer);
	}
	
	/**
	 * Liefert das Bankfeld des Spielers.
	 * 
	 * @param spieler
	 * 			Spieler
	 * @return Das Bankfeld des Spielers.
	 */
	protected BankFeld bank(int spieler) {
		return brett.getBankFeldVon(spiel.getSpieler().get(spieler));
	}
	
	/**
	 * Liefert erstes Lagerfeld des Spielers.
	 * 
	 * @param spieler
	 * 			Spieler
	 * @return Das erste Lagerfeld des Spielers.
	 */
	protected LagerFeld lager(int spieler) {
		return lager(spieler, 0);
	}
	
	/**
	 * Liefert ein konkretes Lagerfeld des Spielers.
	 * 
	 * @param spieler
	 * 			Spieler
	 * @param feld
	 * 			Konkretes Feld.
	 * @return Konkretes Lagerfeld des Spielers.
	 */
	protected LagerFeld lager(int spieler, int feld) {
		return brett.getLagerFelderVon(spiel.getSpieler().get(spieler)).get(feld);
	}
	
	/**
	 * Liefert erstes Himmelfeld des Spielers.
	 * 
	 * @param spieler
	 * 			Spieler
	 * @return Das erste Lagerfeld des Spielers.
	 */
	protected HimmelFeld himmel(int spieler) {
		return himmel(spieler, 0);
	}
	
	/**
	 * Liefert ein konkretes Himmelfeld des Spielers.
	 * 
	 * @param spieler
	 * 			Spieler
	 * @param feld
	 * 			Konkretes Feld.
	 * @return Konkretes Himmelfeld des Spielers.
	 */
	protected HimmelFeld himmel(int spieler, int feld) {
		return brett.getHimmelFelderVon(spiel.getSpieler().get(spieler)).get(feld);
	}
}
