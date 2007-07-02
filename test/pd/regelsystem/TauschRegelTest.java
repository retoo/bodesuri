package pd.regelsystem;

import pd.regelsystem.verstoesse.RegelVerstoss;

/**
 * Testet die Funktionalität der TauschRegel.
 */
public class TauschRegelTest extends RegelTestCase {
	protected void setUp() {
		super.setUp();
		regel = new TauschRegel();
	}

	public void testArbeitetMitWeg() {
		assertFalse(regel.arbeitetMitWeg());
	}

	/**
	 * Prüft, ob ein simpler Tausch funktioniert.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testTauschen() throws RegelVerstoss {
		start = bank(0).getNtesFeld(2);
		ziel  = bank(0).getNtesFeld(5);
		lager(0).versetzeFigurAuf(start);
		lager(1).versetzeFigurAuf(ziel);
		sollteValidieren();
		
		assertTrue(start.istBesetztVon(spieler(1)));
		assertTrue(ziel.istBesetztVon(spieler(0)));
	}
	
	/**
	 * Prüft, ob zwei eigene Figuren nicht getauscht werden können.
	 */
	public void testTauschenZweiEigene() {
		start = bank(0).getNtesFeld(2);
		ziel  = bank(0).getNtesFeld(5);
		lager(0, 0).versetzeFigurAuf(start);
		lager(0, 1).versetzeFigurAuf(ziel);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob zwei fremde Figuren nicht getauscht werden können.
	 */
	public void testTauschenZweiFremde() {
		start = bank(0).getNtesFeld(2);
		ziel  = bank(0).getNtesFeld(5);
		lager(1, 0).versetzeFigurAuf(start);
		lager(1, 1).versetzeFigurAuf(ziel);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob nicht "auf der Stelle", d.h. nur mit einer Figur
	 * getauscht werden kann.
	 */
	public void testTauschenMitEinerFigur() {
		start = bank(0);
		ziel  = bank(0).getNaechstes();
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben();
		
		start.versetzeFigurAuf(ziel);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob geschützte Figuren auch wirklich nicth getauscht 
	 * werden können.
	 * 
	 * @throws RegelVerstoss
	 */
	public void testTauschenGeschuetzt() throws RegelVerstoss {
		start = lager(0);
		ziel  = bank(0);
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
		assertTrue(ziel.istGeschuetzt());
		
		start = bank(0);
		ziel  = bank(0).getNtesFeld(7);
		lager(1).versetzeFigurAuf(ziel);
		sollteVerstossGeben();
	}
	
	/**
	 * Prüft, ob Figuren, die auf einem Lagerfeld stehen, nicht
	 * getauscht werden können.
	 */
	public void testTauschenMitLagerFeld() {
		start = lager(0);
		ziel  = bank(0).getNaechstes();
		lager(1).versetzeFigurAuf(ziel);
		sollteVerstossGeben();
	}
}
