package bomberman.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {

	public MainMenu(){
		super();
		this.setLayout(new GridLayout());
		JButton play =new JButton("Play");
		play.setText("teste");
		JButton settings = new JButton("Settings");
		JButton exit =new JButton ("Exit");
		this.add(play);
		this.add(settings);
		this.add(exit);
	}
}
