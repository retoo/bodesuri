package pd.regelsystem;

import java.util.List;

import pd.brett.Feld;
import pd.karten.Karte;
import pd.spieler.Figur;
import pd.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.TauschAktion;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

/**
 * Regel für das Tauschen zweier Figuren.
 */
public class TauschRegel extends Regel {
	public TauschRegel() {
		super();
		setBeschreibung("Figuren tauschen");
	}

	/**
	 * Validiere Zugeingabe. Der Zug muss mit einer eigenen Figur und der eines
	 * anderen Spielers gemacht werden.
	 */
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		if (zugEingabe.getAnzahlBewegungen() != 1) {
			throw new RegelVerstoss("Nur eine Bewegung möglich.");
		}

		Spieler spieler = zugEingabe.getSpieler();
		Feld start = zugEingabe.getBewegung().start;
		Feld ziel  = zugEingabe.getBewegung().ziel;

		if (start.istFrei() || ziel.istFrei()) {
			throw new RegelVerstoss("Es müssen zwei Figuren getauscht werden.");
		}

		if (start.istGeschuetzt() || ziel.istGeschuetzt()) {
			throw new RegelVerstoss("Es können keine geschützten Figuren " +
			                        "getauscht werden.");
		}

		if (!(start.istBesetztVon(spieler) ^ ziel.istBesetztVon(spieler))) {
			throw new RegelVerstoss("Es muss eine eigene Figur und " +
			                        "eine andere getauscht werden.");
		}

		Zug zug = new Zug();
		zug.fuegeHinzu(new TauschAktion(start, ziel));
		return zug;
	}

	public boolean kannZiehen(Spieler spieler) {
		for (Figur figur : spieler.getFiguren()) {
			if (!figur.getFeld().istGeschuetzt()) {
				for (Spieler spieler2 : spieler.getSpiel().getSpieler()) {
					if (spieler == spieler2) {
						continue;
					}
					for (Figur figur2 : spieler2.getFiguren()) {
						if (!figur2.getFeld().istGeschuetzt()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

    public void moeglicheZuege(Spieler spieler, Karte karte, List<ZugEingabe> moeglich) {
		for (Figur figur : spieler.getFiguren()) {
			Feld startFeld = figur.getFeld();
			if (!startFeld.istGeschuetzt()) {
				for (Spieler spieler2 : spieler.getSpiel().getSpieler()) {
					if (spieler == spieler2) {
						continue;
					}
					for (Figur figur2 : spieler2.getFiguren()) {
						Feld zielFeld = figur2.getFeld();
						if (!zielFeld.istGeschuetzt()) {
							Bewegung bewegung = new Bewegung(startFeld, zielFeld);
							ZugEingabe ze = new ZugEingabe(spieler, karte, bewegung);
							moeglich.add(ze);
						}
					}
				}
			}
		}
    }
}
