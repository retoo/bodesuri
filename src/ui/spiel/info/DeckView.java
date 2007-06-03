package ui.spiel.info;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import pd.karten.Acht;
import pd.karten.Ass;
import pd.karten.Drei;
import pd.karten.Fuenf;
import pd.karten.Karte;
import pd.karten.KartenFarbe;
import pd.karten.Sechs;
import pd.karten.Zehn;
import applikation.client.ClientController;

/**
 * JPanel, das DeckView wird zur Darstellung der Karten verwendet. Hier werden
 * die Karten der Spieler verwaltet und dargestellt.
 */
public class DeckView extends JPanel {
	Vector<Karte> karten = new Vector<Karte>();

	public DeckView(ClientController controller) {
		TitledBorder titel = new TitledBorder("Karten");
		setBorder(titel);
		setLayout(null);
		setPreferredSize(new Dimension(230, 280));
		setMaximumSize(new Dimension(230, 280));
		setMinimumSize(new Dimension(230, 280));

		karten.add(new Ass(KartenFarbe.Kreuz, 1));
		karten.add(new Drei(KartenFarbe.Karo, 1));
		karten.add(new Fuenf(KartenFarbe.Kreuz, 1));
		karten.add(new Zehn(KartenFarbe.Pik, 1));
		karten.add(new Sechs(KartenFarbe.Herz, 1));
		karten.add(new Acht(KartenFarbe.Karo, 1));

		KartenAuswahl kartenAuswahl = new KartenAuswahl(new Point());
		KarteMouseAdapter karteMouseAdapter = new KarteMouseAdapter(controller,
		                                                            this,
		                                                            kartenAuswahl);
		for (int i = 5; i >= 0; i--) {
			Point p = new Point(20 + i * 20, 30 + i * 20);
			this.add(new KarteView(p, karten.get(i), karteMouseAdapter, this));
		}
	}
}
