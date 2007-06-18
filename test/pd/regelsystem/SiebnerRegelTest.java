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
	
	public void testAndereMitMehrerenBewegungen() {
		start[0] = bank(0);
		ziel[0]  = start[0].getNtesFeld(3);
		lager(0).versetzeFigurAuf(start[0]);
		start[1] = ziel[0].getNaechstes();
		ziel[1]  = start[1].getNaechstes();
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteVerstossGeben(new VorwaertsRegel(4));
		sollteVerstossGeben(new TauschRegel());
		sollteVerstossGeben(new StartRegel());
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
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istFrei());
		assertTrue(start[1].istFrei());
		assertTrue(ziel[1].istFrei());
		assertTrue(start[2].istFrei());
		assertTrue(ziel[2].istBesetztVon(spieler(0)));
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
	
	public void testSiebnerZweiHeimSchicken() throws RegelVerstoss {
		start[0] = bank(0);
		ziel[0]  = bank(0).getNtesFeld(7);
		lager(0).versetzeFigurAuf(start[0]);
		Feld gegner1 = bank(0).getNtesFeld(2);
		Feld gegner2 = bank(0).getNtesFeld(4);
		lager(1, 0).versetzeFigurAuf(gegner1);
		lager(1, 1).versetzeFigurAuf(gegner2);
		sollteValidieren();
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istBesetztVon(spieler(0)));
		assertTrue(gegner1.istFrei());
		assertTrue(gegner2.istFrei());
		assertTrue(lager(1, 0).istBesetztVon(spieler(1)));
		assertTrue(lager(1, 1).istBesetztVon(spieler(1)));
	}
	
	public void testSiebnerZuWenigSchritte() {
		sollteVerstossGeben();
		
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
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istBesetztVon(spieler(0)));
		assertTrue(start[1].istFrei());
		assertTrue(ziel[1].istBesetztVon(spieler(0)));
	}
	
	public void testSiebnerMehrfachStartFigurGefressen() {
		start[0] = bank(0);
		ziel[0]  = bank(0).getNtesFeld(6);
		lager(0).versetzeFigurAuf(start[0]);
		start[1] = bank(0).getNaechstes();
		ziel[1]  = start[1].getNaechstes();
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteVerstossGeben();
	}
	
	public void testSiebnerUeberGeschuetztes() throws RegelVerstoss {
		start[0] = lager(0);
		ziel[0]  = bank(0);
		sollteValidieren(new StartRegel());
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istBesetztVon(spieler(0)));
		
		start[0] = bank(0).getVorheriges();
		ziel[0]  = start[0].getNtesFeld(7);
		lager(0).versetzeFigurAuf(start[0]);
		sollteVerstossGeben();
	}
	
	public void testSiebnerUeberBankFeld() throws RegelVerstoss {
		start[0] = bank(0).getVorheriges();
		ziel[0]  = bank(0);
		lager(0).versetzeFigurAuf(start[0]);
		start[1] = bank(0).getVorheriges().getVorheriges();
		ziel[1]  = start[1].getNtesFeld(6);
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteValidieren();
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istFrei());
		assertTrue(lager(0).istBesetztVon(spieler(0)));
		assertTrue(start[1].istFrei());
		assertTrue(ziel[1].istBesetztVon(spieler(0)));
	}
	
	public void testSiebnerMitGeschuetztem() throws RegelVerstoss {
		start[0] = bank(0);
		ziel[0]  = bank(0).getNtesFeld(5);
		lager(0).versetzeFigurAuf(start[0]);
		start[0].setGeschuetzt(true);
		start[1] = bank(0).getVorheriges();
		ziel[1]  = bank(0).getNaechstes();
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteValidieren();
		
		assertTrue(start[0].istFrei());
		assertTrue(ziel[0].istBesetztVon(spieler(0)));
		assertTrue(start[1].istFrei());
		assertTrue(ziel[1].istBesetztVon(spieler(0)));
	}
	
	public void testSiebnerMitGeschuetztemVerzwickt() throws RegelVerstoss {
		start[0] = bank(0);
		ziel[0]  = bank(0).getNtesFeld(3);
		lager(0).versetzeFigurAuf(start[0]);
		start[0].setGeschuetzt(true);
		start[1] = bank(0).getVorheriges();
		ziel[1]  = bank(0);
		lager(0, 1).versetzeFigurAuf(start[1]);
		start[2] = bank(0).getVorheriges().getVorheriges();
		ziel[2]  = bank(0).getNaechstes();
		lager(0, 2).versetzeFigurAuf(start[2]);
		sollteValidieren();
	}
	
	public void testSiebnerMitFremdem() {
		start[0] = bank(0);
		ziel[0]  = bank(0).getNtesFeld(7);
		lager(1).versetzeFigurAuf(start[0]);
		sollteVerstossGeben();
	}
	
	public void testSiebnerMitLeerenBewegungen() throws RegelVerstoss {
		start[0] = bank(0);
		ziel[0]  = bank(0).getNtesFeld(7);
		lager(0).versetzeFigurAuf(start[0]);
		start[1] = bank(0).getVorheriges();
		ziel[1]  = bank(0).getVorheriges();
		lager(0, 1).versetzeFigurAuf(start[1]);
		sollteValidieren();
	}
	
	public void testSiebnerKannZiehen() {
		assertFalse(regel.kannZiehen(spieler(0)));
		
		lager(0, 0).versetzeFigurAuf(himmel(0, 0));
		lager(0, 1).versetzeFigurAuf(himmel(0, 1));
		lager(0, 2).versetzeFigurAuf(himmel(0, 2));
		lager(0, 3).versetzeFigurAuf(himmel(0, 3));
		assertFalse(regel.kannZiehen(spieler(0)));
		
		himmel(0, 0).versetzeFigurAuf(bank(0));
		assertTrue(regel.kannZiehen(spieler(0)));
	}
	
	// TODO: Robin Mehr und kompliziertere Tests schreiben, z. B. mit Himmel. --Robin
	
	protected List<Bewegung> getBewegungen() {
		Vector<Bewegung> bewegungen = new Vector<Bewegung>();
		for (int i = 0; i < start.length; ++i) {
			if (start[i] == null) break;
			bewegungen.add(new Bewegung(start[i], ziel[i]));
		}
		return bewegungen;
	}
}
