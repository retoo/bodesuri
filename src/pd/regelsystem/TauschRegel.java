package pd.regelsystem;

import pd.karten.Karte;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.regelsystem.verstoesse.Verstoesse;
import pd.spiel.brett.Feld;
import pd.spiel.spieler.Figur;
import pd.spiel.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.TauschAktion;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;
import pd.zugsystem.ZugEingabeAbnehmer;

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
			throw new Verstoesse.AnzahlBewegungen();
		}

		Spieler spieler = zugEingabe.getBetroffenerSpieler();
		Feld start = zugEingabe.getBewegung().start;
		Feld ziel  = zugEingabe.getBewegung().ziel;

		if (start.istFrei() || ziel.istFrei()) {
			throw new Verstoesse.FalscheFigurenTauschen();
		}

		if (start.istGeschuetzt() || ziel.istGeschuetzt()) {
			throw new Verstoesse.GeschuetzteTauschen();
		}

		if (!(start.istBesetztVon(spieler) ^ ziel.istBesetztVon(spieler))) {
			throw new Verstoesse.FalscheFigurenTauschen();
		}

		Zug zug = new Zug();
		zug.fuegeHinzu(new TauschAktion(start, ziel));
		return zug;
	}

	protected void liefereZugEingaben(Spieler spieler, Karte karte,
	                               ZugEingabeAbnehmer abnehmer) {
		for (Figur figur : spieler.getZiehbareFiguren()) {
			Feld start = figur.getFeld();
			if (!start.istGeschuetzt()) {
				for (Spieler spieler2 : spieler.getSpiel().getSpieler()) {
					if (spieler2 == figur.getSpieler()) {
						continue;
					}
					for (Figur figur2 : spieler2.getFiguren()) {
						Feld ziel = figur2.getFeld();
						if (!ziel.istGeschuetzt()) {
							Bewegung bewegung = new Bewegung(start, ziel);
							ZugEingabe ze = new ZugEingabe(spieler, karte, bewegung);
							boolean abbrechen = abnehmer.nehmeEntgegen(ze);
							if (abbrechen) {
								return;
							}
						}
					}
				}
			}
		}
	}
}
