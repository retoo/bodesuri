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

		List<Spieler> spieler = spiel.getSpieler();
		spieler.get(0).setPartner(spieler.get(2));
		spieler.get(2).setPartner(spieler.get(0));
		spieler.get(1).setPartner(spieler.get(3));
		spieler.get(3).setPartner(spieler.get(1));

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
