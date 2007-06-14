package ui.spiel.brett;

import java.util.IdentityHashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pd.spieler.SpielerFarbe;
import ui.ressourcen.Icons;
import applikation.client.pd.Spieler;

/**
 * JPanel mit den Namen der Spieler.
 */

public class SpielerView extends JPanel implements Observer {
	IdentityHashMap<SpielerFarbe, Icon> farbeMap;

	public SpielerView(Spieler spieler) {
		farbeMap = new IdentityHashMap<SpielerFarbe, Icon>();
		farbeMap.put(SpielerFarbe.blau, Icons.SPIELER_BLAU);
		farbeMap.put(SpielerFarbe.gelb, Icons.SPIELER_GELB);
		farbeMap.put(SpielerFarbe.gruen, Icons.SPIELER_GRUEN);
		farbeMap.put(SpielerFarbe.rot, Icons.SPIELER_ROT);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		spieler.addObserver(this);
		
		JLabel name = new JLabel(spieler.getSpieler().getName());
		name.setIcon(farbeMap.get(spieler.getSpieler().getFarbe()));
		name.setFont(name.getFont().deriveFont(1)); // Fett
		add(name);
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
