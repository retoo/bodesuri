package ui.spiel.karten;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import ui.GUIController;

public class SteuerungsView extends JPanel {
	GUIController controller;

	public SteuerungsView(GUIController controller) {
		this.controller = controller;

		TitledBorder titel = new TitledBorder("Steuerung");
		setBorder(titel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		JLabel gespielteKarte = new JLabel();
		controller.registriereGespielteKarten(gespielteKarte);
		JButton aussetzen = new JButton("Aufgeben");
		aussetzen.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	SteuerungsView.this.controller.aufgeben();
            }
		});

		add(gespielteKarte);
		add(aussetzen);
	}

}
