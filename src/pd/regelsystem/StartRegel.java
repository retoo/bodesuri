package pd.regelsystem;

import pd.karten.Karte;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.regelsystem.verstoesse.Verstoesse;
import pd.spiel.brett.BankFeld;
import pd.spiel.brett.Brett;
import pd.spiel.brett.Feld;
import pd.spiel.brett.LagerFeld;
import pd.spiel.spieler.Figur;
import pd.spiel.spieler.Spieler;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.HeimschickAktion;
import pd.zugsystem.Zug;

/**
 * Regel für das Starten mit einer Figur vom {@link LagerFeld} auf das
 * {@link BankFeld}.
 */
public class StartRegel extends Regel {
	public StartRegel() {
		super();
		setBeschreibung("Starten");
	}

	/**
	 * Validiere ZugEingabe. Der Zug muss von einem Lagerfeld des Spielers auf
	 * sein Bankfeld gehen. Geht nicht, wenn das Bankfeld geschützt ist.
	 */
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		if (zugEingabe.getAnzahlBewegungen() != 1) {
			throw new Verstoesse.AnzahlBewegungen();
		}

		Spieler spieler = zugEingabe.getBetroffenerSpieler();
		Feld start = zugEingabe.getBewegung().start;
		Feld ziel  = zugEingabe.getBewegung().ziel;

		if (!start.istLager()) {
			throw new Verstoesse.NurMitFigurVomLagerStarten();
		}
		LagerFeld lager = (LagerFeld)start;

		if (!lager.istBesetztVon(spieler)) {
			throw new Verstoesse.NurMitFigurVomLagerStarten();
		}

		if (!ziel.istBank()) {
			throw new Verstoesse.NurAufEigeneBankStarten();
		}
		BankFeld bank = (BankFeld)ziel;

		if (!bank.istVon(spieler)) {
			throw new Verstoesse.NurAufEigeneBankStarten();
		}

		Zug zug = new Zug();

		if (ziel.istGeschuetzt()) {
			throw new Verstoesse.AufGeschuetzteFigurStarten();
		} else if (ziel.istBesetzt()) {
			zug.fuegeHinzu(new HeimschickAktion(ziel));
		}

		zug.fuegeHinzu(new Aktion(start, ziel));

		return zug;
	}

	protected void liefereZugEingaben(Spieler spieler, Karte karte,
	                               ZugEingabeAbnehmer abnehmer) {
		Brett brett = spieler.getSpiel().getBrett();
		for (Figur figur : spieler.getZiehbareFiguren()) {
			if (figur.getFeld().istLager()) {
				Feld ziel = brett.getBankFeldVon(figur.getSpieler());
				if (!ziel.istGeschuetzt()) {
					Feld start = figur.getFeld();
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
