package ui.geteiltes;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.ressourcen.Icons;
import applikation.client.pd.Spieler;

/**
 * JPanel mit den Namen der Spieler.
 */

public class SpielerView extends JPanel implements Observer {
	private JLabel name;

	public SpielerView(Spieler spieler) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setOpaque(false);

		spieler.addObserver(this);

		this.name = new JLabel(spieler.getSpieler().getName());
		name.setIcon(Icons.getSpielerIcon(spieler.getSpieler().getFarbe()));
		name.setFont(name.getFont().deriveFont(Font.BOLD));
		add(name);
	}

	public void update(Observable observable, Object arg) {
		/* TODO: Unchecked cast */
		Spieler spieler = (Spieler) observable;
		if (spieler.getAmZug()) {
			this.name.setForeground(Color.WHITE);
		} else if (spieler.hatAufgegeben()) {
			this.name.setForeground(Color.GRAY);
		} else {
			this.name.setForeground(Color.BLACK);
		}
	}

	public void setSpielerName(String name) {
		this.name.setText(name);
	}
}
