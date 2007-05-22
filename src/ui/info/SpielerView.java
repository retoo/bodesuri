package ui.info;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class SpielerView extends JPanel {

	private static final long serialVersionUID = 1L;

	public SpielerView() {
		TitledBorder titel = new TitledBorder("Spieler");
		// titel.setTitleFont(titel.getTitleFont().deriveFont(Font.BOLD));
		setBorder(titel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.add(new JLabel("Spieler 1"));
		this.add(new JLabel("Spieler 2"));
		this.add(new JLabel("Spieler 3"));
		this.add(new JLabel("Spieler 4"));
	}
}
