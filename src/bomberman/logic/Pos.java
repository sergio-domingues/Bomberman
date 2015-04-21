package bomberman.logic;

/**
 * Classe auxiliar para facilitar a gestao das posicoes dos diferentes objectos do tabuleiro de jogo
 * @author Diogo Moura
 *
 */
public class Pos {
	private float x, y;

	/**
	 * Define uma coordenada cartesiana
	 * 
	 * @param x
	 *            Coordenada Horizontal
	 * @param y
	 *            Coordenada Vertical
	 */
	public Pos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter Coordenada Horizontal
	 * 
	 * @return Coordenada Horizontal
	 */
	public float getX() {
		return x;
	}

	/**
	 * Setter Coordenada Horizontal
	 * 
	 * @param x
	 *            Coordenada Horizontal
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Getter Coordenada Vertical
	 * 
	 * @return Coordenada Vertical
	 */
	public float getY() {
		return y;
	}

	/**
	 * Setter Coordenada Vertical
	 * 
	 * @param y
	 *            Coordenada Vertical
	 */
	public void setY(float y) {
		this.y = y;
	}

}
