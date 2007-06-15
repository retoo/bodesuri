package ui.spiel.brett;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ui.GUIController;
import ui.ressourcen.Icons;
import ui.spiel.karten.KartenAuswahlView;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;

public class SpielView extends JPanel {
	Image filz;
	public SpielView(GUIController controller, Spiel spiel, Spieler spielerIch) {
		ImageIcon icon = (ImageIcon) Icons.FILZ;
		filz = icon.getImage();
		
		// Layout setzen
		setLayout(new BorderLayout());
		setOpaque(false);

		// Views
		SpielBrettView spielBrettView = new SpielBrettView(controller, spiel);
		KartenAuswahlView kartenAuswahlView = new KartenAuswahlView(controller, spielerIch);

		// Layout zusammenstellen
		add(spielBrettView, BorderLayout.CENTER);
		add(kartenAuswahlView, BorderLayout.EAST);
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(filz, 0, 0, null);
		super.paintComponent(g);
	}
}
