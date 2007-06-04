package pd.regelsystem;

public class RueckwaertsRegelTest extends RegelTestCase {
	public void testRueckwaertsAusHimmel() {
		start = himmel(0).getNaechstes();
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new RueckwaertsRegel(2));
	}
	
	public void testRueckwaertsInHimmel() {
		start = bank(0).getNaechstes();
		ziel  = himmel(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new RueckwaertsRegel(2));
	}
	
	public void testRueckwaertsAusLager() {
		start = lager(0);
		ziel  = bank(0);
		sollteVerstossGeben(new RueckwaertsRegel(1));
	}
	
	public void testRueckwaertsInLager() {
		start = bank(0);
		ziel  = lager(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new RueckwaertsRegel(1));
	}
}
