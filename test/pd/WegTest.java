package pd;

import pd.zugsystem.Bewegung;
import pd.zugsystem.Weg;

public class WegTest extends ProblemDomainTestCase {
	public void testWegVorwaerts() {
		Bewegung bewegung = new Bewegung(bank(0), bank(0).getNtesFeld(3));
		Weg weg = bewegung.getWeg();
		
		assertEquals(bewegung.start, weg.get(0));
		assertEquals(bewegung.start.getNtesFeld(1), weg.get(1));
		assertEquals(bewegung.start.getNtesFeld(2), weg.get(2));
		assertEquals(bewegung.ziel, weg.get(3));
		assertEquals(4, weg.size());
		assertFalse(weg.istRueckwaerts());
	}
	
	public void testWegVorwaertsUeberGrenze() {
		Bewegung bewegung = new Bewegung(bank(3), bank(0));
		Weg weg = bewegung.getWeg();
		
		assertEquals(bewegung.start, weg.firstElement());
		assertEquals(bewegung.ziel, weg.lastElement());
		assertEquals(17, weg.size());
		assertFalse(weg.istRueckwaerts());
	}
	
	public void testWegRueckwaerts() {
		Bewegung bewegung = new Bewegung(bank(0).getNtesFeld(5), bank(0));
		Weg weg = bewegung.getWeg();
		
		assertEquals(bewegung.start, weg.firstElement());
		assertEquals(bewegung.ziel, weg.lastElement());
		assertEquals(6, weg.size());
		assertTrue(weg.istRueckwaerts());
	}
	
	public void testWegRueckwaertsUeberGrenze() {
		Bewegung bewegung = new Bewegung(bank(0), bank(3));
		Weg weg = bewegung.getWeg();
		
		assertEquals(bewegung.start, weg.firstElement());
		assertEquals(bewegung.ziel, weg.lastElement());
		assertEquals(17, weg.size());
		assertTrue(weg.istRueckwaerts());
	}
}
