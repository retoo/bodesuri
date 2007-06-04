package ui.spiel.info;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import applikation.client.Controller;



public class InfoView extends JPanel {
	JPanel northPanel = new JPanel();

	public InfoView(Controller controller) {
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(260, 440));
		setPreferredSize(new Dimension(260, 440));
		setBorder(new EmptyBorder(15, 15, 15, 15));

		northPanel.setLayout(new BorderLayout());
		northPanel.add(new SpielerView(controller), BorderLayout.NORTH);
		northPanel.add(new DeckView(controller), BorderLayout.CENTER);

		add(northPanel, BorderLayout.NORTH);
		add(new SteuerungsView(controller), BorderLayout.CENTER);
	}

}
