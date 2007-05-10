package ui.chat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatView extends JPanel {

	private static final long serialVersionUID = 1L;

	public ChatView() {
		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		setBackground(new Color(0, 0, 150));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setPreferredSize(new Dimension(600, 200));
		setMaximumSize(new Dimension(600, 1000));
		setMinimumSize(new Dimension(600, 10));
		
		this.add(new JLabel("Hier käme der Chat hin... \n(Prio 3)"));
	}
}
