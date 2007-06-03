package pd.regelsystem;

public class TauschRegelTest extends RegelTestCase {
	public void testTauschen() throws RegelVerstoss {
		start = bankFeld1.getNtesFeld(2);
		ziel  = bankFeld1.getNtesFeld(5);
		lagerFeld1.versetzeFigurAuf(start);
		lagerFeld2.versetzeFigurAuf(ziel);
		sollteValidieren(new TauschRegel());
		
		assertTrue(start.istBesetztVon(spieler2));
		assertTrue(ziel.istBesetztVon(spieler1));
	}
	
	public void testTauschenZweiEigene() {
		start = bankFeld1.getNtesFeld(2);
		ziel  = bankFeld1.getNtesFeld(5);
		start.setFigur(spieler1.getFiguren().get(0));
		ziel.setFigur(spieler1.getFiguren().get(1));
		sollteVerstossGeben(new TauschRegel());
	}
	
	public void testTauschenZweiFremde() {
		start = bankFeld1.getNtesFeld(2);
		ziel  = bankFeld1.getNtesFeld(5);
		start.setFigur(spieler2.getFiguren().get(0));
		ziel.setFigur(spieler2.getFiguren().get(1));
		sollteVerstossGeben(new TauschRegel());
	}
	
	public void testTauschenMitEinerFigur() {
		start = bankFeld1;
		ziel  = bankFeld1.getNaechstes();
		lagerFeld1.versetzeFigurAuf(start);
		sollteVerstossGeben(new TauschRegel());
		
		start.versetzeFigurAuf(ziel);
		sollteVerstossGeben(new TauschRegel());
	}
	
	public void testTauschenGeschuetzt() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(ziel.istGeschuetzt());
		
		start = bankFeld1;
		ziel  = bankFeld1.getNtesFeld(7);
		lagerFeld2.versetzeFigurAuf(ziel);
		sollteVerstossGeben(new TauschRegel());
	}
}
