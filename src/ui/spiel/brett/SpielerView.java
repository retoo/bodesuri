package ui.spiel.brett;

import java.awt.Color;
import java.awt.Point;
import java.util.IdentityHashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pd.spieler.SpielerFarbe;

import applikation.client.pd.Spieler;

/**
 * JPanel mit den Namen der Spieler.
 */

public class SpielerView extends JPanel implements Observer {
	IdentityHashMap<SpielerFarbe, Color> farbeMap;

	public SpielerView(Spieler spieler, Point point) {
		farbeMap = new IdentityHashMap<SpielerFarbe, Color>();
		farbeMap.put(SpielerFarbe.blau, Color.BLUE);
		farbeMap.put(SpielerFarbe.gelb, Color.YELLOW);
		farbeMap.put(SpielerFarbe.gruen, Color.GREEN);
		farbeMap.put(SpielerFarbe.rot, Color.RED);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		spieler.addObserver(this);

		JLabel name = new JLabel(spieler.getSpieler().getName());
		name.setForeground(farbeMap.get(spieler.spieler.getFarbe()));
		name.setFont(name.getFont().deriveFont(1)); // Fett
		this.add(name);
		setBounds((int) point.getX(), (int) point.getY(), 150, 40);
	}

	public void update(Observable arg0, Object arg) {
		// TODO Änderungen wenn der Spieler am Zug ist oder nicht...
		if ((Boolean) arg) {
			// Am Zug.
		} else {
			// Nicht mehr am Zug.
		}
	}
}
