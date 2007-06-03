package pd;

import junit.framework.TestCase;
import pd.Spiel;
import pd.brett.BankFeld;
import pd.brett.Brett;
import pd.brett.HimmelFeld;
import pd.brett.LagerFeld;
import pd.karten.KartenGeber;
import pd.spieler.Spieler;

public abstract class ProblemDomainTestCase extends TestCase {
	protected Spiel spiel;
	protected Brett brett;
	protected KartenGeber kartenGeber;
	
	protected void setUp() {
		spiel = new Spiel();
		brett = spiel.getBrett();
		
		for (int i = 0; i < 4; ++i) {
			spiel.fuegeHinzu("Spieler" + i);
		}
		
		kartenGeber = spiel.getKartenGeber();
	}
	
	protected Spieler spieler(int nummer) {
		return spiel.getSpieler().get(nummer);
	}
	
	protected BankFeld bank(int spieler) {
		return brett.getBankFeldVon(spiel.getSpieler().get(spieler));
	}
	
	protected LagerFeld lager(int spieler) {
		return lager(spieler, 0);
	}
	
	protected LagerFeld lager(int spieler, int feld) {
		return brett.getLagerFelderVon(spiel.getSpieler().get(spieler)).get(feld);
	}
	
	protected HimmelFeld himmel(int spieler) {
		return himmel(spieler, 0);
	}
	
	protected HimmelFeld himmel(int spieler, int feld) {
		return brett.getHimmelFelderVon(spiel.getSpieler().get(spieler)).get(feld);
	}
}
