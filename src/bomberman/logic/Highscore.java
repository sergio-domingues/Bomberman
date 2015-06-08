package bomberman.logic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Highscore {
	private int score = -1;
	private File file;

	public Highscore() {
		file = new File("resources/highscore.dat");
		loadScore();
		

	}

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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
