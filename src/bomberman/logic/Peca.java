package bomberman.logic;

import bomberman.logic.Pos;

public class Peca {
	public static enum Estado {
		ACTIVO, INATIVO
	};

	protected Pos pos;
	protected char sigla;
	protected Estado estado;

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

	public Pos getPos() {
		return pos;
	}

	public void setPos(Pos pos) {
		this.pos = pos;
	}

	public Estado getEstado() {
		return estado;
	}

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
