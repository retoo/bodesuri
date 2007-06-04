package pd.regelsystem;

import java.util.List;
import java.util.Vector;

import pd.brett.Feld;
import pd.regelsystem.RegelVerstoss;
import pd.regelsystem.SiebnerRegel;
import pd.zugsystem.Bewegung;

public class SiebnerRegelTest extends RegelTestCase {
	private Feld[] start;
	private Feld[] ziel;
	
	protected void setUp() {
		super.setUp();
		regel = new SiebnerRegel();
		start = new Feld[7];
		ziel  = new Feld[7];
	}
	
	public void testSiebner() throws RegelVerstoss {
		start[0] = bank(0).getNaechstes();
		ziel[0]  = start[0].getNtesFeld(7);
		sollteVerstossGeben();
		
		lager(0).versetzeFigurAuf(start[0]);
		sollteValidieren();
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istBesetztVon(spieler(0)));
	}
	
	public void testSiebnerUnsinnigAberGueltig() throws RegelVerstoss {
		start[0] = bank(0);
		ziel[0]  = start[0].getNtesFeld(2);
		start[1] = ziel[0];
		ziel[1]  = start[1].getNtesFeld(2);
		start[2] = ziel[1];
		ziel[2]  = start[2].getNtesFeld(3);
		lager(0).versetzeFigurAuf(start[0]);
		sollteValidieren();
	}
	
	public void testSiebnerHeimSchicken() throws RegelVerstoss {
		start[0] = bank(0);
		ziel[0]  = start[0].getNtesFeld(7);
		lager(0).versetzeFigurAuf(start[0]);
		Feld gegnerFeld = start[0].getNtesFeld(3);
		lager(1).versetzeFigurAuf(gegnerFeld);
		sollteValidieren();
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istBesetztVon(spieler(0)));
		assertTrue(gegnerFeld.istFrei());
		assertTrue(lager(1).istBesetztVon(spieler(1)));
	}
	
	public void testSiebnerZuWenigSchritte() {
		start[0] = bank(0).getNaechstes();
		ziel[0]  = start[0].getNtesFeld(6);
		lager(0).versetzeFigurAuf(start[0]);
		sollteVerstossGeben();
		
		ziel[0]  = start[0].getNtesFeld(3);
		start[1] = ziel[0].getNtesFeld(3);
		ziel[1]  = start[1].getNtesFeld(1);
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteVerstossGeben();
	}
	
	public void testSiebnerZuVielSchritte() {
		start[0] = bank(0);
		ziel[0]  = start[0].getNtesFeld(10);
		sollteVerstossGeben();
		
		start[1] = ziel[0].getNaechstes();
		ziel[1]  = start[1].getNtesFeld(3);
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteVerstossGeben();
	}
	
	public void testSiebnerMehrfach() throws RegelVerstoss {
		start[0] = bank(0).getNaechstes();
		ziel[0]  = start[0].getNtesFeld(3);
		start[1] = ziel[0].getNaechstes();
		ziel[1]  = start[1].getNtesFeld(4);
		sollteVerstossGeben();
		
		lager(0).versetzeFigurAuf(start[0]);
		sollteVerstossGeben();
		
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteValidieren();
	}
	
	public void testSiebnerMehrfachStartFigurGefressen() {
		start[0] = bank(0).getNaechstes();
		ziel[0]  = start[0].getNaechstes();
		start[1] = bank(0);
		ziel[1]  = bank(0).getNtesFeld(6);
		sollteVerstossGeben();
	}
	
	// TODO: Mehr und kompliziertere Tests schreiben, z. B. mit Himmel.
	
	protected List<Bewegung> getBewegungen() {
		Vector<Bewegung> bewegungen = new Vector<Bewegung>();
		for (int i = 0; i < start.length; ++i) {
			if (start[i] == null) break;
			bewegungen.add(new Bewegung(start[i], ziel[i]));
		}
		return bewegungen;
	}
}