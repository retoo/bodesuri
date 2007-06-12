package pd.regelsystem;

import java.util.HashMap;

import pd.brett.Feld;
import pd.spieler.Figur;
import pd.spieler.Spieler;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Weg;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class SiebnerRegel extends VorwaertsRegel {
	public SiebnerRegel() {
	    super(7); // Für kannZiehen gebraucht.
	    setBeschreibung("7 vorwärts, auf beliebige Figuren aufgeteilt");
    }

	// TODO: Hat Review nötig :).
	@SuppressWarnings("null")
    public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		if (zugEingabe.getAnzahlBewegungen() <= 0) {
			throw new RegelVerstoss("Mindestens eine Bewegung nötig.");
		}

		int wegLaenge = 0;
		HashMap<Feld, Figur> figuren = new HashMap<Feld, Figur>();
		for (Bewegung bewegung : zugEingabe.getBewegungen()) {
			Weg weg = bewegung.getWeg();
			wegLaenge += weg.size() - 1;
			for (Feld feld : weg) {
				figuren.put(feld, feld.getFigur());
			}
		}

		if (wegLaenge != 7) {
			throw new RegelVerstoss("Zug muss über " + 7 +
                                    " und nicht " + wegLaenge + " Felder gehen.");
		}

		Spieler spieler = zugEingabe.getSpieler();
		Zug zug = new Zug();

		for (Bewegung bewegung : zugEingabe.getBewegungen()) {
			pruefeBewegung(bewegung, spieler);
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

				if (feld.istGeschuetzt() || (hatFigur && feld.istHimmel())) {
					throw new RegelVerstoss("Weg beinhaltet geschütztes Feld.");
				}

				if (hatFigur) {
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

	// TODO: SiebnerRegel.kannZiehen implementieren (Kombinatorik).
	public boolean kannZiehen(Spieler spieler) {
		return super.kannZiehen(spieler);
	}
}
