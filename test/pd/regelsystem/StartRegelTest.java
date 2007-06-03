package pd.regelsystem;

public class StartRegelTest extends RegelTestCase {
	public void testStart() throws RegelVerstoss {
		start = lager(0);
		ziel  = bank(0);
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
		assertTrue(ziel.istGeschuetzt());
		
		start = bank(0);
		ziel  = start.getNtesFeld(5);
		sollteValidieren(new VorwaertsRegel(5));
		
		assertFalse(start.istGeschuetzt());
		assertFalse(ziel.istGeschuetzt());
	}
	
	public void testStartNichtVonLagerFeldAus() {
		start = bank(0);
		ziel  = bank(0).getNaechstes();
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartKeineFigurAufLagerFeld() {
		start = lager(0);
		ziel  = bank(0);
		start.versetzeFigurAuf(ziel);
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartNichtAufBankFeld() {
		start = lager(0);
		ziel  = bank(0).getNaechstes();
		sollteVerstossGeben(new StartRegel());
		
		ziel  = bank(1);
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartAndererSpieler() {
		start = lager(1);
		ziel  = bank(1);
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartGeschuetzt() throws RegelVerstoss {
		start = lager(0);
		ziel  = bank(0);
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
		assertTrue(ziel.istGeschuetzt());
		
		start = lager(0, 1);
		ziel  = bank(0);
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartHeimSchicken() throws RegelVerstoss {
		start = lager(0);
		ziel  = bank(0);
		lager(1).versetzeFigurAuf(ziel);
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
		assertTrue(ziel.istGeschuetzt());
		assertTrue(lager(1).istBesetztVon(spieler(1)));
	}
}
