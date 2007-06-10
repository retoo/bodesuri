package pd.regelsystem;

import java.util.List;
import java.util.Vector;

import pd.brett.BankFeld;
import pd.brett.Feld;
import pd.brett.HimmelFeld;
import pd.brett.SpielerFeld;
import pd.spieler.Spieler;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

/**
 * Regel für normales Vorwärtsfahren, mit Heimschicken von Figur auf Zielfeld.
 */
public class VorwaertsRegel extends Regel {
	private int schritte;

	/**
	 * @param schritte Anzahl Schritte, die die Regel überprüfen soll
	 */
	public VorwaertsRegel(int schritte) {
		this.schritte = schritte;
		setBeschreibung(schritte + " vorwärts");
	}

	/**
	 * Validiere Zugeingabe. Der Zug muss mit eigener Figur ausgeführt werden
	 * und auf ein ungeschütztes Zielfeld gehen. Wenn das Zielfeld von einer
	 * Figur besetzt ist, wird diese heimgeschickt.
	 */
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		if (zugEingabe.getAnzahlBewegungen() != 1) {
			throw new RegelVerstoss("Nur eine Bewegung möglich.");
		}

		Bewegung bewegung = zugEingabe.getBewegung();
		Feld start = bewegung.start;
		Feld ziel  = bewegung.ziel;
		Spieler spieler = zugEingabe.getSpieler();

		if (!start.istBesetztVon(spieler)) {
			throw new RegelVerstoss("Zug muss mit eigener Figur begonnen " +
			                        "werden.");
		}

		List<Feld> weg = getWeg(bewegung);
		int wegLaenge = weg.size() - 1;
		if (wegLaenge != schritte) {
			throw new RegelVerstoss("Zug muss über " + schritte +
			                        " und nicht " + wegLaenge + " Felder gehen.");
		}

		if (start.istBank() && ziel.istHimmel() &&
		    start.istGeschuetzt()) {
			throw new RegelVerstoss("Es muss zuerst eine Runde gemacht werden.");
		}

		if (ziel.istHimmel()) {
			HimmelFeld himmel = (HimmelFeld) ziel;
			if (himmel.getSpieler() != spieler) {
				throw new RegelVerstoss("Es muss in den eigenen Himmel " +
				                        "gezogen werden.");
			}
		}

		for (Feld feld : weg) {
			if (feld == start) continue;

			if (feld.istBank() && feld != ziel &&
			    ((BankFeld)feld).istBesetztVonBesitzer()) {
				throw new RegelVerstoss("Es kann nicht über eine Figur " +
				                        "auf ihrem Bankfeld gezogen werden.");
			}

			if (feld.istGeschuetzt()) {
				throw new RegelVerstoss("Es kann nicht auf oder über ein " +
				                        "geschütztes Feld gezogen werden.");
			}
		}

		Zug zug = new Zug();

		if (ziel.istBesetzt()) {
			zug.fuegeHinzu(heimschickAktion(ziel, ziel.getFigur().getSpieler()));
		}

		zug.fuegeHinzu(new Aktion(start, ziel));

		return zug;
	}

	protected List<Feld> getWeg(Bewegung bewegung) throws RegelVerstoss {
		Vector<Feld> weg = new Vector<Feld>();

		Feld start = bewegung.start;
		Feld ziel  = bewegung.ziel;

		if (start.istHimmel() && !(ziel.istHimmel())) {
			throw new RegelVerstoss(
				"Im Himmel kann nur noch vorwärts gefahren werden.");
		} else if (start.istLager() && ziel.istLager()) {
			throw new RegelVerstoss("Im Lager kann nicht gefahren werden.");
		} else if (start.istLager()) {
			throw new RegelVerstoss(
				"Es darf nur mit dem König oder dem Ass gestartet werden.");
		} else if (ziel.istLager()) {
			throw new RegelVerstoss(
				"Es gibt nur eine Art, ins Lager zurückzukehren...");
		}

		Feld feld = start;
		while (feld != ziel) {
			weg.add(feld);
			if (feld.istBank() && ziel.istHimmel() 
				&& ((SpielerFeld) feld).getSpieler() ==
				   ((SpielerFeld) ziel).getSpieler())
			{
				feld = ((BankFeld) feld).getHimmel();
			} else {
				feld = feld.getNaechstes();
			}
		}
		weg.add(feld);
		return weg;
	}
}
