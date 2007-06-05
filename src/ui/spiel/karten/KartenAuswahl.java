package ui.spiel.karten;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JLabel;

import ui.ressourcen.Icons;

public class KartenAuswahl extends JLabel{
	public KartenAuswahl(Point p){
		removeAll();
	}

	public void setPosition(Point p){
		setIcon(Icons.KARTEN_AUSWAHL);
		setBounds((int) p.getX()-10, (int) p.getY()-10, Icons.KARTEN_AUSWAHL.getIconWidth(),
				Icons.KARTEN_AUSWAHL.getIconHeight());
		setPreferredSize(new Dimension(100, 125));
		setMaximumSize(new Dimension(100, 125));
		setMinimumSize(new Dimension(100, 125));
	}
}
