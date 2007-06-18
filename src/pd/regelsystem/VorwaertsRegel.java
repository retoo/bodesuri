package pd.regelsystem;

import java.util.List;
import java.util.Vector;

import pd.brett.BankFeld;
import pd.brett.Feld;
import pd.brett.HimmelFeld;
import pd.karten.Karte;
import pd.spieler.Figur;
import pd.spieler.Spieler;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.HeimschickAktion;
import pd.zugsystem.Weg;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

/**
 * Regel für normales Vorwärtsfahren, mit Heimschicken von Figur auf Zielfeld.
 */
public class VorwaertsRegel extends Regel {
	protected int schritte;

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

		pruefeBewegung(bewegung, spieler);

		Weg weg = bewegung.getWeg();
		pruefeWegRichtung(weg);
		pruefeWegLaenge(weg);

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
			zug.fuegeHinzu(new HeimschickAktion(ziel));
		}

		zug.fuegeHinzu(new Aktion(start, ziel));

		return zug;
	}

	protected void pruefeBewegung(Bewegung bewegung, Spieler spieler) throws RegelVerstoss {
		Feld start = bewegung.start;
		Feld ziel  = bewegung.ziel;

		if (start.istHimmel() && !ziel.istHimmel()) {
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

		if (ziel.istHimmel()) {
			HimmelFeld himmel = (HimmelFeld) ziel;
			if (himmel.getSpieler() != spieler) {
				throw new RegelVerstoss("Es muss in den eigenen Himmel " +
				"gezogen werden.");
			}
		}

		if (start.istBank() && ziel.istHimmel() && start.istGeschuetzt()) {
			throw new RegelVerstoss("Es muss zuerst eine Runde gemacht werden.");
		}
	}

	protected void pruefeWegRichtung(Weg weg) throws RegelVerstoss {
		if (weg.istRueckwaerts()) {
			throw new RegelVerstoss("Es kann nicht rückwärts gefahren werden.");
		}
	}

	protected void pruefeWegLaenge(Weg weg) throws RegelVerstoss {
		int wegLaenge = weg.size() - 1;
		if (wegLaenge != schritte) {
			throw new RegelVerstoss("Zug muss über " + schritte +
			                        " und nicht " + wegLaenge + " Felder gehen.");
		}
	}

	public boolean kannZiehen(Spieler spieler) {
		for (Figur figur : spieler.getFiguren()) {
			Feld start = figur.getFeld();

			Feld ziel = getZiel(start, schritte, false);
			if (ziel != null && istZugMoeglich(spieler, start, ziel)) {
				return true;
			}

			ziel = getZiel(start, schritte, true);
			if (ziel != null && istZugMoeglich(spieler, start, ziel)) {
				return true;
			}
		}
		return false;
	}

	public void moeglicheZuege(Spieler spieler, Karte karte, List<ZugEingabe> moeglich) {
		for (Figur figur : spieler.getFiguren()) {
			Feld start = figur.getFeld();

			Feld ziel = getZiel(start, schritte, false);
			if (ziel != null && istZugMoeglich(spieler, start, ziel)) {
				Bewegung bewegung = new Bewegung(start, ziel);
				moeglich.add(new ZugEingabe(spieler, karte, bewegung));
			}

			ziel = getZiel(start, schritte, true);
			if (ziel != null && istZugMoeglich(spieler, start, ziel)) {
				Bewegung bewegung = new Bewegung(start, ziel);
				moeglich.add(new ZugEingabe(spieler, karte, bewegung));
			}
		}
	}

	protected boolean istZugMoeglich(Spieler spieler, Feld start, Feld ziel) {
		List<Bewegung> bewegungen = new Vector<Bewegung>();
		bewegungen.add(new Bewegung(start, ziel));
		return istZugMoeglich(spieler, bewegungen);
	}

	protected boolean istZugMoeglich(Spieler spieler, List<Bewegung> bewegungen) {
		try {
			ZugEingabe ze = new ZugEingabe(spieler, null, bewegungen);
			validiere(ze);
			return true;
		} catch (RegelVerstoss rv) {
			return false;
		}
	}

	private Feld getZiel(Feld start, int schritte, boolean mitHimmel) {
		Feld feld = start;
		for (int i = 0; i < schritte; ++i) {
			if (mitHimmel && feld.istBank()) {
				feld = ((BankFeld) feld).getHimmel();
			} else {
				feld = feld.getNaechstes();
			}
			if (feld == null) {
				return null;
			}
		}
		return feld;
	}
}
