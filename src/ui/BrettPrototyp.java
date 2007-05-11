package ui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JFrame;

import ui.brett.Feld2d;


public class BrettPrototyp extends JFrame {

	private static final long serialVersionUID = 1L;

	public Vector<Feld2d> felder;

	public BrettPrototyp(String title, Vector<Feld2d> felder) {
		setTitle(title);
		setSize(1330, 100);
		setVisible(true);
		setLayout(null);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});

		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		env.getAvailableFontFamilyNames();
		setFont(new Font("Arial", Font.BOLD, 11));

		this.felder = felder;
		System.out.println("Size: " + felder.size());
		
		for (Feld2d feld: felder ){
			add(feld);
		}
	}



}
