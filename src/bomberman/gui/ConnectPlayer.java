package bomberman.gui;

import java.awt.FlowLayout;
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
	private Image vermelho, verde, amarelo, azul;

	private int nrPlayersMax;

	public ConnectPlayer(int nrPlayers, JFrame frame) {

		this.nrPlayersMax = nrPlayers;
		loadImagens();

		this.setBounds(frame.getBounds());

		Connection.setMaxConnection(nrPlayers);
		Connection.getInstance();

		this.setLayout(new FlowLayout());
		JLabel ip = new JLabel();
		ip.setText(Connection.getHostIp());

		JLabel nrConnectsLabel = new JLabel();
		nrConnectsLabel.setText("Nr Jogadores Ligados:" + Integer.toString(Connection.getInstance().getNrConnections()));

		Timer timer = new Timer(500, timerListener);
		timer.start();

		this.add(ip);
		this.add(nrConnectsLabel);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// TODO ACERTAR FUNCAO
		int sx1 = (int) (0);
		int sy1 = (int) (0);
		int sx2 = (int) (PanelJogo.TILESIZE);
		int sy2 = (int) (PanelJogo.TILESIZE);

		for (int i = 0; i < Connection.getInstance().getNrConnections(); i++) {
			int dx1 = (int) ((i % 2 * 50) * PanelJogo.TILESIZE);
			int dy1 = (int) ((i / 2) * PanelJogo.TILESIZE);
			int dx2 = (int) (dx1 + PanelJogo.TILESIZE);
			int dy2 = (int) (dy1 + PanelJogo.TILESIZE);

			if (i == 0) {
				g.drawImage(vermelho, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
			} else if (i == 1) {
				g.drawImage(azul, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
			}
		}
	}

	private void loadImagens() {
		try {
			this.vermelho = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerVermelho.png"));
			this.verde = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerVerde.png"));
			this.azul = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerAzul.png"));
			this.amarelo = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerAmarelo.png"));

		} catch (IOException e) {
			System.err.println("Erro carregar imagens!");
			e.printStackTrace();
		}

	}

	ActionListener timerListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	};
}
