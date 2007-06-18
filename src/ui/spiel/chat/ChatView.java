package ui.spiel.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Chat;

/**
 * Implementierung des Chat-Client, der zur Text-Kommunikation zwischen den
 * einzelnen Clients dient.
 */
public class ChatView extends JPanel implements Observer {
	private JTextArea chatArea;
	private JTextField eingabe;
	private Steuerung steuerung;

	private Chat chat;

	public ChatView(Chat chat, Steuerung steuerung) {
		this.steuerung = steuerung;
		this.chat = chat;

		chat.addObserver(this);

		// Layout setzen
		setLayout(new BorderLayout());
		setBackground(Color.darkGray);

		// Layout zusammenstellen
		chatArea = new JTextArea();

		chatArea.setEditable(false);
		/* hässlich, wir zwingen das textarea min. drei Zeilen gross zu sein */
		chatArea.setText("\n\n\n");

		add(new JScrollPane(chatArea), BorderLayout.CENTER);

		eingabe = new JTextField();

		eingabe.requestFocusInWindow();

		eingabe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatView.this.steuerung.chatNachricht(eingabe.getText());
				eingabe.setText("");
			}
		});

		add(eingabe, BorderLayout.SOUTH);
	}

	public void update(Observable o, Object arg) {
		StringBuilder messages = new StringBuilder();

		for (String message : chat) {
			messages.insert(0, message);
		}

		chatArea.setText(messages.toString());
	}
}
