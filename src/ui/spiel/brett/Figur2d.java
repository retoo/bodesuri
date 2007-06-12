package ui.spiel.brett;

import java.util.IdentityHashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JLabel;

import pd.spieler.Figur;
import pd.spieler.SpielerFarbe;
import ui.ressourcen.Icons;
import ui.spiel.brett.felder.Feld2d;

/**
 * JLabel, wird zur Darstellung der Spielfiguren verwendet.
 */
public class Figur2d extends JLabel implements Observer {
	private Figur figur;
	private BrettView brett;
	private IdentityHashMap<SpielerFarbe, Icon> farbeMap;

	public Figur2d(Figur figur, IdentityHashMap<SpielerFarbe, Icon> farbeMap,
			BrettView brett) {
		super(farbeMap.get(figur.getSpieler().getFarbe()));
		this.figur = figur;
		this.brett = brett;
		this.farbeMap = farbeMap;
		setzeAuf(brett.getFeld2d(figur.getFeld()));
		figur.addObserver(this);
	}

	/**
	 * Die Spielfigur wird auf das ausgewählte Feld gesetzt.
	 * 
	 * @param ziel
	 *            Zielfeld
	 */
	public void setzeAuf(Feld2d ziel) {
		setBounds(getPosX(ziel), getPosY(ziel), farbeMap.get(
				figur.getSpieler().getFarbe()).getIconWidth(), farbeMap.get(
				figur.getSpieler().getFarbe()).getIconHeight());
	}

	public void update(Observable o, Object arg) {
		setzeAuf(brett.getFeld2d(figur.getFeld()));
		// brett.getFeld2d(figur.getFeld()).setAusgewaehlt(false);
	}

	public int getPosX(Feld2d ziel) {
		return ziel.getPointX() - (Icons.FIGUR_BLAU.getIconWidth() / 2) + 2;
	}

	public int getPosY(Feld2d ziel) {
		return ziel.getPointY()
				- (Icons.FIGUR_BLAU.getIconHeight() - (Icons.FELD_NORMAL
						.getIconHeight() / 2)) + 7;
	}
}
