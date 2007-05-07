package ui.info;

import java.awt.Component;
import java.awt.Dimension;

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
		setAlignmentX(Component.RIGHT_ALIGNMENT);

		this.add(new JLabel("Spieler 1"));
		this.add(new JLabel("Spieler 2"));
		this.add(new JLabel("Spieler 3"));
		this.add(new JLabel("Spieler 4"));
	}

	public Dimension getMaximumSize() {
		return new Dimension(100, 1000);
	}
	
	public Dimension getMinimumSize() {
		return new Dimension(100, 0);
	}
}
