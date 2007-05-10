package ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
	private JMenuItem spielBeitreten;
	private JMenuItem spielVerlassen;

	public BodesuriView() {
		setTitle("Bodesuri");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

		// Menu
		menuleiste = new JMenuBar();

		spielMenu = new JMenu("Spiel");
		menuleiste.add(spielMenu);

		spielBeitreten = new JMenuItem("Spiel beitreten");
		spielMenu.add(spielBeitreten);

		spielVerlassen = new JMenuItem("Spiel verlassen");
		spielMenu.add(spielVerlassen);

		setJMenuBar(menuleiste);

		// Panels
		// Box scheint das beste Layout zu sein.
		// - Border geht nicht wegen dem Resize.
		// - GridBag ist zu kompliziert, weil wir unabhängige Spalten haben.
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		Box links = Box.createVerticalBox();
		links.add(new BrettView());
		links.add(new ChatView());

		Box rechts = Box.createVerticalBox();
		rechts.add(new SpielerView());
		rechts.add(new DeckView());
		rechts.add(Box.createVerticalGlue());

		add(links);
		add(rechts);
		add(Box.createHorizontalGlue());
		pack();
	}

	public static void main(String[] args) {
		// Speziell für den Mac
		if (System.getProperty("mrj.version") != null) {
			// Menubar oben
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		new BodesuriView().setVisible(true);
	}

}
