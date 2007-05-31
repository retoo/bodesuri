package ui.spiel.info;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import applikation.client.BodesuriClient;

/**
 * JPanel, dient zur auflistung der einzelnen Spieler.
 */

public class SpielerView extends JPanel {

	private static final long serialVersionUID = 1L;

	public SpielerView(BodesuriClient automat) {
		TitledBorder titel = new TitledBorder("Spieler");
		// titel.setTitleFont(titel.getTitleFont().deriveFont(Font.BOLD));
		setBorder(titel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		
		/**
		 * @fixme: Funktioniert nur mit 4 Spielern. (Exception)
		 */
		this.add(new JLabel(automat.spiel.getSpieler().get(0).getName()));
		this.add(new JLabel(automat.spiel.getSpieler().get(1).getName()));
		this.add(new JLabel(automat.spiel.getSpieler().get(2).getName()));
		this.add(new JLabel(automat.spiel.getSpieler().get(3).getName()));
	}
}
