package ui.spiel.brett;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import pd.brett.Feld;

/**
 * JLabel, wird zur Darstellung der Spielfiguren verwendet.
 */
public class Figur2d extends JLabel implements Observer {
	private static final long serialVersionUID = 1L;
	static final private ImageIcon bildFigur = new ImageIcon(Feld2d.class.getResource("/ui/ressourcen/figur.png"));
	private BrettView brett;

	public Figur2d(Feld2d feld, BrettView brett) {
		super(bildFigur);
		setzeAuf(feld);
		this.brett = brett;
	}

	/**
	 * Die Spielfigur wird auf das ausgewählte Feld gesetzt.
	 * 
	 * @param ziel Zielfeld
	 */
	public void setzeAuf(Feld2d ziel) {
		setBounds(ziel.getX(), ziel.getY(), bildFigur.getIconWidth(),
		          bildFigur.getIconHeight());
	}

	/**
     * OBSERVER-PATTERN: OBSERVER
     * überschreibt <code>update()</code> Methode des Observer.
     *
     * @param observable Zu observierendes Objekt
     * @param object Objekt
     */
	public void update(Observable o, Object arg) {
		if (arg instanceof Feld) {
			setzeAuf(brett.getFeld2d((Feld)arg));
		}
	}
}
