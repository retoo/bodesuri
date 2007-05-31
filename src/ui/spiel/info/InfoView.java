package ui.spiel.info;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import applikation.client.BodesuriClient;

public class InfoView extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public InfoView(BodesuriClient automat) {
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(260, 440));
		setPreferredSize(new Dimension(260, 440));
		setBorder(new EmptyBorder(15, 15, 15, 15));
		
		add(new SpielerView(automat), BorderLayout.NORTH);
		add(new DeckView(automat), BorderLayout.CENTER);
	}

}
