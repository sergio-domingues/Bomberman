package bomberman.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import bomberman.logic.Bomba.EstadoBomba;
import bomberman.logic.Bomba;
import bomberman.logic.Bomberman;
import bomberman.logic.Jogador;
import bomberman.logic.Jogador.Direcao;
import bomberman.logic.Jogador.EstadoJogador;
import bomberman.logic.Peca;

@SuppressWarnings("serial")
public class PanelJogo extends JPanel implements KeyListener {

	public static final int TILESIZE = 50;
	private Bomberman bm;
	private Timer timer;
	private double tempo = 0;

	private BufferedImage wall, fixedWall, floor, jogador, bomba, explosao;

	public PanelJogo(Bomberman bm) {
		setFocusable(true);
		this.setMinimumSize(new Dimension(TILESIZE * bm.getMapa().getTamanho(), TILESIZE * bm.getMapa().getTamanho()));
		this.loadImages();
		this.setLayout(new FlowLayout());
		this.setVisible(true);
		this.bm = bm;
		this.addKeyListener(this);
		timer = new Timer(60, timerListener);
		timer.start();

		Bomberman.imprimeMapa(bm.getMapa().getTab(), bm.getMapa().getTamanho());
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g); // limpa fundo ...

		BufferedImage img = floor; // default image

		int xi, yi;

		for (int i = 0; i < bm.getMapa().getTamanho(); i++) {
			for (int j = 0; j < bm.getMapa().getTamanho(); j++) {

				if (bm.getMapa().getTab()[i][j] == 'X') {
					img = fixedWall;
				} else if (bm.getMapa().getTab()[i][j] == ' ') {
					img = floor;
				} else if (bm.getMapa().getTab()[i][j] == 'W') {
					img = wall;
				}

				xi = j * TILESIZE;
				yi = i * TILESIZE;

				g.drawImage(img, xi, yi, TILESIZE, TILESIZE, null);
			}
		}

		for (int i = 0; i < bm.getBombas().size(); i++) {

			if (bm.getBombas().get(i).getEstadoBomba() != EstadoBomba.EXPLODINDO) {
				g.drawImage(bomba, (int) (bm.getBombas().get(i).getPos().getX() * TILESIZE),
						(int) (bm.getBombas().get(i).getPos().getY() * TILESIZE), TILESIZE, TILESIZE, null);
			}
		}

		// impressao jogador
		for (int i = 0; i < bm.getJogadores().size(); i++) {

			if (bm.getJogadores().get(i).getEstado() == Peca.Estado.ACTIVO)
				g.drawImage(jogador, (int) (bm.getJogadores().get(i).getPos().getX() * TILESIZE),
						(int) (bm.getJogadores().get(i).getPos().getY() * TILESIZE), TILESIZE, TILESIZE, null);
		}

		for (int i = 0; i < bm.getBombas().size(); i++) {

			if (bm.getBombas().get(i).getEstadoBomba() == EstadoBomba.EXPLODINDO) {

				int ignore[] = { 0, 0, 0, 0 };
				int x = (int) bm.getBombas().get(i).getPos().getX();
				int y = (int) bm.getBombas().get(i).getPos().getY();

				g.drawImage(explosao, x * TILESIZE, y * TILESIZE, TILESIZE, TILESIZE, null);

				// impressao explosao
				for (int j = 1; j <= bm.getBombas().get(i).getRaio(); j++) {
					// y++
					if (y + j < bm.getMapa().getTamanho() - 1 && bm.getMapa().getTab()[y + j][x] != 'X' && ignore[0] != 1) {
						if (bm.getMapa().getTab()[y + j][x] == 'W')
							ignore[0] = 1;

						if (ignore[0] != 1)
							g.drawImage(explosao, x * TILESIZE, (y + j) * TILESIZE, TILESIZE, TILESIZE, null);
					} else
						ignore[0] = 1;
					// y--
					if (y - j > 0 && bm.getMapa().getTab()[y - j][x] != 'X' && ignore[1] != 1) {
						if (bm.getMapa().getTab()[y - j][x] == 'W')
							ignore[1] = 1;
 
						if (ignore[1] != 1)
							g.drawImage(explosao, x * TILESIZE, (y - j) * TILESIZE, TILESIZE, TILESIZE, null);
					} else
						ignore[1] = 1;
					// x++
					if (x + j < bm.getMapa().getTamanho() - 1 && bm.getMapa().getTab()[y][x + j] != 'X' && ignore[2] != 1) {
						if (bm.getMapa().getTab()[y][x + j] == 'W')
							ignore[2] = 1;

						if (ignore[2] != 1)
							g.drawImage(explosao, (x + j) * TILESIZE, y * TILESIZE, TILESIZE, TILESIZE, null);
					} else
						ignore[2] = 1;
					// x--
					if (x - j > 0 && bm.getMapa().getTab()[y][x - j] != 'X' && ignore[3] != 1) {
						if (bm.getMapa().getTab()[y][x - j] == 'W')
							ignore[3] = 1;

						if (ignore[3] != 1)
							g.drawImage(explosao, (x - j) * TILESIZE, y * TILESIZE, TILESIZE, TILESIZE, null);
					} else
						ignore[3] = 1;
				}
			}
		}

	}

	public void loadImages() {
		try {
			wall = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\Wall.png"));
			fixedWall = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\FixedWall.png"));
			floor = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\Floor.png"));
			jogador = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\Jogador.png"));
			bomba = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\Bomba.png"));
			explosao = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\explosao.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		for (Iterator<Jogador> it = bm.getJogadores().iterator(); it.hasNext();) {

			Jogador j = it.next();

			if (j.getEstado() != Peca.Estado.ACTIVO)
				continue;

			j.setEstadoJogador(EstadoJogador.MOVER);
			if (j.getId() == 1) {

				if (e.getKeyCode() == KeyEvent.VK_UP) {
					j.move(Direcao.CIMA, bm.getMapa());
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					j.move(Direcao.BAIXO, bm.getMapa());
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					j.move(Direcao.DIREITA, bm.getMapa());
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					j.move(Direcao.ESQUERDA, bm.getMapa());
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					bm.colocarBomba(j);
				}
			}

			else {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					j.move(Direcao.CIMA, bm.getMapa());
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					j.move(Direcao.BAIXO, bm.getMapa());
				} else if (e.getKeyCode() == KeyEvent.VK_D) {
					j.move(Direcao.DIREITA, bm.getMapa());
				} else if (e.getKeyCode() == KeyEvent.VK_A) {
					j.move(Direcao.ESQUERDA, bm.getMapa());
				} else if (e.getKeyCode() == KeyEvent.VK_Z) {
					bm.colocarBomba(j);
				}
			}
		}

		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		bm.getJogadores().get(0).setEstadoJogador(EstadoJogador.PARADO);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	ActionListener timerListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			tempo += 60;
			
			bm.updateBomba(60);

			bm.verificaJogador(60);
				
			repaint();
		}
	};
}
