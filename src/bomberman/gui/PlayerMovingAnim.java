package bomberman.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.logic.Jogador;

public class PlayerMovingAnim implements Animation {

	private int tempo;
	private Image sprite;
	private int dir, lastMoveIndex = 0, UPDATERATE = 80;

	private static final int TILESIZE = PanelJogo.TILESIZE;
	public static final int DOWN = 0, LEFT = 1, RIGHT = 2, UP = 3, MOVE = 1;

	public PlayerMovingAnim(Animation.ColorPlayer color, Jogador.Direcao dir) {
		changeColor(color);
		updateDir(dir);
		this.tempo = 0;
	}

	@Override
	public void render(Graphics g, double posx, double posy) {
		int dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2;

		dx1 = (int) (posx * TILESIZE);
		dy1 = (int) (posy * TILESIZE);
		dx2 = (int) (dx1 + TILESIZE);
		dy2 = (int) (dy1 + TILESIZE);
		sx1 = (int) (this.lastMoveIndex * sprite.getWidth(null) / 4);
		sy1 = (int) (this.dir * sprite.getHeight(null) / 4);
		sx2 = (int) (sx1 + sprite.getWidth(null) / 4);
		sy2 = (int) (sy1 + sprite.getHeight(null) / 4);

		g.drawImage(sprite, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}

	@Override
	public void update(int tempo) {
		this.tempo += tempo;
		
		if (this.tempo >= UPDATERATE) {
			this.tempo -= UPDATERATE;

			if (this.lastMoveIndex == 3) {
				this.lastMoveIndex = 0;
			} else
				this.lastMoveIndex++;
		}
	}

	public void updateDir(Jogador.Direcao dir) {

		if (dir == Jogador.Direcao.BAIXO)
			this.dir = DOWN;
		else if (dir == Jogador.Direcao.CIMA)
			this.dir = UP;
		else if (dir == Jogador.Direcao.DIREITA)
			this.dir = RIGHT;
		else
			this.dir = LEFT;
		
		this.lastMoveIndex = 0;
	}

	public void changeColor(Animation.ColorPlayer color) {
		try {
			switch (color) {
			case RED:
				sprite = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerVermelho.png"));
				break;
			case YELLOW:
				sprite = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerAmarelo.png"));
				break;
			case GREEN:
				sprite = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerVerde.png"));
				break;
			case BLUE:
				sprite = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerAzul.png"));
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}
}
