package bomberman.logic;

import bomberman.logic.Pos;

/**
 * Classe Mae de todas os Componentes do jogo
 * @author Diogo Moura
 * 
 */
public class Peca {
	public static enum Estado {
		ACTIVO, INATIVO
	};

	protected Pos pos;
	protected char sigla;
	protected Estado estado;

	/**
	 * 
	 * @param x Posicao Horizontal da Peca
	 * @param y Posição Vertical da Peca
	 * @param sigla Caracter indentificador da Peça
	 */
	Peca(double x, double y, char sigla) {
		pos = new Pos(x, y);
		this.sigla = sigla;
		estado = Estado.ACTIVO;
	}

	/**
	 * Getter Sigla da Peca
	 * 
	 * @return Sigla da Peca
	 */
	public char getSigla() {
		return sigla;
	}

	/**
	 * Setter da Sigla da Peca
	 * 
	 * @param sigla
	 *            Nova Sigla
	 */
	public void setSigla(char sigla) {
		this.sigla = sigla;
	}

	/**
	 * Obtem Posicao da Peca
	 * @return Posicao da Peca
	 */
	public Pos getPos() {
		return pos;
	}

	/**
	 * Coloca a Peca numa posicao
	 * @param pos Nova Posicao
	 */
	public void setPos(Pos pos) {
		this.pos = pos;
	}

	/**
	 * Obtem o estado da Peça
	 * @return ACTIVA ou INACTIVA
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * Muda o estado da Peca
	 * @param estado ACTIVO ou INACTIVO
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	/**
	 * Verifica se um Peca esta na mesma posicao de outra
	 * 
	 * @param p
	 *            Peca a comparar
	 * @return True-Colide False- Não colide
	 */
	public boolean colide(Peca p) {
		if (this.getPos().getX() == p.getPos().getX() && this.getPos().getY() == p.getPos().getY()) {
			return true;
		} else
			return false;
	}

	/**
	 * Verifica se um Peca avista a outra, ou seja tem caminho desempedido, consoanet o mapa e o alcance da visualização
	 * @param p peca a ser avistada
	 * @param tab Mapa onde se encontram as pecas
	 * @param alcance Alcance maximo da peca
	 * @return Avista a peca pretendida
	 */
	public boolean ver(Peca p, Mapa tab, double alcance) {

		int pos;

		// Percorre Horizontal
		if (Math.abs(p.getPos().getY() - this.getPos().getY()) < 1.0) {
			// se alvo estiver mais perto que o alcance
			if (Math.abs(this.getPos().getX() - p.getPos().getX()) < alcance) {
				alcance = Math.abs(this.getPos().getX() - p.getPos().getX());
			}

			if (this.getPos().getX() < p.getPos().getX()) {
				pos = +1; // frente
			} else {
				pos = -1; // tras
			}

			for (int i = 0; i <= Math.ceil(alcance); i++) {

				if (tab.getTab()[(int) this.getPos().getY()][(int) this.getPos().getX() + (i * pos)] == 'X'
						|| tab.getTab()[(int) this.getPos().getY()][(int) this.getPos().getX() + (i * pos)] == 'W') {
					return false;
				} else if (pos < 0) {
					if ((int) Math.floor(p.getPos().getX()) == ((int) Math.floor(this.getPos().getX() + (pos * i)))) {
						return true;
					}
				} else if (pos > 0) {
					if ((int) Math.ceil(p.getPos().getX()) == ((int) Math.ceil(this.getPos().getX() + (pos * i)))) {
						return true;
					}
				}
			}

			// Percorre Verticalmente
		} else if (Math.abs(p.getPos().getX() - this.getPos().getX()) < 1.0) {

			if (Math.abs(this.getPos().getY() - p.getPos().getY()) < alcance) {
				alcance = Math.abs(this.getPos().getY() - p.getPos().getY());
			}

			if (this.getPos().getY() < p.getPos().getY()) {
				pos = +1; // baixo
			} else {
				pos = -1; // cima
			}

			for (int i = 0; i <= Math.ceil(alcance); i++) {
				if (tab.getTab()[(int) this.getPos().getY() + (i * pos)][(int) this.getPos().getX()] == 'X'||
						tab.getTab()[(int) this.getPos().getY() + (i * pos)][(int) this.getPos().getX()] == 'W') {
					return false;
				} else if (pos > 0) {
					if ((int) Math.ceil(p.getPos().getY()) == ((int) Math.ceil(this.getPos().getY() + (i * pos)))) {
						return true;
					}
				} else if (pos < 0) {
					if ((int) Math.floor(p.getPos().getY()) == ((int) Math.floor(this.getPos().getY() + (i * pos)))) {
						return true;
					}
				}
			}
		}

		return false;
	}

}
