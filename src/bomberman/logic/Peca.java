package bomberman.logic;

import bomberman.logic.Pos;

public class Peca {
	public static enum Estado {
		ACTIVO, INATIVO
	};

	private Pos pos;
	private char sigla;
	private Estado estado;

	Peca(int x, int y, char sigla) {
		pos = new Pos(x, y);
		this.sigla = sigla;
		estado=Estado.ACTIVO;
	}

	public void move(int x, int y) {
		pos.setX(x);
		pos.setY(y);
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
	
}
