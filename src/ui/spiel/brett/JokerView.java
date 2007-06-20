package ui.spiel.brett;

import java.awt.Color;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pd.karten.Acht;
import pd.karten.Ass;
import pd.karten.Bube;
import pd.karten.Dame;
import pd.karten.Drei;
import pd.karten.Fuenf;
import pd.karten.Karte;
import pd.karten.KartenFarbe;
import pd.karten.Koenig;
import pd.karten.Neun;
import pd.karten.Sechs;
import pd.karten.Sieben;
import pd.karten.Vier;
import pd.karten.Zehn;
import pd.karten.Zwei;
import ui.ressourcen.BrettXML;
import ui.ressourcen.Icons;
import ui.spiel.karten.KarteMouseAdapter;
import ui.spiel.karten.KarteView;
import ui.spiel.karten.KartenAuswahl;

public class JokerView extends JPanel {
	private Vector<Karte> karten;
	private Vector<applikation.client.pd.Karte> kartenDeck;
	private BrettXML brettXML;

	public JokerView(JFrame view, KarteMouseAdapter karteMouseAdapter,
			KartenAuswahl kartenAuswahl, JockerDeck deck) {
		setLayout(null);
		setBackground(new Color(0, 0, 0, 100));
		setOpaque(false);

		erstelleKarten();
		erstelleDeck();

		try {
			brettXML = new BrettXML("/ui/ressourcen/brett.xml");
		} catch (Exception e) {
			// Checked Exception in unchecked umwandeln
			throw new RuntimeException(e);
		}

		Vector<KarteView> karteViews = new Vector<KarteView>();
		for (int i = 0; i < kartenDeck.size(); i++) {
			KarteView kv = new KarteView(brettXML.getJokerKarten().get(i),
					karteMouseAdapter, kartenAuswahl);
			karteViews.add(kv);
			kv.setKarte(kartenDeck.get(i));
			add(kv);
		}

		JLabel jokerSchliessen = new JLabel();
		jokerSchliessen.setIcon(Icons.JOKERSCHLIESSEN);
		Point pos = brettXML.getJokerKarten().get(13);
		jokerSchliessen.setBounds(pos.x, pos.y, Icons.JOKERSCHLIESSEN
				.getIconWidth(), Icons.JOKERSCHLIESSEN.getIconHeight());
		jokerSchliessen.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				System.out.println(evt);
			}
		});
		add(jokerSchliessen);

		JLabel hintergrund = new JLabel();
		hintergrund.setBackground(new Color(0, 0, 0, 178));
		hintergrund.setOpaque(true);
		hintergrund.setBounds(0, 0, view.getWidth(), view.getHeight());

		add(hintergrund);

	}

	public void erstelleKarten() {
		karten = new Vector<Karte>();
		karten.add(new Ass(KartenFarbe.Herz));
		karten.add(new Koenig(KartenFarbe.Herz));
		karten.add(new Bube(KartenFarbe.Herz));
		karten.add(new Dame(KartenFarbe.Herz));
		karten.add(new Zehn(KartenFarbe.Herz));
		karten.add(new Neun(KartenFarbe.Herz));
		karten.add(new Acht(KartenFarbe.Herz));
		karten.add(new Sieben(KartenFarbe.Herz));
		karten.add(new Sechs(KartenFarbe.Herz));
		karten.add(new Fuenf(KartenFarbe.Herz));
		karten.add(new Vier(KartenFarbe.Herz));
		karten.add(new Drei(KartenFarbe.Herz));
		karten.add(new Zwei(KartenFarbe.Herz));
	}

	public void erstelleDeck() {
		kartenDeck = new Vector<applikation.client.pd.Karte>();
		for (int i = 0; i < karten.size(); i++) {
			kartenDeck.add(new applikation.client.pd.Karte(karten.get(i)));
		}
	}

}
