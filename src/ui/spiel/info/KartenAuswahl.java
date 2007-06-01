package ui.spiel.info;

import java.awt.Dimension;
import java.awt.Point;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class KartenAuswahl extends JLabel{

	private static final long serialVersionUID = 1L;

	public KartenAuswahl(Point p){
		removeAll();
	}
	
	public void setPosition(Point p){
		String pfad = "/ui/ressourcen/karten_auswahl.png";
		System.out.println(pfad);
		URL resource1 = KarteView.class.getResource(pfad);
		final Icon bildFigur = new ImageIcon(resource1);
		setIcon(bildFigur);

		setBounds((int) p.getX()-10, (int) p.getY()-10, bildFigur.getIconWidth(),
		          bildFigur.getIconHeight());
		setPreferredSize(new Dimension(100, 125));
		setMaximumSize(new Dimension(100, 125));
		setMinimumSize(new Dimension(100, 125));
	}
}
