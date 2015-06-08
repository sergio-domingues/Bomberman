package bomberman.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import bomberman.connection.Connection;

@SuppressWarnings("serial")
public class ConnectPlayer extends JPanel {
	private Image vermelho, verde, amarelo, azul, disconnected;
	private boolean comecouJogo = false;
	private boolean countDown;
	private long tempo = 3500;// 3 segundos
	private int nrPlayersMax;
	private JFrame frame;
	private JLabel tempoLabel = new JLabel();
	JLabel ipLabel;

	public ConnectPlayer(int nrPlayers, JFrame frame) {

		this.nrPlayersMax = nrPlayers;
		loadImagens();
		this.frame = frame;
		this.frame.setTitle("Connecting Players");

		this.setBounds(0, 0, frame.getBounds().width, frame.getBounds().height);

		Connection.setMaxConnection(nrPlayers);
		Connection.getInstance();

		this.setLayout(null);
		JLabel ip = new JLabel();
		ip.setText(Connection.getHostIp());

		JLabel nrConnectsLabel = new JLabel();
		nrConnectsLabel.setText("Nr Jogadores Ligados:" + Integer.toString(Connection.getInstance().getNrConnections()));

		Timer timer = new Timer(500, timerListener);
		timer.start();

		for (int i = 0; i < nrPlayersMax; i++) {
			JLabel player = new JLabel();
			player.setText("Jogador" + (i + 1));
			player.setBounds(180 + (i % 2) * 400, 250 + (int) (i / 2) * 300, 100, 50);
			this.add(player);
		}
		tempoLabel = new JLabel();
		tempoLabel.setBounds(frame.getBounds().width / 2 - 50, frame.getBounds().height / 2 - 150, 150, 150);
		tempoLabel.setFont(new Font("Calibri", Font.BOLD, 150));
		tempoLabel.setForeground(Color.RED);

		ipLabel = new JLabel("<html>Connect to this IP:<br>" + Connection.getHostIp() + "</html>");
		ipLabel.setBounds(frame.getWidth() / 2 - 100, frame.getHeight() / 2 - 120, 350, 150);
		ipLabel.setFont(new Font("Calibri", Font.BOLD, 25));
		ipLabel.setForeground(Color.BLUE);

		this.add(tempoLabel);
		this.add(ip);
		this.add(nrConnectsLabel);
		this.add(ipLabel);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		int sx1 = (int) (0);
		int sy1 = (int) (0);
		int sx2 = (int) (vermelho.getWidth(null) / 4);
		int sy2 = (int) (vermelho.getHeight(null) / 4);

		for (int i = 0; i < this.nrPlayersMax; i++) {
			int dx1 = (int) (((int) i % 2) * 400 + 130);
			int dy1 = (int) (((int) i / 2) * 300 + 100);
			int dx2 = (int) (dx1 + PanelJogo.TILESIZE + 100);
			int dy2 = (int) (dy1 + PanelJogo.TILESIZE + 100);

			if (i < Connection.getInstance().getNrConnections()) {

				if (i == 0) {
					g.drawImage(vermelho, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
				} else if (i == 1) {
					g.drawImage(azul, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
				} else if (i == 2) {
					g.drawImage(verde, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
				} else if (i == 3) {
					g.drawImage(amarelo, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
				}
			} else {
				g.drawImage(disconnected, dx1, dy1, dx2, dy2, 0, 0, disconnected.getWidth(null), disconnected.getHeight(null), null);
			}
		}

	}

	private void loadImagens() {
		try {
			this.vermelho = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerVermelho.png"));
			this.verde = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerVerde.png"));
			this.azul = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerAzul.png"));
			this.amarelo = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerAmarelo.png"));
			this.disconnected = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\disconnected.png"));

		} catch (IOException e) {
			System.err.println("Erro carregar imagens!");
			e.printStackTrace();
		}

	}

	ActionListener timerListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (countDown) {
				tempo = tempo - 500;
				tempoLabel.setText(Long.toString(tempo / 1000));
			} else if (Connection.getInstance().getNrConnections() == nrPlayersMax) {
				countDown = true;
				ipLabel.setEnabled(false);
			}
			if (tempo <= 0 && comecouJogo == false) {
				Gui.getInstance().startGame();
				comecouJogo = true;
			}
			repaint();
		}

	};
}
