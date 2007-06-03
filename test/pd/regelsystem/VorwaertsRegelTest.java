package pd.regelsystem;

import pd.spieler.Figur;

public class VorwaertsRegelTest extends RegelTestCase {
	public void testVorwaertsHeimSchicken() throws RegelVerstoss {
		start = bankFeld1;
		ziel  = start.getNtesFeld(5);
		lagerFeld1.versetzeFigurAuf(start);
		lagerFeld2.versetzeFigurAuf(ziel);
		Figur figur2 = ziel.getFigur();
		sollteValidieren(new VorwaertsRegel(5));
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertEquals(figur2, lagerFeld2.getFigur());
	}
	
	public void testVorwaertsAufGeschuetztes() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(ziel.istGeschuetzt());

		spieler = spieler2;
		start = bankFeld1.getVorheriges().getVorheriges();
		ziel  = bankFeld1;
		lagerFeld2.versetzeFigurAuf(start);
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsAufBankFeldBesetztVonBesitzer()
			throws RegelVerstoss {
		start = bankFeld1.getVorheriges().getVorheriges();
		ziel  = bankFeld1;
		lagerFeld1.versetzeFigurAuf(bankFeld1);
		lagerFeld2.versetzeFigurAuf(start);
		spieler = spieler2;
		sollteValidieren(new VorwaertsRegel(2));
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler2));
		assertTrue(lagerFeld1.istBesetztVon(spieler1));
	}
	
	public void testVorwaertsUeberGeschuetztes() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(ziel.istGeschuetzt());

		spieler = spieler2;
		start = bankFeld1.getVorheriges();
		ziel  = bankFeld1.getNaechstes();
		lagerFeld2.versetzeFigurAuf(start);
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsUeberBankFeldBesetzt() throws RegelVerstoss {
		start = bankFeld1.getVorheriges();
		ziel  = bankFeld1.getNaechstes();
		lagerFeld1.versetzeFigurAuf(start);
		lagerFeld2.versetzeFigurAuf(bankFeld1);
		sollteValidieren(new VorwaertsRegel(2));
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(bankFeld1.istBesetztVon(spieler2));
	}
	
	public void testVorwaertsUeberBankFeldBesetztVonBesitzer() {
		start = bankFeld1.getVorheriges();
		ziel  = bankFeld1.getNaechstes();
		lagerFeld1.versetzeFigurAuf(bankFeld1);
		lagerFeld2.versetzeFigurAuf(start);
		spieler = spieler2;
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsAufHimmelFeld() throws RegelVerstoss {
		start = bankFeld1.getVorheriges();
		ziel  = himmelFeld1;
		lagerFeld1.versetzeFigurAuf(start);
		sollteValidieren(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsAufFremdesHimmelFeld() {
		start = himmelFeld1;
		ziel  = himmelFeld1.getNtesFeld(2);
		lagerFeld2.versetzeFigurAuf(start);
		spieler = spieler2;
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsAufHimmelFeldNachStart() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		sollteValidieren(new StartRegel());
		
		start = bankFeld1;
		ziel  = himmelFeld1.getNaechstes();
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsImHimmel() throws RegelVerstoss {
		start = himmelFeld1;
		ziel  = himmelFeld1.getNtesFeld(2);
		lagerFeld1.versetzeFigurAuf(start);
		sollteValidieren(new VorwaertsRegel(2));
	}
}
