package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.brett.BrettView;
import ui.chat.ChatView;
import ui.info.DeckView;
import ui.info.SpielerView;

/**
 * Das GUI des Spieles...
 * 
 */
public class BodesuriView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar menuleiste;
	private JMenu spielMenu;
	private JMenuItem spielStarten;
	private JMenuItem spielBeenden;

	public BodesuriView() {
		setTitle("Bodesuri");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

		// Menu
		menuleiste = new JMenuBar();

		spielMenu = new JMenu("Spiel");
		menuleiste.add(spielMenu);

		spielStarten = new JMenuItem("Spiel starten");
		spielMenu.add(spielStarten);

		spielBeenden = new JMenuItem("Spiel beenden");
		spielMenu.add(spielBeenden);

		setJMenuBar(menuleiste);

		// Panels
		/*
		 * Hat sich nach längeren Test als das Beste erwiesen:
		 * - Border nicht, weil wir die Kontrolle über die Grösse des 
		 *   Spielfeldes haben wollen
		 * - Box nicht, da zwei zusätzliche Panels erstelt werden müssen
		 */
		setLayout(new GridBagLayout());

		GridBagConstraints links = new GridBagConstraints();
		links.anchor = GridBagConstraints.NORTHWEST;
		links.gridx = 0;
		links.gridy = 0;
		add(new BrettView(), links);

		links.gridx = 0;
		links.gridy = 1;
		add(new ChatView(), links);

		GridBagConstraints rechts = new GridBagConstraints();
		rechts.anchor = GridBagConstraints.NORTHWEST;
		rechts.gridx = 1;
		rechts.gridy = 0;
		add(new SpielerView(), rechts);

		rechts.gridx = 1;
		rechts.gridy = 1;
		add(new DeckView(), rechts);

		pack();
	}

	public static void main(String[] args) {
		new BodesuriView().setVisible(true);
	}

}
