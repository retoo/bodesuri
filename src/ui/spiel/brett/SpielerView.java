package ui.spiel.brett;

import java.awt.Color;
import java.awt.Font;
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
	private IdentityHashMap<SpielerFarbe, Icon> farbeMap;
	private JLabel name;

	public SpielerView(Spieler spieler) {
		farbeMap = new IdentityHashMap<SpielerFarbe, Icon>();
		farbeMap.put(SpielerFarbe.blau, Icons.SPIELER_BLAU);
		farbeMap.put(SpielerFarbe.gelb, Icons.SPIELER_GELB);
		farbeMap.put(SpielerFarbe.gruen, Icons.SPIELER_GRUEN);
		farbeMap.put(SpielerFarbe.rot, Icons.SPIELER_ROT);

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setOpaque(false);

		spieler.addObserver(this);

		this.name = new JLabel(spieler.getSpieler().getName());
		name.setIcon(farbeMap.get(spieler.getSpieler().getFarbe()));
		name.setFont(name.getFont().deriveFont(Font.BOLD));
		add(name);
	}

	public void update(Observable observable, Object arg) {
		Spieler spieler = (Spieler) observable;
		if (spieler.getAmZug()) {
			this.name.setForeground(Color.WHITE);
		} else if (spieler.getHatAufgebeben()) {
			// TODO: Danilo: Evtl. noch schöner visualisieren. Grau ist nicht
			// sehr intuitiv --Philippe
			this.name.setForeground(Color.GRAY);
		} else {
			this.name.setForeground(Color.BLACK);
		}
	}
}
