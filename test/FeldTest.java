import java.util.Vector;

import PD.Zugsystem.Feld;

public class FeldTest extends ProblemDomainTest {
	public void testNaechstesVorheriges() {
		Feld feld = bankFeld.getNaechstes().getVorheriges();
		assertEquals(bankFeld, feld);
	}
	
	public void testWeg() {
		Vector<Feld> weg = bankFeld.getWeg(zielFeld);
		assertEquals(4, weg.size());
		assertEquals(bankFeld, weg.get(0));
		assertEquals(zielFeld, weg.get(3));
	}
}
