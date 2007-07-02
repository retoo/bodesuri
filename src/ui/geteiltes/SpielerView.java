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
	private Spieler spieler;

	public SpielerView(Spieler spieler) {
		this.spieler = spieler;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setOpaque(false);

		this.name = new JLabel(spieler.getSpieler().getName());
		name.setIcon(Icons.getSpielerIcon(spieler.getSpieler().getFarbe()));
		name.setFont(name.getFont().deriveFont(Font.BOLD));
		add(name);

		spieler.addObserver(this);
	}

	/**
	 * OBSERVER-PATTERN: OBSERVER 
	 * Überschreibt <code>update()</code> Methode des Observer.
	 * 
	 * @param o Zu observierendes Objekt
	 * @param arg Objekt
	 */
	public void update(Observable observable, Object arg) {
		if (name.getText() != spieler.getName()) {
			name.setText(spieler.getName());
		}

		if (spieler.amZug()) {
			this.name.setForeground(Color.WHITE);
		} else if (spieler.hatAufgegeben()) {
			this.name.setForeground(Color.BLACK);
		} else {
			this.name.setForeground(Color.BLACK);
		}
	}

	/**
	 * OBSERVER-PATTERN: OBSERVER 
	 * Überschreibt <code>removeNotify</code> Methode des Observer.
	 * Es wird auch dafür gesorgt, dass alle zugehörigen Objekt sich vom
	 * Observer abmelden.
	 * 
	 * @param o Zu observierendes Objekt
	 * @param arg Objekt
	 */
	public void removeNotify() {
		spieler.deleteObserver(this);
		super.removeNotify();
	}
}
