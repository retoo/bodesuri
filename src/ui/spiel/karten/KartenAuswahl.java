package ui.spiel.karten;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.Icon;
import javax.swing.JLabel;

import ui.ressourcen.Icons;

public class KartenAuswahl extends JLabel {
	public void setPosition(Point pos) {
		Icon icon = Icons.KARTEN_AUSWAHL;
		setIcon(icon);
		setBounds(pos.x - 10, pos.y - 10,
		          icon.getIconWidth(), icon.getIconHeight());
		setPreferredSize(new Dimension(100, 125));
		setMaximumSize(new Dimension(100, 125));
		setMinimumSize(new Dimension(100, 125));
	}
}
