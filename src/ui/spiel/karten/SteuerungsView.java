package ui.spiel.karten;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.GUIController;

public class SteuerungsView extends JPanel {
	GUIController controller;

	public SteuerungsView(GUIController controller) {
		this.controller = controller;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		JLabel gespielteKarte = new JLabel();
		controller.registriereGespielteKarten(gespielteKarte);
		JButton aussetzen = new JButton("Aufgeben");
		aussetzen.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	SteuerungsView.this.controller.aufgeben();
            }
		});

		add(gespielteKarte);
		add(aussetzen);
	}

}
