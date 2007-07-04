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


package ui.lobby;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import ui.geteiltes.ChatView;
import ui.geteiltes.SpielerView;
import applikation.client.controller.Steuerung;
import applikation.client.pd.Chat;
import applikation.client.pd.Spieler;

/**
 * JFrame für das Lobby, in dem die Spieler auf den Spielstart warten.
 */
public class LobbyView extends JFrame {
	// Konstanten und Vorgabewerte
	private final static int FRAME_WIDTH = 450;
	private final static int FRAME_HEIGHT = 400;
	private final static String introTitel = "Willkommen in der Bodesuri-Lobby";

	private JPanel spielerPanel;
	private JLabel introTitelLabel;
	private JPanel viewPanel;

	public LobbyView(List<Spieler> spieler, Steuerung steuerung, Chat chat) {
		// Initialisierung
		introTitelLabel = new JLabel(introTitel);
		introTitelLabel.setFont(introTitelLabel.getFont().deriveFont(Font.BOLD));
		introTitelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		spielerPanel = new JPanel();
		viewPanel = new JPanel();
		viewPanel.setBorder(new EmptyBorder(10,10,10,10));
		viewPanel.setOpaque(false);

		setTitle("Bodesuri - Lobby");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setResizable(false);

		spielerPanel.setBorder(new TitledBorder("Anwesende Spieler"));
		spielerPanel.setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		spielerPanel.setLayout(new BoxLayout(spielerPanel, BoxLayout.Y_AXIS));
		spielerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		for (Spieler s : spieler) {
			SpielerView spielerView = new SpielerView(s);
			spielerView.setBorder(BorderFactory.createEmptyBorder(2, 7, 5, 5));
			spielerPanel.add(spielerView);
		}

		// Panels dem Frame hinzufügen
		viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		viewPanel.add(introTitelLabel);
		viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		viewPanel.add(spielerPanel);
		ChatView chatView = new ChatView(chat, steuerung);
		chatView.setBorder(new TitledBorder("Chat"));
		viewPanel.add(chatView);
		add(viewPanel);

		// View anordnen und zentrieren
		pack();
		setLocationRelativeTo(null);
	}
}
