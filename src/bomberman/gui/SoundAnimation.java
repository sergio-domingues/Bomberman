package bomberman.gui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Classe que gera o som do Jogo
 * @author Diogo Moura
 *
 */
public class SoundAnimation {

	MediaPlayer player;
	String path;

	public SoundAnimation(String path) {
		this.path = path;
		new javafx.embed.swing.JFXPanel(); // forca inicializacao
		player = new MediaPlayer(new Media(path));
	}

	public MediaPlayer getPlayer() {
		return player;
	}

	public void setPlayer(MediaPlayer player) {
		this.player = player;
	}

	public void alteraVolume(int val) {
		player.setVolume(val / 100);
	}

	public void play() {
		player.play();
	}

	public void stop() {
		player.stop();
	}
}
