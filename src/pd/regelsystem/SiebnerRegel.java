package pd.regelsystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import pd.brett.BankFeld;
import pd.brett.Feld;
import pd.spieler.Figur;
import pd.spieler.Spieler;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.HeimschickAktion;
import pd.zugsystem.Weg;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class SiebnerRegel extends VorwaertsRegel {
	public SiebnerRegel() {
		super(7); // Für kannZiehen gebraucht.
		setBeschreibung("7 vorwärts auf mehrere Figuren aufteilbar, Überholen schickt heim");
	}

	// TODO: Reto & Robin Hat Review nötig :). --Robin
	@SuppressWarnings("null")
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		if (zugEingabe.getAnzahlBewegungen() <= 0) {
			throw new RegelVerstoss("Mindestens eine Bewegung nötig.");
		}

		Spieler spieler = zugEingabe.getBetroffenerSpieler();
		
		int wegLaenge = 0;
		HashMap<Feld, Figur> figuren = new HashMap<Feld, Figur>();
		Set<Feld> geschuetzt = new HashSet<Feld>();
		for (Bewegung bewegung : zugEingabe.getBewegungen()) {
			pruefeBewegung(bewegung, spieler);
			Weg weg = bewegung.getWeg();
			pruefeWegRichtung(weg);
			wegLaenge += weg.size() - 1;
			for (Feld feld : weg) {
				figuren.put(feld, feld.getFigur());
				if (feld.istGeschuetzt()) {
					geschuetzt.add(feld);
				}
			}
		}

		if (wegLaenge != 7) {
			throw new WegLaengeVerstoss(7, wegLaenge);
		}

		Zug zug = new Zug();

		for (Bewegung bewegung : zugEingabe.getBewegungen()) {
			for (Feld feld : bewegung.getWeg()) {
				Figur figur = figuren.get(feld);
				boolean hatFigur = (figur != null);

				if (feld == bewegung.start) {
					if (!hatFigur) {
						throw new RegelVerstoss("Startfeld hat keine Figur " +
						                        "zum Ziehen.");
					} else if (figur.getSpieler() != zugEingabe.getSpieler()) {
						throw new RegelVerstoss("Man kann nur mit eigenen Figuren ziehen.");
					}
					continue;
				}

				if (geschuetzt.contains(feld) ||
				    (hatFigur && feld.istHimmel())) {
					throw new RegelVerstoss("Weg beinhaltet geschütztes Feld.");
				}

				if (hatFigur) {
					zug.fuegeHinzu(new HeimschickAktion(feld));
					figuren.put(feld, null);
				}
			}
			
			if (bewegung.start == bewegung.ziel) {
				continue;
			}

			Figur figur = figuren.get(bewegung.start);
			figuren.put(bewegung.start, null);
			figuren.put(bewegung.ziel, figur);
			geschuetzt.remove(bewegung.start);

			zug.fuegeHinzu(new Aktion(bewegung.start, bewegung.ziel));
		}

		return zug;
	}
	
	public boolean kannZiehen(Spieler spieler) {
		Map<Figur, Feld> positionen = new IdentityHashMap<Figur, Feld>();
		for (Figur figur : spieler.getFiguren()) {
			positionen.put(figur, figur.getFeld());
		}
		List<Figur> reihenfolge = new Vector<Figur>();
		return sucheZug(spieler, positionen, reihenfolge, 7);
	}
	
	private boolean sucheZug(Spieler spieler, Map<Figur, Feld> positionen,
	                         List<Figur> reihenfolge, int schritte) {
		if (schritte == 0) {
			return istZugMoeglich(spieler, positionen, reihenfolge);
		}
		
		for (Figur figur : spieler.getFiguren()) {
			Feld feld = positionen.get(figur);
			
			if (feld.istLager()) {
				continue;
			}
			
			Feld feldNeu;
			List<Feld> kandidaten = new Vector<Feld>();
			if (feld.istBank() && ((BankFeld) feld).getSpieler() == spieler) {
				feldNeu = ((BankFeld) feld).getHimmel();
				kandidaten.add(feldNeu);
			}
			feldNeu = feld.getNaechstes();
			if (feldNeu != null) {
				kandidaten.add(feldNeu);
			}
			
			boolean figurInReihenfolge = reihenfolge.contains(figur);
			if (!figurInReihenfolge) {
				reihenfolge.add(figur);
			}
			for (Feld kandidat : kandidaten) {
				positionen.put(figur, kandidat);
				if (sucheZug(spieler, positionen, reihenfolge, schritte - 1)) {
					return true;
				}
				positionen.put(figur, feld);
			}
			if (!figurInReihenfolge) {
				reihenfolge.remove(figur);
			}
		}
		
		return false;
	}
	
	private boolean istZugMoeglich(Spieler spieler, Map<Figur, Feld> positionen,
	                               List<Figur> reihenfolge) {
		List<Bewegung> bewegungen = new Vector<Bewegung>();
		for (Figur figur : reihenfolge) {
			Feld start = figur.getFeld();
			Feld ziel  = positionen.get(figur);
			if (start != ziel) {
				bewegungen.add(new Bewegung(start, ziel));
			}
		}
		return istZugMoeglich(spieler, bewegungen);
	}
}
