package bomberman.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import bomberman.logic.Bomberman;

public class Gui {

	public JFrame frame;
	private JPanel jogo;
	private int nrPlayers = 1;
	public int volume = 50;
	private static Gui instance = null;
	private SoundAnimation powerupSound = new SoundAnimation(new File(System.getProperty("user.dir") + "\\resources\\default.mp3").toURI().toString());
	private Bomberman bm = new Bomberman();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					instance = new Gui();
					instance.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static Gui getInstance() {
		if (instance == null) {
			instance = new Gui();
		}
		return instance;
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

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 50 * bm.getMapa().getTamanho(), 50 * bm.getMapa().getTamanho());
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("BOMBERMAN");

		initMainMenu();

		frame.getContentPane().add(jogo);
		frame.repaint();
	}

	private void initMainMenu() {
		this.jogo = new JPanel();

		this.jogo.setBounds(frame.getBounds());

		this.jogo.setLayout(null);

		Image logoImage = null;
		Image fundo = null;
		try {
			logoImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\logo.png"));
			fundo = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon fundoIcon = new ImageIcon(fundo);
		JLabel fundoLabel = new JLabel();
		fundoLabel.setIcon(fundoIcon);
		fundoLabel.setBounds(50, 100, 500, 500);
		fundoLabel.setOpaque(true);

		

		ImageIcon logoIcon = new ImageIcon(logoImage);
		JLabel logo = new JLabel();
		logo.setIcon(logoIcon);
		logo.setBounds(frame.getWidth() / 6 - 50, frame.getHeight() / 6 - 150, 500, 150);

		jogo.add(logo);

		JButton play = new JButton("Play");
		play.setBounds(frame.getWidth() / 2 - 225, (int) 2.5 * frame.getHeight() / 6 - 100, 250, 100);

		JButton settings = new JButton("Settings");
		settings.setBounds(frame.getWidth() / 2 - 225, (int) 3.5 * frame.getHeight() / 6 - 50, 250, 100);

		JButton exit = new JButton("Exit");
		exit.setBounds(frame.getWidth() / 2 - 225, (int) 4.5 * frame.getHeight() / 6, 250, 100);

		Font fontLetras = new Font("Calibri", Font.BOLD, 55);
		play.setFont(fontLetras);
		settings.setFont(fontLetras);
		exit.setFont(fontLetras);
		
		play.setForeground(Color.RED);
		settings.setForeground(Color.RED);
		exit.setForeground(Color.RED);

		play.setBorder(BorderFactory.createEmptyBorder());
		play.setContentAreaFilled(false);
		play.setFocusPainted(false);
		play.setOpaque(false);

		settings.setBorder(BorderFactory.createEmptyBorder());
		settings.setContentAreaFilled(false);
		settings.setFocusPainted(false);
		settings.setOpaque(false);

		exit.setBorder(BorderFactory.createEmptyBorder());
		exit.setContentAreaFilled(false);
		exit.setFocusPainted(false);
		exit.setOpaque(false);

		play.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				play.setForeground(Color.BLUE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				play.setForeground(Color.RED);
			}
		});

		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conectPlayers();
			}
		});

		settings.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				settings.setForeground(Color.BLUE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				settings.setForeground(Color.RED);
			}
		});

		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog settingsDialog = new JDialog();
				settingsDialog.getContentPane().setLayout(new GridLayout(2, 2));
				settingsDialog.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 250, 250);
				settingsDialog.setModal(true);

				JSlider players = new JSlider(JSlider.HORIZONTAL, 2, 4, nrPlayers);
				JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, volume);

				players.setMinorTickSpacing(1);
				players.setPaintTicks(true);
				players.setPaintLabels(true);
				players.setLabelTable(players.createStandardLabels(1));

				volumeSlider.setMinorTickSpacing(5);
				volumeSlider.setPaintTicks(true);
				volumeSlider.setPaintLabels(true);
				volumeSlider.setLabelTable(players.createStandardLabels(10));

				settingsDialog.getContentPane().add(new JLabel("Nr Jogadores:", SwingConstants.CENTER));
				settingsDialog.getContentPane().add(players);

				settingsDialog.getContentPane().add(new JLabel("Volume Som:", SwingConstants.CENTER));
				settingsDialog.getContentPane().add(volumeSlider);

				// TODO Ver os labls do JSlider

				settingsDialog.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						nrPlayers = players.getValue();
						volume = volumeSlider.getValue();

					}
				});

				settingsDialog.setVisible(true);
				settingsDialog.requestFocus();
			}

		});

		exit.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				exit.setForeground(Color.BLUE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				exit.setForeground(Color.RED);
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		this.jogo.add(play);
		this.jogo.add(settings);
		this.jogo.add(exit);
		jogo.add(fundoLabel);

		frame.getContentPane().add(jogo);

	}

	private void conectPlayers() {
		frame.getContentPane().remove(this.jogo);

		this.jogo = new ConnectPlayer(nrPlayers, frame);
		frame.getContentPane().add(this.jogo);

		frame.validate();
		frame.repaint();
	}

	public void startGame() {
		this.frame.getContentPane().remove(this.jogo);

		this.frame.validate();
		this.frame.repaint();

		bm = new Bomberman();
		for (int i = 0; i < nrPlayers; i++) {
			bm.adicionarJogador();
		}
		this.powerupSound.play();

		this.jogo = new PanelJogo(bm, this.frame);
		this.frame.getContentPane().add(this.jogo);
	}
}
