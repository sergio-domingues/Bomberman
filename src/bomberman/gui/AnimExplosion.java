package bomberman.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bomberman.logic.Bomba;

/**
 * Animacao da Explosao da Bomba quando esta est� no estado EXPLODINDO
 * @author Diogo Moura
 *
 */
public class AnimExplosion implements Animation {

	private int tempo;
	private Image sprite;
	private int lastIndex = 0;
	private static final int TILESIZE = PanelJogo.TILESIZE;
	private SoundAnimation player = new SoundAnimation(new File(System.getProperty("user.dir") + "\\resources\\Bomb.mp3").toURI().toString());
	
	public AnimExplosion(ColorPlayer color) {
		tempo = 0;
		changeColor(color);
		player.play();
		player.getPlayer().setVolume(0.1);
	}

	@Override
	public void render(Graphics g, double posx, double posy) {
		int dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2;

		dx1 = (int) (posx * TILESIZE);
		dy1 = (int) (posy * TILESIZE);
		dx2 = (int) (dx1 + TILESIZE);
		dy2 = (int) (dy1 + TILESIZE);
		sx1 = (int) (lastIndex * sprite.getWidth(null) / 7);
		sy1 = (int) (0);
		sx2 = (int) (sx1 + sprite.getWidth(null) / 7);
		sy2 = (int) (sy1 + sprite.getHeight(null));

		g.drawImage(sprite, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}

	@Override
	public void update(int tempo) {
		this.tempo += tempo;
		
		if (this.tempo >= Bomba.TEMPOEXPLOSAO / 7) {
			this.tempo -= Bomba.TEMPOEXPLOSAO / 7;
			lastIndex++;
		}	

	}
	
	public void changeColor(Animation.ColorPlayer color) {
		try {
			switch (color) {
			case RED:
				sprite = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\explosion.png"));
				break;
			case YELLOW:
				sprite = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\explosion.png"));
				break;
			case GREEN:
				sprite = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\explosion.png"));
				break;
			case BLUE:
				sprite = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\explosion.png"));
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SoundAnimation getPlayer() {
		return player;
	}

	public void setPlayer(SoundAnimation player) {
		this.player = player;
	}

}
