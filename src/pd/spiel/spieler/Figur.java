package pd.spiel.spieler;

import java.util.Observable;

import pd.spiel.brett.Feld;

/**
 * Figur, von der jeder {@link Spieler} 4 hat und die auf einem {@link Feld}
 * steht.
 */
public class Figur extends Observable {
	private Spieler spieler;
	private Feld feld;

	/**
	 * @param spieler Spieler, dem die Figur gehört
	 */
	public Figur(Spieler spieler) {
		this.spieler = spieler;
	}

	/**
	 * @return Spieler, dem die Figur gehört
	 */
	public Spieler getSpieler() {
		return spieler;
	}

	/**
	 * @param spieler Spieler, der überprüft wird
	 * @return true, wenn Figur dem Spieler gehört
	 */
	public boolean istVon(Spieler spieler) {
		return this.spieler == spieler;
	}

	/**
	 * @return Feld, auf dem die Figur steht
	 */
	public Feld getFeld() {
		return feld;
	}

	/**
	 * Figur benachrichtigen, dass sie auf ein neues Feld bewegt wurde.
	 * 
	 * @param ziel Feld, auf dem sie neu steht
	 */
	public void versetzeAuf(Feld ziel) {
		this.feld = ziel;
		setChanged();
		notifyObservers();
	}
}
