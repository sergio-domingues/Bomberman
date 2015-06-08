package bomberman.logic;

import java.util.Random;

/**
 * COntrutor do Mapa
 * @author Diogo Moura
 *
 */
public class Builder {
	/**
	 * Dificuldade do Mapa a construir
	 * @author Diogo Moura
	 *
	 */
	public enum Difficulty {
		EASY, MEDIUM, HARD
	};

	private Difficulty difficulty;
	private int tamanho;
	private static final double percentagemParedes = 0.8; // percentagem do mapa
															// ocupado por
															// paredes no incio
															// do jogo
	/**
	 * Construtor do Criador de Mapa
	 * @param d Dificuldade do Mapa a ser criado
	 * @param size tamanho do mapa
	 */
	public Builder(Difficulty d, int size) {
		tamanho = size;
		difficulty = d;
	}

	/**
	 * Cria um mapa facil
	 * @return Matriz com o mapa (Paredes Fixas e Caixas)
	 */
	public char[][] createEasyMap() {
		char[][] mapa = new char[tamanho][tamanho];

		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				mapa[i][j] = ' ';
			}
		}

		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				if (j == 0 || i == 0 || i == tamanho - 1 || j == tamanho - 1) {
					mapa[i][j] = 'X';
				} else if (j % 2 == 0 && i % 2 == 0) {
					mapa[i][j] = 'X';
				}
			}
		}

		int espacosPreencher = (int) ((((this.tamanho - 2) * (this.tamanho - 2)) - (Math.floor(((tamanho - 2) / 2)) * Math.floor(((tamanho - 2) / 2)))) * percentagemParedes);
		int x, y;
		Random r = new Random();

		while (espacosPreencher > 0) {
			x = r.nextInt(tamanho);
			y = r.nextInt(tamanho);

			if (x == 0 || y == 0 || y == tamanho || x == tamanho || mapa[y][x] == 'X') {
				continue;
			} else if ((x < 3 && y < 3) || (x >= tamanho - 3 && y >= tamanho - 3) || (x < 3 && y >= tamanho - 3) || (x >= tamanho - 3 && y < 3)) {
				continue;
			} else {
				mapa[y][x] = 'W';
				espacosPreencher--;
			}

		}

		return mapa;
	}
}
