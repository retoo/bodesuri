package ui.spiel.chat;

import java.awt.BorderLayout;
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

		// Layout zusammenstellen
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setRows(3);

		eingabe = new JTextField();
		eingabe.requestFocusInWindow();

		eingabe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatView.this.steuerung.chatNachricht(eingabe.getText());
				eingabe.setText("");
			}
		});

		add(new JScrollPane(chatArea), BorderLayout.CENTER);
		add(eingabe, BorderLayout.SOUTH);
	}

	public void update(Observable o, Object arg) {
		StringBuilder text = new StringBuilder();

		for (int i = 0; i < chat.size(); ++i) {
			if (i != 0) text.append("\n");
			text.append(chat.get(i));
		}

		chatArea.setText(text.toString());
		chatArea.setCaretPosition(chatArea.getDocument().getLength());
	}
}
