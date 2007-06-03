package ui.spiel.brett;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.spiel.chat.ChatView;
import applikation.client.ClientController;

public class SpielView extends JPanel {
	public SpielView(ClientController controller) {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(15, 15, 15, 15));
		add(new SpielBrettView(controller), BorderLayout.CENTER);
		add(new ChatView(controller), BorderLayout.SOUTH);
	}
}
