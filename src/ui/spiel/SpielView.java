package ui.spiel;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ui.spiel.brett.BrettView;
import ui.spiel.chat.ChatView;
import applikation.client.BodesuriClient;

public class SpielView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public SpielView(BodesuriClient automat) {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(new BrettView(automat));
		add(new ChatView(automat));
	}
}
