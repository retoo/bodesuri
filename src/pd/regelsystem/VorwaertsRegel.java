package pd.regelsystem;

import java.util.List;
import java.util.Vector;

import pd.brett.BankFeld;
import pd.brett.Feld;
import pd.brett.HimmelFeld;
import pd.brett.LagerFeld;
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

		if (weg.size() != schritte + 1) {
			throw new RegelVerstoss("Zug muss über " + schritte +
			                        " und nicht " + weg.size() + " Felder gehen.");
		}

		if (start instanceof BankFeld && ziel instanceof HimmelFeld &&
		    start.istGeschuetzt()) {
			throw new RegelVerstoss("Es muss zuerst eine Runde gemacht werden.");
		}

		if (ziel instanceof HimmelFeld) {
			HimmelFeld himmel = (HimmelFeld) ziel;
			if (himmel.getSpieler() != spieler) {
				throw new RegelVerstoss("Es muss in den eigenen Himmel " +
				                        "gezogen werden.");
			}
		}

		for (Feld feld : weg) {
			if (feld == start) continue;

			if (feld instanceof BankFeld && feld != ziel &&
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

		if (start instanceof HimmelFeld && !(ziel instanceof HimmelFeld)) {
			throw new RegelVerstoss(
				"Im Himmel kann nur noch vorwärts gefahren werden.");
		} else if (start instanceof LagerFeld) {
			throw new RegelVerstoss(
				"Es darf nur mit dem König oder dem Ass gestartet werden.");
		} else if (ziel instanceof LagerFeld) {
			throw new RegelVerstoss(
				"Es gibt nur eine Art, ins Lager zurückzukehren...");
		}

		Feld feld = start;
		while (feld != ziel) {
			weg.add(feld);
			if (feld instanceof BankFeld && ziel instanceof HimmelFeld) {
				feld = ((BankFeld) feld).getHimmel();
			} else {
				feld = feld.getNaechstes();
			}
		}
		weg.add(feld);
		return weg;
	}
}
