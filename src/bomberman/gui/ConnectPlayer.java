package bomberman.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import bomberman.connection.Connection;

@SuppressWarnings("serial")
public class ConnectPlayer extends JPanel {
	private Image vermelho, verde, amarelo, azul, disconnected;
	private boolean comecouJogo = false;
	private int nrPlayersMax;
	private JFrame frame;

	public ConnectPlayer(int nrPlayers, JFrame frame) {

		this.nrPlayersMax = nrPlayers;
		loadImagens();
		this.frame = frame;

		this.setBounds(frame.getBounds());

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
			player.setBounds(50 + (i % 2) * 400, 150 + (int) (i / 2) * 300, 100, 50);
			this.add(player);
		}

		this.add(ip);
		this.add(nrConnectsLabel);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// TODO ACERTAR FUNCAO
		int sx1 = (int) (0);
		int sy1 = (int) (0);
		int sx2 = (int) (vermelho.getWidth(null) / 4);
		int sy2 = (int) (vermelho.getHeight(null) / 4);

		for (int i = 0; i < this.nrPlayersMax; i++) {
			int dx1 = (int) (((int) i % 2) * 400);
			int dy1 = (int) (((int) i / 2) * 300);
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
			repaint();
			if (Connection.getInstance().getNrConnections() == nrPlayersMax && comecouJogo == false) {
				Gui.getInstance().startGame();
				comecouJogo = true;
			}
		}
	};
}
