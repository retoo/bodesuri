/**
 * @(#) KarteView.java
 */

package ui.spiel.info;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import pd.karten.Karte;
import applikation.events.KarteGewaehltEvent;
import dienste.automat.EventQueue;

public class KarteView extends JLabel {

	private static final long serialVersionUID = 1L;

	private Karte karte;

	private EventQueue queue;

	public KarteView(Point p, Karte karte, EventQueue queue, DeckView view) {
		this.karte = karte;
		this.queue = queue;

		String pfad = "/ui/ressourcen/" + karte.getName() + "_"
				+ karte.getKartenFarbe() + ".png";
		System.out.println(pfad);
		URL resource1 = KarteView.class.getResource(pfad);
		final Icon bildFigur = new ImageIcon(resource1);
		setIcon(bildFigur);

		setBounds((int) p.getX(), (int) p.getY(), bildFigur.getIconWidth(),
				bildFigur.getIconHeight());
		setPreferredSize(new Dimension(100, 125));
		setMaximumSize(new Dimension(100, 125));
		setMinimumSize(new Dimension(100, 125));
		setBackground(Color.black);

		addMouseListener(new java.awt.event.MouseAdapter() {

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				KarteGewaehltEvent e = new KarteGewaehltEvent(
						KarteView.this.karte);
				KarteView.this.queue.enqueue(e);
			}
		});
	}
}
