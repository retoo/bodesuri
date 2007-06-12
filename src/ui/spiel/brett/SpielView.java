package ui.spiel.brett;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ui.GUIController;
import ui.ressourcen.Icons;
import ui.spiel.karten.KartenAuswahlView;

public class SpielView extends JPanel {
	Image filz;
	public SpielView(GUIController controller) {
		ImageIcon icon = (ImageIcon) Icons.FILZ;
		filz = icon.getImage();
		
		// Layout setzen
		setLayout(new BorderLayout());
		setOpaque(false);

		// Views
		SpielBrettView spielBrettView = new SpielBrettView(controller);
		KartenAuswahlView kartenAuswahlView = new KartenAuswahlView(controller);

		// Layout zusammenstellen
		add(spielBrettView, BorderLayout.CENTER);
		add(kartenAuswahlView, BorderLayout.EAST);
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(filz, 0, 0, null);
		super.paintComponent(g);
	}
}
