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


package ui.spiel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.geteiltes.ChatView;
import ui.ressourcen.Icons;
import ui.spiel.brett.BrettView;
import ui.spiel.steuerung.SteuerungsView;
import applikation.client.controller.Steuerung;
import applikation.client.pd.Spiel;

/**
 * Das Haupt-GUI des Spiels.
 */
public class SpielView extends JFrame {
	private static final int RAND = 8;

	public SpielView(Steuerung steuerung, Spiel spiel) {
		setTitle("Bodesuri - Spiel (" + spiel.spielerIch + ")");
		setName("Bodesuri");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Views
		JPanel topPanel = erstelleTopPanel(steuerung, spiel);
		ChatView chatView = new ChatView(spiel.chat, steuerung);

		// Layout
		JPanel panel = new BodesuriViewPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		panel.add(topPanel, c);
		panel.add(Box.createVerticalStrut(RAND), c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		panel.add(chatView, c);

		panel
				.setBorder(BorderFactory.createEmptyBorder(RAND, RAND, RAND,
						RAND));

		setContentPane(panel);

		// GUI anzeigen
		pack();
	}

	private JPanel erstelleTopPanel(Steuerung steuerung, Spiel spiel) {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new GridBagLayout());

		BrettView brettView = new BrettView(steuerung, spiel);
		SteuerungsView steuerungsView = new SteuerungsView(steuerung, spiel);

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;

		panel.add(brettView, c);
		panel.add(steuerungsView, c);

		return panel;
	}
}

/**
 * Dient zur Darstellung des Hintergrunds, welches sich im Hindergrund des
 * gesamten Spiels befindet.
 */
class BodesuriViewPanel extends JPanel {
	private Paint paint;

	public BodesuriViewPanel() {
		setOpaque(false);
		paint = erstellePaint();
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(paint);
		g2.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

	private Paint erstellePaint() {
		Image filz = ((ImageIcon) Icons.FILZ).getImage();
		int width = filz.getWidth(null);
		int height = filz.getHeight(null);

		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(filz, 0, 0, null);
		bg.dispose();

		Rectangle2D tr = new Rectangle2D.Double(0, 0, width, height);
		return new TexturePaint(bi, tr);
	}
}