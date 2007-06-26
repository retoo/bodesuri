package pd.regelsystem;

import pd.brett.BankFeld;
import pd.brett.Brett;
import pd.brett.Feld;
import pd.brett.LagerFeld;
import pd.karten.Karte;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.spieler.Figur;
import pd.spieler.Spieler;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.HeimschickAktion;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;
import pd.zugsystem.ZugEingabeAbnehmer;

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
			throw new RegelVerstoss("Nur eine Bewegung möglich.");
		}

		Spieler spieler = zugEingabe.getBetroffenerSpieler();
		Feld start = zugEingabe.getBewegung().start;
		Feld ziel  = zugEingabe.getBewegung().ziel;

		if (!start.istLager()) {
			throw new RegelVerstoss("Der Start muss ein Lagerfeld sein.");
		}
		LagerFeld lager = (LagerFeld)start;

		if (!lager.istBesetztVon(spieler)) {
			throw new RegelVerstoss("Auf Lagerfeld muss Figur stehen.");
		}

		if (!ziel.istBank()) {
			throw new RegelVerstoss("Das Ziel muss das Bankfeld sein.");
		}
		BankFeld bank = (BankFeld)ziel;

		if (!bank.istVon(spieler)) {
			throw new RegelVerstoss("Das Bankfeld muss dem Spieler gehören.");
		}

		Zug zug = new Zug();

		if (ziel.istGeschuetzt()) {
			throw new RegelVerstoss("Bankfeld ist geschützt.");
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
