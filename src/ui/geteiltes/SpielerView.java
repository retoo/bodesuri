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

	public void update(Observable o, Object arg) {
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
	 * Sorge dafür, dass der Observer ausgetragen wird.
	 */
	public void removeNotify() {
		spieler.deleteObserver(this);
		super.removeNotify();
	}
}
