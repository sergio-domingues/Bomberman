package bomberman.logic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe responsavel por guardar e carregar do ficheiro o Highscore
 * @author Utilizador
 *
 */
public class Highscore {
	private int score = -1;
	private File file;

	/**
	 * Carrega o Highscore do ficheiro "highscore.dat"
	 */
	public Highscore() {
		file = new File("resources/highscore.dat");
		loadScore();
		

	}

	/**
	 * Guarda o score num ficheiro
	 */
	public void saveScore() {
		FileWriter out = null;
		try {
			out = new FileWriter(file);
			out.write(score);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carrega o score do ficheiro
	 */
	private void loadScore() {
		FileReader in = null;
		try {
			in = new FileReader(file);
			score = in.read();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtem o score
	 * @returnScore
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Atribui um novo score ao highscore
	 * @param score novo highscore
	 */
	public void setScore(int score) {
		this.score = score;
	}
}
