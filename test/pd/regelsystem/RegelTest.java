package pd.regelsystem;
import pd.ProblemDomainTestCase;
import pd.brett.Feld;
import pd.karten.KartenFarbe;
import pd.karten.Vier;
import pd.regelsystem.Regel;
import pd.regelsystem.RegelVerstoss;
import pd.regelsystem.StartRegel;
import pd.regelsystem.TauschRegel;
import pd.regelsystem.VorwaertsRegel;
import pd.spieler.Figur;
import pd.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class RegelTest extends ProblemDomainTestCase {
	private Regel viererRegel;
	
	private Spieler spieler;
	private Feld start;
	private Feld ziel;
	
	protected void setUp() {
		super.setUp();
		viererRegel = new Vier(KartenFarbe.Herz, 0).getRegel();
		spieler = spieler1;
	}
	
	public void testVier() throws RegelVerstoss {
		start = bankFeld1;
		ziel  = start.getNtesFeld(4);
		lagerFeld1.versetzeFigurAuf(start);
		sollteValidieren(viererRegel);
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
	}
	
	public void testVierFalsch() {
		start = bankFeld1;
		ziel  = start.getNtesFeld(3);
		lagerFeld1.versetzeFigurAuf(start);
		sollteVerstossGeben(viererRegel);
		
		start = lagerFeld1;
		sollteVerstossGeben(viererRegel);
	}

	public void testVierRueckwaerts() throws RegelVerstoss {
		start = bankFeld1.getNtesFeld(4);
		ziel  = bankFeld1;
		lagerFeld1.versetzeFigurAuf(start);
		sollteValidieren(viererRegel);
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
	}
	
	public void testVierRueckwaertsFalsch() {
		start = bankFeld1.getNtesFeld(3);
		ziel  = bankFeld1;
		lagerFeld1.versetzeFigurAuf(start);
		sollteVerstossGeben(viererRegel);
	}
	
	public void testVorwaertsAufGeschuetztes() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(ziel.istGeschuetzt());

		spieler = spieler2;
		start = bankFeld1.getVorheriges().getVorheriges();
		ziel  = bankFeld1;
		lagerFeld2.versetzeFigurAuf(start);
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsAufBankFeldBesetztVonBesitzer()
			throws RegelVerstoss {
		start = bankFeld1.getVorheriges().getVorheriges();
		ziel  = bankFeld1;
		lagerFeld1.versetzeFigurAuf(bankFeld1);
		lagerFeld2.versetzeFigurAuf(start);
		spieler = spieler2;
		sollteValidieren(new VorwaertsRegel(2));
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler2));
		assertTrue(lagerFeld1.istBesetztVon(spieler1));
	}
	
	public void testVorwaertsUeberGeschuetztes() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(ziel.istGeschuetzt());

		spieler = spieler2;
		start = bankFeld1.getVorheriges();
		ziel  = bankFeld1.getNaechstes();
		lagerFeld2.versetzeFigurAuf(start);
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsUeberBankFeldBesetzt() throws RegelVerstoss {
		start = bankFeld1.getVorheriges();
		ziel  = bankFeld1.getNaechstes();
		lagerFeld1.versetzeFigurAuf(start);
		lagerFeld2.versetzeFigurAuf(bankFeld1);
		sollteValidieren(new VorwaertsRegel(2));
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(bankFeld1.istBesetztVon(spieler2));
	}
	
	public void testVorwaertsUeberBankFeldBesetztVonBesitzer() {
		start = bankFeld1.getVorheriges();
		ziel  = bankFeld1.getNaechstes();
		lagerFeld1.versetzeFigurAuf(bankFeld1);
		lagerFeld2.versetzeFigurAuf(start);
		spieler = spieler2;
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsAufHimmelFeld() throws RegelVerstoss {
		start = bankFeld1.getVorheriges();
		ziel  = himmelFeld1;
		lagerFeld1.versetzeFigurAuf(start);
		sollteValidieren(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsAufFremdesHimmelFeld() {
		start = himmelFeld1;
		ziel  = himmelFeld1.getNtesFeld(2);
		lagerFeld2.versetzeFigurAuf(start);
		spieler = spieler2;
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsAufHimmelFeldNachStart() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		sollteValidieren(new StartRegel());
		
		start = bankFeld1;
		ziel  = himmelFeld1.getNaechstes();
		sollteVerstossGeben(new VorwaertsRegel(2));
	}
	
	public void testVorwaertsImHimmel() throws RegelVerstoss {
		start = himmelFeld1;
		ziel  = himmelFeld1.getNtesFeld(2);
		lagerFeld1.versetzeFigurAuf(start);
		sollteValidieren(new VorwaertsRegel(2));
	}
	
	public void testStart() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(ziel.istGeschuetzt());
		
		start = bankFeld1;
		ziel  = start.getNtesFeld(5);
		sollteValidieren(new VorwaertsRegel(5));
		
		assertFalse(start.istGeschuetzt());
		assertFalse(ziel.istGeschuetzt());
	}
	
	public void testStartNichtVonLagerFeldAus() {
		start = bankFeld1;
		ziel  = bankFeld1.getNaechstes();
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartKeineFigurAufLagerFeld() {
		start = lagerFeld1;
		ziel  = bankFeld1;
		lagerFeld1.versetzeFigurAuf(ziel);
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartNichtAufBankFeld() {
		start = lagerFeld1;
		ziel  = bankFeld1.getNaechstes();
		sollteVerstossGeben(new StartRegel());
		
		ziel  = bankFeld2;
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartAndererSpieler() {
		start = lagerFeld2;
		ziel  = bankFeld2;
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartGeschuetzt() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(ziel.istGeschuetzt());
		
		start = brett.getLagerFelderVon(spieler1).get(3);
		ziel  = bankFeld1;
		sollteVerstossGeben(new StartRegel());
	}
	
	public void testStartHeimSchicken() throws RegelVerstoss {
		start = lagerFeld1;
		ziel  = bankFeld1;
		lagerFeld2.versetzeFigurAuf(ziel);
		sollteValidieren(new StartRegel());
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertTrue(ziel.istGeschuetzt());
		assertTrue(lagerFeld2.istBesetztVon(spieler2));
	}
	
	public void testHeimSchicken() throws RegelVerstoss {
		start = bankFeld1;
		ziel  = start.getNtesFeld(5);
		lagerFeld1.versetzeFigurAuf(start);
		lagerFeld2.versetzeFigurAuf(ziel);
		Figur figur2 = ziel.getFigur();
		sollteValidieren(new VorwaertsRegel(5));
		
		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler1));
		assertEquals(figur2, lagerFeld2.getFigur());
	}
	
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
	
	public void testRegelVerstoss() {
		RegelVerstoss rv = new RegelVerstoss("Des Todes!");
		assertEquals("Regelverstoss: Des Todes!", rv.toString());
	}
	
	private void sollteValidieren(Regel regel) throws RegelVerstoss {
		Bewegung bewegung = new Bewegung(start, ziel);
		ZugEingabe ze = new ZugEingabe(spieler, null, bewegung);
		Zug zug = regel.validiere(ze);
		zug.ausfuehren();
    }
	
	private void sollteVerstossGeben(Regel regel) {
		Bewegung bewegung = new Bewegung(start, ziel);
		ZugEingabe ze = new ZugEingabe(spieler, null, bewegung);
        try {
	        regel.validiere(ze);
	        fail("Sollte RegelVerstoss geben.");
        } catch (RegelVerstoss rv) {
        	assertNotNull(rv);
        }
	}
}
