package bomberman.gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bomberman.connection.Connection;
import bomberman.connection.Connection.ServerStatus;
import bomberman.logic.Bomberman;

public class Gui {

	private JFrame frame;
	private JPanel jogo;

	private Bomberman bm = new Bomberman();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
//		Connection con = Connection.getInstance();//
//		System.out.println("ENTRA!");
//		while (con.getStatus() != ServerStatus.RUNNING) {
//		}
//		System.out.println("SAI!");

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 50 * bm.getMapa().getTamanho(), 50 * bm.getMapa().getTamanho());
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jogo = new PanelJogo(bm);
		
		jogo.setBounds(0, 0, 50 * bm.getMapa().getTamanho(), 50 * bm.getMapa().getTamanho());

		frame.getContentPane().add(jogo);
	}
}
