package pd.regelsystem;

import pd.brett.BankFeld;
import pd.brett.Feld;
import pd.brett.LagerFeld;
import pd.spieler.Spieler;
import pd.zugsystem.Aktion;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

/**
 * Regel für das Starten mit einer Figur vom {@link LagerFeld} auf das
 * {@link BankFeld}.
 */
public class StartRegel extends Regel {
	/**
	 * Validiere ZugEingabe. Der Zug muss von einem Lagerfeld des Spielers auf
	 * sein Bankfeld gehen. Geht nicht, wenn das Bankfeld geschützt ist.
	 */
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		Spieler spieler = zugEingabe.getSpieler();
		Feld start = zugEingabe.getBewegung().start;
		Feld ziel  = zugEingabe.getBewegung().ziel;
		
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
			Spieler sp = ziel.getFigur().getSpieler();
			// TODO: Gefällt mir noch nicht, vielleicht ne Methode in Spieler?
			LagerFeld lf = sp.getSpiel().getBrett().getFreiesLagerFeldVon(sp);
			zug.fuegeHinzu(new Aktion(ziel, lf));
		} else if (ziel.istBesetztVon(spieler)) {
			// TODO: Wenn Figur geschützt: RegelVerstoss. Sonst heimschicken.
			throw new RegelVerstoss("Bankfeld ist von eigener Figur besetzt.");
		}
		
		zug.fuegeHinzu(new Aktion(start, ziel));
		
		return zug;
	}
}
