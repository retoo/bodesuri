package pd.regelsystem;

import pd.regelsystem.verstoesse.RegelVerstoss;

/**
 * Testet die Funktionalität der VorwärtsRegel.
 */
public class VorwaertsRegelTest extends RegelTestCase {
	public void testArbeitetMitWeg() {
		assertTrue(new VorwaertsRegel(2).arbeitetMitWeg());
	}

	/**
	 * Testet die Vorwärtsregel, wenn beim Zug auch eine
	 * Figur gefressen wird (Figur auf Zielfeld).
	 * 
	 * @throws RegelVerstoss
	 */
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
	
	/**
	 * In der Vorwärtsregel kann nicht Rückwärts gefahren werden.
	 */
	public void testVorwaertsRueckwaerts() {
		start = bank(0).getNtesFeld(6);
		ziel  = bank(0).getNtesFeld(1);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new VorwaertsRegel(5));
	}
	
	/**
	 * Auf geschützte Felder kann nicht gefahren werden.
	 * 
	 * @throws RegelVerstoss
	 */
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
	
	/**
	 * Vorwärtszug auf Bankfeld (nicht geschützt).
	 * 
	 * @throws RegelVerstoss
	 */
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
	
	/**
	 * Über geschützte Bankfelder kann nicht gezogen werden.
	 * 
	 * @throws RegelVerstoss
	 */
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
	
	/**
	 * Über ungeschützte Bankfelder kann gezogen werden.
	 * 
	 * @throws RegelVerstoss
	 */
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
	
	/**
	 * Über geschützte Bankfelder kann nicht gezogen werden.
	 */
	public void testVorwaertsUeberBankFeldBesetztVonBesitzer() {
		start = bank(0).getVorheriges();
		ziel  = bank(0).getNaechstes();
		lager(0).versetzeFigurAuf(bank(0));
		lager(1).versetzeFigurAuf(start);
		spieler = spieler(1);
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	/**
	 * Normaler Vorwärtszug in den Himmel ist möglich.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testVorwaertsAufHimmelFeld() throws RegelVerstoss {
		start = bank(0).getVorheriges();
		ziel  = himmel(0);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren(new VorwaertsRegel(2));
	}
	
	/**
	 * Vorwärtszug in fremden Himmel ist nicht möglich.
	 */
	public void testVorwaertsAufFremdesHimmelFeld() {
		start = himmel(0);
		ziel  = himmel(0).getNtesFeld(2);
		lager(1).versetzeFigurAuf(start);
		spieler = spieler(1);
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	/**
	 * Nach erfolgtem StartZug darf nicht direkt in den Himmel 
	 * gefahren werden.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testVorwaertsAufHimmelFeldNachStart() throws RegelVerstoss {
		start = lager(0);
		ziel  = bank(0);
		sollteValidieren(new StartRegel());
		
		start = bank(0);
		ziel  = himmel(0).getNaechstes();
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	/**
	 * Normaler Vorwärtszug innerhalb des Himmels ist möglich.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testVorwaertsImHimmel() throws RegelVerstoss {
		start = himmel(0);
		ziel  = himmel(0).getNtesFeld(2);
		lager(0).versetzeFigurAuf(start);
		sollteValidieren(new VorwaertsRegel(2));
	}
	
	/**
	 * In ein LagerFeld kann nicht gezogen werden.
	 */
	public void testVorwaertsAufLagerFeld() {
		start = bank(0);
		ziel  = lager(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new VorwaertsRegel(1));
	}
	
	/**
	 * Aus dem Himmel kann nicht hinausgezogen werden.
	 */
	public void testVorwaertsAusHimmelRaus() {
		start = himmel(0);
		ziel  = bank(0);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new VorwaertsRegel(1));
	}

	/**
	 * Im Himmel kann nicht rückwärts gezogen werden.
	 */
	public void testVorwaertsAberRueckwaertsImHimmel() {
		start = himmel(0, 3);
		ziel  = himmel(0, 1);
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new VorwaertsRegel(4));
	}

	/**
	 * Vorwärtszug für den Partner im Endmodus.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testVorwaertsFuerPartner() throws RegelVerstoss {
		for (int i = 0; i < 4; ++i) {
			lager(0, i).versetzeFigurAuf(himmel(0, i));
		}
		assertTrue(spieler(0).istFertig());

		// Für Partner
		start = bank(2);
		ziel  = bank(2).getNtesFeld(5);
		lager(2, 0).versetzeFigurAuf(start);
		sollteValidieren(new VorwaertsRegel(5));
		
		// Für Gegner
		start = bank(1);
		ziel  = bank(1).getNtesFeld(5);
		lager(1, 0).versetzeFigurAuf(start);
		sollteVerstossGeben(new VorwaertsRegel(5));
	}
}
