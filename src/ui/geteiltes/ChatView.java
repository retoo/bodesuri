/*
 * Copyright (C) 2007  Danilo Couto, Philippe Eberli,
 *                     Pascal Hobus, Reto Schüttel, Robin Stocker
 *
 * This file is part of Bodesuri.
 *
 * Bodesuri is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * Bodesuri is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bodesuri; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


package ui.geteiltes;

import java.awt.BorderLayout;
import java.awt.Font;
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
		// Dialog-Schrift setzen. Unter Windows haben wir sonst Courier als
		// Schriftart.
		Font chatAreaFont = new Font("Dialog", chatArea.getFont().getStyle(),
				chatArea.getFont().getSize());
		chatArea.setFont(chatAreaFont);
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
			if (i != 0)
				text.append("\n");
			text.append(chat.get(i));
		}

		chatArea.setText(text.toString());
		chatArea.setCaretPosition(chatArea.getDocument().getLength());
	}
}
