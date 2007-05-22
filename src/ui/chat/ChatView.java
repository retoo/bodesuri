package ui.chat;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatView extends JPanel {

	private static final long serialVersionUID = 1L;

	public ChatView() {
		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		setBackground(new Color(0, 0, 150));
		
		this.add(new JLabel("Hier käme der Chat hin... \n(Prio 3)"));
	}
}
