package pd;

import pd.spiel.spieler.Figur;
import pd.spiel.spieler.Partnerschaft;
import pd.spiel.spieler.Spieler;

public class PartnerschaftTest extends ProblemDomainTestCase {
	public void testIstFertig() {
		Spieler spielerA = spieler(0);
		Spieler spielerB = spieler(2);
		Partnerschaft partnerschaft = new Partnerschaft(spielerA, spielerB);
		assertEquals(spielerA, partnerschaft.getSpielerA());
		assertEquals(spielerB, partnerschaft.getSpielerB());

		assertFalse(spielerA.istFertig());
		assertFalse(spielerB.istFertig());
		assertFalse(partnerschaft.istFertig());

		macheFertig(spielerA);
		assertTrue(spielerA.istFertig());
		assertFalse(partnerschaft.istFertig());

		macheFertig(spielerB);
		assertTrue(spielerB.istFertig());
		assertTrue(partnerschaft.istFertig());
	}

	private void macheFertig(Spieler spieler) {
		for (int i = 0; i < 4; ++i) {
			Figur figur = spieler.getFiguren().get(i);
			figur.versetzeAuf(brett.getHimmelFelderVon(spieler).get(i));
		}
	}
}
