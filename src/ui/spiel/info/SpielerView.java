package ui.spiel.info;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import pd.spieler.Spieler;
import applikation.client.Controller;



/**
 * JPanel, dient zur auflistung der einzelnen Spieler.
 */

public class SpielerView extends JPanel {
	public SpielerView(Controller controller) {
		TitledBorder titel = new TitledBorder("Spieler");
		// titel.setTitleFont(titel.getTitleFont().deriveFont(Font.BOLD));
		setBorder(titel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		/**
		 * @fixme: Funktioniert nur mit 4 Spielern. (Exception)
		 */
		for (Spieler spieler : controller.getSpiel().getSpieler()) {
			this.add(new JLabel(spieler.getName()));
		}
	}
}
