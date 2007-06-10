package pd.regelsystem;

import java.util.HashMap;
import java.util.List;

import pd.brett.Feld;
import pd.spieler.Figur;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class SiebnerRegel extends VorwaertsRegel {
	public SiebnerRegel() {
	    super(0); // Schritte nicht benötigt, da validiere() überschrieben
	    setBeschreibung("7 vorwärts, auf beliebige Figuren aufgeteilt");
    }

	// TODO: Hat Review nötig :).
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		if (zugEingabe.getAnzahlBewegungen() <= 0) {
			throw new RegelVerstoss("Mindestens eine Bewegung nötig.");
		}
		
		int schritte = 0;
		HashMap<Feld, Figur> figuren = new HashMap<Feld, Figur>();
		for (Bewegung bewegung : zugEingabe.getBewegungen()) {
			List<Feld> weg = getWeg(bewegung);
			schritte += weg.size() - 1;
			for (Feld feld : weg) {
				figuren.put(feld, feld.getFigur());
			}
		}
		
		if (schritte != 7) {
			throw new RegelVerstoss("Zug muss über 7 Felder gehen.");
		}
		
		Zug zug = new Zug();
		
		for (Bewegung bewegung : zugEingabe.getBewegungen()) {
			for (Feld feld : getWeg(bewegung)) {
				Figur figur = figuren.get(feld);
				
				if (feld == bewegung.start) {
					if (figur == null) {
						throw new RegelVerstoss("Startfeld hat keine Figur " +
						                        "zum Ziehen.");
					} else if (figur.getSpieler() != zugEingabe.getSpieler()) {
						throw new RegelVerstoss("Man kann nur mit eigenen Figuren ziehen.");
					}
					continue;
				}

				if (feld.istGeschuetzt() ||
				    (figur != null && feld.istHimmel())) {
					throw new RegelVerstoss("Weg beinhaltet geschütztes Feld.");
				}
				
				if (figur != null) {
					zug.fuegeHinzu(heimschickAktion(feld, figur.getSpieler()));
					figuren.put(feld, null);
				}
			}
			
			Figur figur = figuren.get(bewegung.start);
			figuren.put(bewegung.start, null);
			figuren.put(bewegung.ziel, figur);
			
			zug.fuegeHinzu(new Aktion(bewegung.start, bewegung.ziel));
		}
		
		return zug;
	}
}
