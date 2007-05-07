import junit.framework.TestCase;
import pd.spielerverwaltung.Spieler;
import pd.zugsystem.BankFeld;
import pd.zugsystem.Feld;
import pd.zugsystem.Spiel;
import pd.deck.KartenGeber;

public abstract class ProblemDomainTestCase extends TestCase {
	protected Spiel spiel;
	protected Spieler spieler;
	protected BankFeld bankFeld;
	protected Feld zielFeld;
	protected KartenGeber kartenGeber;
	
	protected void setUp() throws Exception {
		spiel = new Spiel();
		
		for (int i = 0; i < 4; ++i) {
			spiel.fuegeHinzu("Spieler" + i);
		}
		spieler = spiel.getSpieler().get(0);
		
		bankFeld = spiel.getBrett().getBankFeldVon(spieler);
		bankFeld.setFigur(spieler.getFiguren().get(0));
		
		zielFeld = bankFeld;
		for (int i = 0; i < 4; ++i) {
			zielFeld = zielFeld.getNaechstes();
		}
		
		kartenGeber = new KartenGeber();
	}

}
