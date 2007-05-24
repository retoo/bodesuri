package ui.spiel.brett;

import java.awt.Point;

import javax.swing.ImageIcon;

import pd.brett.Feld;

public class NormalesFeld2d extends Feld2d {
	private static final long serialVersionUID = 1L;
	static final private ImageIcon icon  = new ImageIcon(Feld2d.class.getResource("/ui/ressourcen/feld.png"));
	
	public NormalesFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter) {
		super(p, feld, mouseAdapter, icon);
	}

}
