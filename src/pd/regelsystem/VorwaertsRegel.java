package pd.regelsystem;

import java.util.List;
import java.util.Vector;

import pd.brett.Feld;
import pd.brett.LagerFeld;
import pd.spieler.Spieler;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class VorwaertsRegel extends Regel {
	private int schritte;
	
	public VorwaertsRegel(int schritte) {
		this.schritte = schritte;
	}
	
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		Bewegung bewegung = zugEingabe.getBewegung();
		Feld start = bewegung.getStart();
		Feld ziel  = bewegung.getZiel();
		
		if (!start.istBesetztVon(zugEingabe.getSpieler())) {
			throw new RegelVerstoss("Zug muss mit Figur begonnen werden.");
		}
		
		if (getWeg(bewegung).size() != schritte + 1) {
			throw new RegelVerstoss("Zug muss über " + schritte +
			                        " Felder gehen.");
		}
		
		Zug zug = new Zug();
		
		if (ziel.istBesetzt()) {
			Spieler sp = ziel.getFigur().getSpieler();
			// TODO: Gefällt mir noch nicht, vielleicht ne Methode in Spieler?
			LagerFeld lf = sp.getSpiel().getBrett().getFreiesLagerFeldVon(sp);
			zug.fuegeHinzu(new Aktion(ziel, lf));
		}
		
		zug.fuegeHinzu(new Aktion(start, ziel));
		
		return zug;
	}
	
	protected List<Feld> getWeg(Bewegung bewegung) {
		Vector<Feld> weg = new Vector<Feld>();
		Feld feld = bewegung.getStart();
		while (feld != bewegung.getZiel()) {
			weg.add(feld);
			feld = feld.getNaechstes();
		}
		weg.add(feld);
		return weg;
	}
}
