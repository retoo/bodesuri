package pd.zugsystem;

import java.io.Serializable;

import pd.spiel.brett.BankFeld;
import pd.spiel.brett.Brett;
import pd.spiel.brett.Feld;
import pd.spiel.brett.SpielerFeld;

/**
 * Hilfsklasse für eine Bewegung, die ein Start und ein Ziel hat.
 */
public class Bewegung implements Serializable {
	/** Start der Bewegung. */
	public final Feld start;
	/** Ziel der Bewegung. */
	public final Feld ziel;

	public Bewegung(Feld start, Feld ziel) {
		this.start = start;
		this.ziel = ziel;
	}

	public String toString() {
		return "von " + start + " nach " + ziel;
	}
	
	public Weg getWeg() {
		boolean vorwaerts;
		
		if ((start.istRing() && ziel.istRing())) {
			int startNr = start.getNummer();
			int zielNr  =  ziel.getNummer();
			int anzahlFelder = Brett.ANZAHL_FELDER;
			int startBisZiel = (zielNr - startNr + anzahlFelder) % anzahlFelder;
			int zielBisStart = (startNr - zielNr + anzahlFelder) % anzahlFelder;
			vorwaerts = (startBisZiel <= zielBisStart);
		} else if ((start.istRing()   && ziel.istHimmel()) ||
		           (start.istHimmel() && ziel.istHimmel())) {
			vorwaerts = true;
		} else {
			return null;
		}
		
		Weg weg = new Weg(vorwaerts);
		
		Feld feld = start;
		while (feld != ziel) {
			weg.add(feld);
			
			if (vorwaerts) {
				if (feld.istBank() && ziel.istHimmel()
				    && ((SpielerFeld) feld).getSpieler() ==
				       ((SpielerFeld) ziel).getSpieler())
				{
					feld = ((BankFeld) feld).getHimmel();
				} else {
					feld = feld.getNaechstes();
				}
			} else {
				feld = feld.getVorheriges();
			}
			
			if (feld == null) {
				return null;
			}
		}
		weg.add(feld);
		
		return weg;
	}
}
