package pd.regelsystem;

public class StartRegelTest extends RegelTestCase {
	public void testStart() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(ziel.istGeschuetzt());
		
		start = bankFeld1;
		ziel  = start.getNtesFeld(5);
		sollteValidieren(new VorwaertsRegel(5));
		
		assertFalse(start.istGeschuetzt());
		assertFalse(ziel.istGeschuetzt());
	}
	
	public void testStartNichtVonLagerFeldAus() {
		start = bankFeld1;
		ziel  = bankFeld1.getNaechstes();
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartKeineFigurAufLagerFeld() {
		start = lagerFeld1;
		ziel  = bankFeld1;
		lagerFeld1.versetzeFigurAuf(ziel);
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartNichtAufBankFeld() {
		start = lagerFeld1;
		ziel  = bankFeld1.getNaechstes();
		sollteVerstossGeben(new StartRegel());
		
		ziel  = bankFeld2;
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartAndererSpieler() {
		start = lagerFeld2;
		ziel  = bankFeld2;
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartGeschuetzt() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(ziel.istGeschuetzt());
		
		start = brett.getLagerFelderVon(spieler1).get(3);
		ziel  = bankFeld1;
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartHeimSchicken() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		lagerFeld2.versetzeFigurAuf(ziel);
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(ziel.istGeschuetzt());
		assertTrue(lagerFeld2.istBesetztVon(spieler2));
	}
}
