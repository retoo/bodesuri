package ui.spiel.brett;

import java.awt.Color;
import java.awt.Point;
import java.util.Vector;

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
import ui.spiel.BodesuriView;
import ui.spiel.karten.KarteView;

public class JokerView extends JPanel{
	private Vector<Karte> karten;
	private Vector<applikation.client.pd.Karte> kartenDeck;
	public JokerView(BodesuriView view){
		setLayout(null);
		setBackground(new Color(0,0,0,100));
		setOpaque(false);
		
		JLabel hintergrund = new JLabel();
		hintergrund.setBackground(new Color(0,0,0,178));
		hintergrund.setOpaque(true);
		hintergrund.setBounds(0, 0, view.getWidth(), view.getHeight());
		
		add(hintergrund);
		
		erstelleKarten();
		erstelleDeck();
		
		Vector<KarteView> karteViews = new Vector<KarteView>();
		for(int i = 0; i < kartenDeck.size(); i++){
			Point position = new Point(200 + i * 100, 300);
			KarteView kv = new KarteView(position, null, null);
			karteViews.add(kv);
			kv.setKarte(kartenDeck.get(i));
			add(kv);
		}
		
	}
	
	public void erstelleKarten(){
		karten = new Vector<Karte>();
		karten.add(new Ass(KartenFarbe.Herz, 0));
		karten.add(new Koenig(KartenFarbe.Herz, 0));
		karten.add(new Bube(KartenFarbe.Herz, 0));
		karten.add(new Dame(KartenFarbe.Herz, 0));
		karten.add(new Zehn(KartenFarbe.Herz, 0));
		karten.add(new Neun(KartenFarbe.Herz, 0));
		karten.add(new Acht(KartenFarbe.Herz, 0));
		karten.add(new Sieben(KartenFarbe.Herz, 0));
		karten.add(new Sechs(KartenFarbe.Herz, 0));
		karten.add(new Fuenf(KartenFarbe.Herz, 0));
		karten.add(new Vier(KartenFarbe.Herz, 0));
		karten.add(new Drei(KartenFarbe.Herz, 0));
		karten.add(new Zwei(KartenFarbe.Herz, 0));
	}
	
	public void erstelleDeck(){
		kartenDeck = new Vector<applikation.client.pd.Karte>();
		for (int i = 0; i < karten.size(); i++){
			kartenDeck.add( new applikation.client.pd.Karte(karten.get(i)));			
		}
	}
	

}
