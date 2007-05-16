package ui.brett;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import pd.brett.Feld;

public class Figur2d extends JLabel implements Observer{
	private static final long serialVersionUID = 1L;
	static final private ImageIcon bildFigur = new ImageIcon(Feld2d.class.getResource("/ui/brett/figur.png"));

	public Figur2d(Feld2d feld) {
		super(bildFigur);
		setzeAuf(feld);
	}
	
	public void setzeAuf(Feld2d ziel) {
		setBounds(ziel.getX(), ziel.getY(), bildFigur.getIconWidth(),
		          bildFigur.getIconHeight());
	}

	public void update(Observable o, Object arg) {
	    if (arg instanceof Feld) {
	    	//setzeAuf((Feld)arg);
	    }
    }
}
