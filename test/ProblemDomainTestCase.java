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
	protected Spieler spieler1;
	protected Spieler spieler2;
	protected BankFeld bankFeld1;
	protected BankFeld bankFeld2;
	protected LagerFeld lagerFeld1;
	protected LagerFeld lagerFeld2;
	protected HimmelFeld himmelFeld1;
	protected HimmelFeld himmelFeld2;
	protected KartenGeber kartenGeber;
	
	protected void setUp() {
		spiel = new Spiel();
		brett = spiel.getBrett();
		
		for (int i = 0; i < 4; ++i) {
			spiel.fuegeHinzu("Spieler" + i);
		}
		
		spieler1 = spiel.getSpieler().get(0);
		bankFeld1 = brett.getBankFeldVon(spieler1);
		lagerFeld1 = brett.getLagerFelderVon(spieler1).get(0);
		himmelFeld1 = brett.getHimmelFelderVon(spieler1).get(0);
		
		spieler2 = spiel.getSpieler().get(1);
		bankFeld2 = brett.getBankFeldVon(spieler2);
		lagerFeld2 = brett.getLagerFelderVon(spieler2).get(0);
		himmelFeld2 = brett.getHimmelFelderVon(spieler2).get(0);
		
		kartenGeber = spiel.getKartenGeber();
	}

}
