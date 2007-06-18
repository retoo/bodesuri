package pd.regelsystem;

public class RueckwaertsRegelTest extends RegelTestCase {
	public void testRueckwaerts() throws RegelVerstoss {
		start = bank(0).getNtesFeld(5);
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren(new RueckwaertsRegel(5));
	}
	
	public void testRueckwaertsVorwaerts() throws RegelVerstoss {
		start = bank(0);
		ziel  = bank(0).getNtesFeld(5);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new RueckwaertsRegel(5));
	}
	
	public void testRueckwaertsImHimmel() {
		start = himmel(0, 3);
		ziel  = himmel(0, 1);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new RueckwaertsRegel(2));
	}
	
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
