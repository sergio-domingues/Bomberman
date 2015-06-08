package bomberman.logic;

/**
 * Classe auxiliar para facilitar a gestao das posicoes dos diferentes objectos do tabuleiro de jogo
 * @author Diogo Moura
 *
 */
public class Pos {
	private double x, y;
 
	/**
	 * Define uma coordenada cartesiana
	 * 
	 * @param x Coordenada Horizontal
	 * @param y Coordenada Vertical
	 */
	public Pos(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter Coordenada Horizontal
	 * 
	 * @return Coordenada Horizontal
	 */
	public double getX() {
		return x;
	}

	/**
	 * Setter Coordenada Horizontal
	 * 
	 * @param x
	 *            Coordenada Horizontal
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Getter Coordenada Vertical
	 * 
	 * @return Coordenada Vertical
	 */
	public double getY() {
		return y;
	}

	/**
	 * Setter Coordenada Vertical
	 * 
	 * @param d
	 *            Coordenada Vertical
	 */
	public void setY(double d) {
		this.y = d;
	}

}
