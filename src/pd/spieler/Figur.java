package pd.spieler;

import java.util.Observable;

import pd.brett.Feld;

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
