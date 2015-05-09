package bomberman.logic;

public class Mapa {
	private char[][] tab;
	private int tamanho;

	public Mapa(int n) {
		tamanho = n;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int n) {
		tamanho = n;
	}

	/**
	 * Getter Tabuleiro de Jogo
	 * 
	 * @return Tabuleiro de Jogo
	 */
	public char[][] getTab() {
		return tab;
	}

	/**
	 * Troca a sigla do Tabuleiro numa especifica coordenada
	 * 
	 * @param x
	 *            Coordenada Horizontal
	 * @param y
	 *            Coordenada Vertical
	 * @param c
	 *            Letr para ser modificada
	 */
	public void setChar(int x, int y, char c) {
		tab[y][x] = c;
	}

	/**
	 * Setter Tabuleiro de Jogo
	 * 
	 * @param tab
	 *            Tabuleiro
	 */
	public void setTabuleiro(char[][] tab) {
		this.tab = tab;
	}
}
