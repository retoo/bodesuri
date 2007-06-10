package ui.spiel.brett;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JLabel;

import pd.spieler.Figur;
import ui.ressourcen.Icons;

/**
 * JLabel, wird zur Darstellung der Spielfiguren verwendet.
 */
public class Figur2d extends JLabel implements Observer {
	private Figur figur;
	private BrettView brett;

	public Figur2d(Figur figur, BrettView brett) {
		super(Icons.FIGUR_BLAU);
		this.figur = figur;
		this.brett = brett;
		setzeAuf(brett.getFeld2d(figur.getFeld()));
		figur.addObserver(this);
	}

	/**
	 * Die Spielfigur wird auf das ausgewählte Feld gesetzt.
	 *
	 * @param ziel Zielfeld
	 */
	public void setzeAuf(Feld2d ziel) {
		setBounds(ziel.getX(), ziel.getY(), Icons.FIGUR_BLAU.getIconWidth(),
				Icons.FIGUR_BLAU.getIconHeight());
	}

	/**
     * OBSERVER-PATTERN: OBSERVER
     * überschreibt <code>update()</code> Methode des Observer.
     *
     * @param o Zu observierendes Objekt
     * @param arg Objekt
     */
	public void update(Observable o, Object arg) {
		setzeAuf(brett.getFeld2d(figur.getFeld()));
		setzeNichtAusgewaehlt();
	}

	public void setzeAusgewaehlt() {
		Icon bildFigur = Icons.FIGUR_BLAU_AUSWAHL;
		setIcon(bildFigur);
		updateUI();
	}
	
	public void setzeNichtAusgewaehlt(){
		Icon bildFigur = Icons.FIGUR_BLAU;
		setIcon(bildFigur);
		updateUI();
	}
}
