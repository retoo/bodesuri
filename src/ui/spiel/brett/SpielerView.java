package ui.spiel.brett;

import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.GUIController;
import applikation.client.controller.SpielerFarbe;

/**
 * JPanel, dient zur auflistung der einzelnen Spieler.
 */

public class SpielerView extends JPanel {
	public SpielerView(GUIController controller, String spielerName, SpielerFarbe farbe, Point point) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		JLabel name = new JLabel(spielerName);
//		name.setForeground(new Color(farbe.getRed(), farbe.getGreen(), farbe.getBlue()));
		name.setFont(new java.awt.Font("Tahoma", 0, 11));
		this.add(name);
		setBounds((int)point.getX(), (int)point.getY(), 150, 40);
	}
}
