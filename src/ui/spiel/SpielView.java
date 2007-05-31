package ui.spiel;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ui.spiel.brett.BrettView;
import ui.spiel.chat.ChatView;
import applikation.client.ClientController;

public class SpielView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public SpielView(ClientController controller) {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(new BrettView(controller));
		add(new ChatView(controller));
	}
}
