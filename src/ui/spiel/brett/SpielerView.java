package ui.spiel.brett;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import applikation.client.controller.Spieler;

/**
 * JPanel mit den Namen der Spieler.
 */

public class SpielerView extends JPanel implements Observer {
	public SpielerView(Spieler spieler, Point point) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		spieler.addObserver(this);

		JLabel name = new JLabel(spieler.getSpieler().getName());
		// name.setForeground(new Color()); spieler.getFarbe();
		name.setFont(name.getFont().deriveFont(0)); // Fett
		this.add(name);
		setBounds((int) point.getX(), (int) point.getY(), 150, 40);
	}

	public void update(Observable arg0, Object arg) {
		// TODO Änderungen wenn der Spieler am Zug ist oder nicht...
		if ((Boolean) arg) {
			//Am Zug.
		} else {
			//Nicht mehr am Zug.
		}
	}
}
