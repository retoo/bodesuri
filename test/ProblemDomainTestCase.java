import junit.framework.TestCase;
import pd.Spiel;
import pd.brett.Brett;
import pd.karten.KartenGeber;
import pd.spieler.Figur;
import pd.spieler.Spieler;

public abstract class ProblemDomainTestCase extends TestCase {
	protected Spiel spiel;
	protected Brett brett;
	protected Spieler spieler1;
	protected Figur figur1;
	protected KartenGeber kartenGeber;
	
	protected void setUp() throws Exception {
		spiel = new Spiel();
		
		for (int i = 0; i < 4; ++i) {
			spiel.fuegeHinzu("Spieler" + i);
		}
		
		spieler1 = spiel.getSpieler().get(0);
		figur1 = spieler1.getFiguren().get(0);
		
		brett = spiel.getBrett();
		
		kartenGeber = spiel.getKartenGeber();
	}

}
