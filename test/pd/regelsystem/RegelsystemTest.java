package pd.regelsystem;

import java.util.List;

import pd.ProblemDomainTestCase;
import pd.regelsystem.karten.Ass;
import pd.regelsystem.karten.Karte;
import pd.regelsystem.karten.KartenFarbe;

public class RegelsystemTest extends ProblemDomainTestCase {
	public void testKannZiehen() {
		Karte karte = new Ass(KartenFarbe.Herz);
		spieler(0).getKarten().add(karte);

		assertTrue(Regelsystem.kannZiehen(spieler(0)));
	}

	public void testGetMoeglicheZuege() {
		Karte karte = new Ass(KartenFarbe.Herz);
		spieler(0).getKarten().add(karte);

		List<ZugEingabe> zuege1 = Regelsystem.getMoeglicheZuege(spieler(0));
		List<ZugEingabe> zuege2 = karte.getRegel().getMoeglicheZuege(spieler(0), karte);
		assertEquals(zuege1, zuege2);
	}
}
