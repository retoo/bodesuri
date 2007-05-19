package pd.regelsystem;

import pd.brett.BankFeld;
import pd.brett.Feld;
import pd.brett.LagerFeld;
import pd.spieler.Spieler;
import pd.zugsystem.Aktion;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class StartRegel extends Regel {
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		Spieler spieler = zugEingabe.getSpieler();
		Feld start = zugEingabe.getBewegung().getStart();
		Feld ziel  = zugEingabe.getBewegung().getZiel();
		
		if (!(start instanceof LagerFeld)) {
			throw new RegelVerstoss("Der Start muss ein Lagerfeld sein.");
		}
		LagerFeld lager = (LagerFeld)start;
		
		if (!lager.istBesetztVon(spieler)) {
			throw new RegelVerstoss("Auf Lagerfeld muss Figur stehen.");
		}
		
		if (!(ziel instanceof BankFeld)) {
			throw new RegelVerstoss("Das Ziel muss das Bankfeld sein.");
		}
		BankFeld bank = (BankFeld)ziel;
		
		if (bank.getSpieler() != spieler) {
			throw new RegelVerstoss("Das Bankfeld muss dem Spieler gehören.");
		}
		
		Zug zug = new Zug();
		
		if (ziel.istBesetzt() && !ziel.istBesetztVon(spieler)) {
			// TODO: Heimschicken
			// TODO: Was passiert, wenn von eigener Figur besetzt?
		}
		
		zug.fuegeHinzu(new Aktion(start, ziel));
		
		return zug;
	}
}
