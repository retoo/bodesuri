package ui.spiel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import ui.ressourcen.Icons;
import ui.spiel.brett.SpielView;
import ui.spiel.chat.ChatView;
import applikation.client.controller.Steuerung;
import applikation.client.pd.Spiel;

/**
 * Das GUI des Spiels.
 */
public class BodesuriView extends JFrame {
	public BodesuriView(Steuerung steuerung, Spiel spiel) {
		// Layout setzen
		setTitle("Bodesuri - Spiel (" + spiel.spielerIch + ")");
		setName("Bodesuri");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setNativeLookAndFeel();

		JPanel panel = new BodesuriViewPanel();
		panel.setLayout(new BorderLayout());

		// Views
		SpielView spielView = new SpielView(steuerung, spiel);
		ChatView chatView = new ChatView(spiel.chat, steuerung);

		// Layout zusammenstellen
		panel.add(spielView, BorderLayout.NORTH);
		panel.add(chatView, BorderLayout.CENTER);

		setContentPane(panel);

		// GUI anzeigen
		pack();
//		JPanel jokerView = new JokerView(this);
//		setGlassPane(jokerView);
//		jokerView.setVisible(true);
	}

	/**
	 * Setzt den nativen Look & Feel für Windows. Auf allen anderen Plattformen
	 * wird eine Exception geworfen, die ignoriert wird.
	 */
	private static void setNativeLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
	}
}

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

		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(filz, 0, 0, null);
		bg.dispose();

		Rectangle2D tr = new Rectangle2D.Double(0, 0, width, height);
		return new TexturePaint(bi, tr);
	}
}