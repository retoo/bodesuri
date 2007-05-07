/**
 * @(#) BrettView.java
 */

package ui.brett;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class BrettView extends JPanel {

	private static final long serialVersionUID = 1L;

	public BrettView() {
		this.add(new JLabel("Hier kommt das Spielbrett hin..."));
		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		TitledBorder titel = new TitledBorder("Spieler");
		setBorder(titel);
	}
}
