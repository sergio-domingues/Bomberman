package bomberman.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import bomberman.connection.Connection;
import bomberman.gui.AnimJogador.Instruction;
import bomberman.logic.Bomba.EstadoBomba;
import bomberman.logic.Bomberman;
import bomberman.logic.Jogador;
import bomberman.logic.Jogador.Direcao;
import bomberman.logic.Jogador.EstadoJogador;
import bomberman.logic.Peca;

@SuppressWarnings("serial")
public class PanelJogo extends JPanel implements KeyListener {

	public static final int TILESIZE = 50;
	public static final int DOWN = 0, LEFT = 1, RIGHT = 2, UP = 3, STOP = 0, MOVE = 1;

	private Bomberman bm;
	private Timer timer;
	private long tempo = 0;
	private int dir = 2, color = 0;
	private boolean playBackMusic = true;

	private static final int UPDATERATE = 70;// tempo de refresh objectos
	private static ArrayList<AnimJogador> animacoes = new ArrayList<AnimJogador>();

	private BufferedImage box, wall, floor, powerupBomb, powerupSpeed, powerupRange, pwup;

	public PanelJogo(Bomberman bm, JFrame frame) {
		this.setBounds(0, 0, 50 * bm.getMapa().getTamanho() + 20, 50 * bm.getMapa().getTamanho() + 20);
		setFocusable(true);
		this.loadImages();
		this.setLayout(new FlowLayout());
		this.setVisible(true);
		this.bm = bm;
		this.addKeyListener(this);
		timer = new Timer(UPDATERATE, timerListener);
		timer.start();

		for (int i = 0; i < bm.getJogadores().size(); i++) {
			animacoes.add(new AnimJogador(Connection.getInstance().getConnections()[i]));
		}
	}

	// converte indice dir para TIPO DIRECCAO correspondente
	public int parseDirection(Jogador.Direcao dir) {

		if (dir == Jogador.Direcao.BAIXO)
			return DOWN;
		else if (dir == Jogador.Direcao.CIMA)
			return UP;
		else if (dir == Jogador.Direcao.ESQUERDA)
			return LEFT;
		else
			return RIGHT;
	}

	public Jogador.Direcao parseDirection(int dir) {

		if (dir == DOWN)
			return Jogador.Direcao.BAIXO;
		else if (dir == UP)
			return Jogador.Direcao.CIMA;
		else if (dir == LEFT)
			return Jogador.Direcao.ESQUERDA;
		else
			return Jogador.Direcao.DIREITA;
	}

	// converte indice color para TIPO colorPlayer correspondente
	public Animation.ColorPlayer parseColor(int color) {

		if (color == 0)
			return Animation.ColorPlayer.RED;
		else if (color == 1)
			return Animation.ColorPlayer.BLUE;
		else if (color == 2)
			return Animation.ColorPlayer.GREEN;
		else
			return Animation.ColorPlayer.YELLOW;
	}

	public int parseColor(Animation.ColorPlayer color) {

		if (color == Animation.ColorPlayer.RED)
			return 0;
		else if (color == Animation.ColorPlayer.BLUE)
			return 1;
		else if (color == Animation.ColorPlayer.GREEN)
			return 2;
		else
			return 3;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // limpa fundo ...

		BufferedImage img = floor; // default image

		int xi, yi;

		for (int i = 0; i < bm.getMapa().getTamanho(); i++) {
			for (int j = 0; j < bm.getMapa().getTamanho(); j++) {

				if (bm.getMapa().getTab()[i][j] == 'X') {
					img = wall;
				} else if (bm.getMapa().getTab()[i][j] == ' ') {
					img = floor;
				} else if (bm.getMapa().getTab()[i][j] == 'W') {
					img = box;
				} else if (bm.getMapa().getTab()[i][j] == 'E')
					img = powerupBomb;
				else if (bm.getMapa().getTab()[i][j] == 'R')
					img = powerupRange;
				else if (bm.getMapa().getTab()[i][j] == 'S')
					img = powerupSpeed;

				xi = j * TILESIZE;
				yi = i * TILESIZE;

				// TODO TIRAR ESTA INSTRUCAO PARA TORNAR ISTO MAIS EFICIENTE
				g.drawImage(floor, xi, yi, TILESIZE, TILESIZE, null);
				g.drawImage(img, xi, yi, TILESIZE, TILESIZE, null);
			}
		}

		// impressao bombas
		for (int i = 0; i < bm.getBombas().size(); i++) {
			if (bm.getBombas().get(i).getEstadoBomba() != EstadoBomba.EXPLODINDO) {
				double x, y;

				x = bm.getBombas().get(i).getPos().getX();
				y = bm.getBombas().get(i).getPos().getY();

				bm.getBombas().get(i).getBombAnim().render(g, x, y);
			}
		}

		double dx1, dy1;

		// impressao jogador
		for (int i = 0; i < bm.getJogadores().size(); i++) {

			if (bm.getJogadores().get(i).getEstado() != Peca.Estado.ACTIVO)
				continue;

			dx1 = bm.getJogadores().get(i).getPos().getX();
			dy1 = bm.getJogadores().get(i).getPos().getY();

			bm.getJogadores().get(i).getAnimation().render(g, dx1, dy1 - 0.20);
		}

		// impressao bombas
		for (int i = 0; i < bm.getBombas().size(); i++) {

			if (bm.getBombas().get(i).getEstado() == Peca.Estado.INATIVO)
				continue;

			if (bm.getBombas().get(i).getEstadoBomba() == EstadoBomba.EXPLODINDO) {

				int ignore[] = { 0, 0, 0, 0 };
				int x = (int) bm.getBombas().get(i).getPos().getX();
				int y = (int) bm.getBombas().get(i).getPos().getY();

				double posx = bm.getBombas().get(i).getPos().getX();
				double posy = bm.getBombas().get(i).getPos().getY();

				bm.getBombas().get(i).getBombAnim().render(g, posx, posy);

				// impressao explosao
				for (int j = 1; j <= bm.getBombas().get(i).getRaio(); j++) {
					// y++
					if (y + j < bm.getMapa().getTamanho() - 1 && bm.getMapa().getTab()[y + j][x] != 'X' && ignore[0] != 1) {
						if (bm.getMapa().getTab()[y + j][x] == 'W') {
							ignore[0] = 1;
							bm.getBombas().get(i).getBombAnim().render(g, posx, posy + j);
						}

						if (ignore[0] != 1)
							bm.getBombas().get(i).getBombAnim().render(g, posx, posy + j);

					} else
						ignore[0] = 1;
					// y--
					if (y - j > 0 && bm.getMapa().getTab()[y - j][x] != 'X' && ignore[1] != 1) {
						if (bm.getMapa().getTab()[y - j][x] == 'W') {
							ignore[1] = 1;
							bm.getBombas().get(i).getBombAnim().render(g, posx, posy - j);
						}

						if (ignore[1] != 1)
							bm.getBombas().get(i).getBombAnim().render(g, posx, posy - j);

					} else
						ignore[1] = 1;
					// x++
					if (x + j < bm.getMapa().getTamanho() - 1 && bm.getMapa().getTab()[y][x + j] != 'X' && ignore[2] != 1) {
						if (bm.getMapa().getTab()[y][x + j] == 'W') {
							ignore[2] = 1;
							bm.getBombas().get(i).getBombAnim().render(g, posx + j, posy);
						}

						if (ignore[2] != 1)
							bm.getBombas().get(i).getBombAnim().render(g, posx + j, posy);

					} else
						ignore[2] = 1;
					// x--
					if (x - j > 0 && bm.getMapa().getTab()[y][x - j] != 'X' && ignore[3] != 1) {
						if (bm.getMapa().getTab()[y][x - j] == 'W') {
							ignore[3] = 1;
							bm.getBombas().get(i).getBombAnim().render(g, posx - j, posy);
						}

						if (ignore[3] != 1)
							bm.getBombas().get(i).getBombAnim().render(g, posx - j, posy);

					} else
						ignore[3] = 1;
				}
			}
		}
		// bm.imprimeMapa(bm.getMapa().getTab(), bm.getMapa().getTamanho());
	}

	public void loadImages() {
		try {
			box = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\box.png"));
			wall = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\wall.png"));
			floor = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\floor.png"));
			powerupBomb = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\bombpwup.png"));
			powerupRange = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\rangepwup.png"));
			powerupSpeed = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\speedpwup.png"));

		} catch (IOException e) {
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

				bm.checkPowerUp(j);
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

				bm.checkPowerUp(j);
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		bm.getJogadores().get(0).setEstadoJogador(EstadoJogador.PARADO);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public boolean isPlayBackMusic() {
		return playBackMusic;
	}

	public void setPlayBackMusic(boolean playBackMusic) {
		this.playBackMusic = playBackMusic;
	}

	ActionListener timerListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			tempo += UPDATERATE;
			bm.updateElapsedTime(UPDATERATE);


			for (int i = 0; i < animacoes.size(); i++) {
				if (animacoes.get(i).getNextInstruction() == Instruction.MOVE) {

					if (bm.getJogadores().get(i).getEstadoJogador() == Jogador.EstadoJogador.PARADO)
						bm.getJogadores().get(i).setEstadoJogador(EstadoJogador.MOVER);

					bm.moveJogador(bm.getJogadores().get(i), animacoes.get(i).getDir());
					bm.checkPowerUp(bm.getJogadores().get(i));

				} else if (animacoes.get(i).getNextInstruction() == Instruction.STOP) {
					
					if (bm.getJogadores().get(i).getEstadoJogador() != Jogador.EstadoJogador.PARADO)
						bm.getJogadores().get(i).setEstadoJogador(EstadoJogador.PARADO);					
				}
				if (animacoes.get(i).getNextInstruction() == Instruction.PLANTBOMB) {
					bm.colocarBomba(bm.getJogadores().get(i));
				}

			}

			// UPDATE ANIMACOES BOMBAS
			for (int i = 0; i < bm.getBombas().size(); i++) {
				if (bm.getBombas().get(i).getEstado() == Peca.Estado.ACTIVO)
					bm.getBombas().get(i).getBombAnim().update(UPDATERATE);
			}

			// UPDATE ANIMACOES JOGADORES
			for (int i = 0; i < bm.getJogadores().size(); i++) {
				if (bm.getJogadores().get(i).getEstado() == Peca.Estado.ACTIVO)
					bm.getJogadores().get(i).getAnimation().update(UPDATERATE);
			}

			bm.updateBomba(UPDATERATE);
			bm.verificaJogador(UPDATERATE);
			repaint();
		}
	};
}
