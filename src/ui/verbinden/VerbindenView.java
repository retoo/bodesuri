package ui.verbinden;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import ui.GUIController;

/**
 * JFrame, dient zur Eingabe der Informationen für den Server auf den verbindet
 * werden soll, sowie auch zur Erfassung der Spielerinformationen.
 */
public class VerbindenView extends JFrame {
	private final int FRAME_WIDTH 		= 300;
	private final int FRAME_HEIGHT 		= 175;
	private final int BORDER_WIDTH		= 15;
	private final String DEFAULT_PORT	= "7788";
	private final String DEFAULT_HOST	= "localhost";
	
	private JTextField hostname = new InputTextField( DEFAULT_HOST );
	private JTextField port = new InputTextField( DEFAULT_PORT );
	private JTextField spielerName;
	private GUIController controller;
	private JPanel inputpanel;
	private JPanel buttonpanel = new JPanel();
	private JButton verbinden = new JButton("Verbinden");
	private JButton abbrechen = new JButton("Abbrechen");

	public VerbindenView(GUIController controller, final String DEFAULT_NAME) {
		this.controller = controller;
		spielerName = new InputTextField(DEFAULT_NAME);

		setTitle("Bodesuri - Verbinden");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout( new BorderLayout() );
		setPreferredSize( new Dimension(FRAME_WIDTH, FRAME_HEIGHT) );
		setResizable(false);
		
		// View auf Monitor zentrieren
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize(); 
		int x = (monitor.width - getSize().width - FRAME_WIDTH) / 2; 
		int y = (monitor.height - getSize().height - FRAME_HEIGHT) / 2; 
		setLocation(x, y);

		// Components modifizieren
		inputpanel = new InputPanel(hostname, port, spielerName);
		buttonpanel.setLayout( new BoxLayout(buttonpanel, BoxLayout.LINE_AXIS) );
		buttonpanel.setBorder(new EmptyBorder(0, 0, BORDER_WIDTH, BORDER_WIDTH));
		abbrechen.setSelected( false );
		verbinden.setSelected( true );
		verbinden.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "pressed");
		verbinden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String host = hostname.getText();
				String spieler = spielerName.getText();
				Integer port_raw = Integer.valueOf(port.getText());

				VerbindenView.this.controller.verbinde(host, port_raw, spieler);
			}
		});
		abbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				VerbindenView.this.setVisible(false);
				VerbindenView.this.dispose();
				System.exit(0);
			}
		});
		
		// Components hinzufügen
		buttonpanel.add( Box.createHorizontalGlue() );
		buttonpanel.add( abbrechen );
		buttonpanel.add( Box.createRigidArea(new Dimension(BORDER_WIDTH, 0)) );
		buttonpanel.add( verbinden );
		add( inputpanel, BorderLayout.CENTER );
		add( buttonpanel, BorderLayout.SOUTH );
		
		// View anzeigen
		getRootPane().setDefaultButton(verbinden);
		pack();
	}
	
	private class VerbindenLabel extends JLabel {
		public VerbindenLabel(String text) {
			super(text);
			setForeground(Color.BLACK);
		}
	}
	
	private class InputTextField extends JTextField {
		public InputTextField(String text) {
			super(text);
			
		}
	}
	
	private class InputPanel extends JPanel {
		public InputPanel(JTextField hostname, JTextField port, JTextField spielerName) {
			setLayout( new GridLayout(3, 2, 0, 5) );
			setBorder(new EmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));
			
			// Components hinzufügen
			add( new VerbindenLabel("Server:") );
			add( hostname );
			add( new VerbindenLabel("Port:") );
			add( port );
			add( new VerbindenLabel("Spieler:") );
			add( spielerName );
		}
	}
}
