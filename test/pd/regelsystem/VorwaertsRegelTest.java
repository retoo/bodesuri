package pd.regelsystem;

public class VorwaertsRegelTest extends RegelTestCase {
	public void testVorwaertsHeimSchicken() throws RegelVerstoss {
		start = bank(0);
		ziel  = start.getNtesFeld(5);
		lager(0).versetzeFigurAuf(start);
		lager(1).versetzeFigurAuf(ziel);
		sollteValidieren(new VorwaertsRegel(5));
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
		assertTrue(lager(1).istBesetztVon(spieler(1)));
	}
	
	public void testVorwaertsAufGeschuetztes() throws RegelVerstoss {
		start = lager(0);
		ziel  = bank(0);
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
		assertTrue(ziel.istGeschuetzt());

		spieler = spieler(1);
		start = bank(0).getVorheriges().getVorheriges();
		ziel  = bank(0);
		lager(1).versetzeFigurAuf(start);
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsAufBankFeldBesetztVonBesitzer()
			throws RegelVerstoss {
		start = bank(0).getVorheriges().getVorheriges();
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(bank(0));
		lager(1).versetzeFigurAuf(start);
		spieler = spieler(1);
		sollteValidieren(new VorwaertsRegel(2));
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(1)));
		assertTrue(lager(0).istBesetztVon(spieler(0)));
	}
	
	public void testVorwaertsUeberGeschuetztes() throws RegelVerstoss {
		start = lager(0);
		ziel  = bank(0);
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
		assertTrue(ziel.istGeschuetzt());

		spieler = spieler(1);
		start = bank(0).getVorheriges();
		ziel  = bank(0).getNaechstes();
		lager(1).versetzeFigurAuf(start);
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsUeberBankFeldBesetzt() throws RegelVerstoss {
		start = bank(0).getVorheriges();
		ziel  = bank(0).getNaechstes();
		lager(0).versetzeFigurAuf(start);
		lager(1).versetzeFigurAuf(bank(0));
		sollteValidieren(new VorwaertsRegel(2));
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
		assertTrue(bank(0).istBesetztVon(spieler(1)));
	}
	
	public void testVorwaertsUeberBankFeldBesetztVonBesitzer() {
		start = bank(0).getVorheriges();
		ziel  = bank(0).getNaechstes();
		lager(0).versetzeFigurAuf(bank(0));
		lager(1).versetzeFigurAuf(start);
		spieler = spieler(1);
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsAufHimmelFeld() throws RegelVerstoss {
		start = bank(0).getVorheriges();
		ziel  = himmel(0);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsAufFremdesHimmelFeld() {
		start = himmel(0);
		ziel  = himmel(0).getNtesFeld(2);
		lager(1).versetzeFigurAuf(start);
		spieler = spieler(1);
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsAufHimmelFeldNachStart() throws RegelVerstoss {
		start = lager(0);
		ziel  = bank(0);
		sollteValidieren(new StartRegel());
		
		start = bank(0);
		ziel  = himmel(0).getNaechstes();
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsImHimmel() throws RegelVerstoss {
		start = himmel(0);
		ziel  = himmel(0).getNtesFeld(2);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren(new VorwaertsRegel(2));
	}
}