package pd.regelsystem;

public class TauschRegelTest extends RegelTestCase {
	public void testTauschen() throws RegelVerstoss {
		start = bank(0).getNtesFeld(2);
		ziel  = bank(0).getNtesFeld(5);
		lager(0).versetzeFigurAuf(start);
		lager(1).versetzeFigurAuf(ziel);
		sollteValidieren(new TauschRegel());
		
		assertTrue(start.istBesetztVon(spieler(1)));
		assertTrue(ziel.istBesetztVon(spieler(0)));
	}
	
	public void testTauschenZweiEigene() {
		start = bank(0).getNtesFeld(2);
		ziel  = bank(0).getNtesFeld(5);
		lager(0, 0).versetzeFigurAuf(start);
		lager(0, 1).versetzeFigurAuf(ziel);
		sollteVerstossGeben(new TauschRegel());
	}
	
	public void testTauschenZweiFremde() {
		start = bank(0).getNtesFeld(2);
		ziel  = bank(0).getNtesFeld(5);
		lager(1, 0).versetzeFigurAuf(start);
		lager(1, 1).versetzeFigurAuf(ziel);
		sollteVerstossGeben(new TauschRegel());
	}
	
	public void testTauschenMitEinerFigur() {
		start = bank(0);
		ziel  = bank(0).getNaechstes();
		lager(0).versetzeFigurAuf(start);
		sollteVerstossGeben(new TauschRegel());
		
		start.versetzeFigurAuf(ziel);
		sollteVerstossGeben(new TauschRegel());
	}
	
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
		sollteVerstossGeben(new TauschRegel());
	}
}
