package bomberman.logic;

import bomberman.gui.AnimBomb;
import bomberman.gui.AnimExplosion;
import bomberman.gui.Animation;

public class Bomba extends Peca {
	public static enum EstadoBomba {
		ARMADA, EXPLODINDO
	};

	public static final double TEMPOARMADO = 2000;
	public static final double TEMPOEXPLOSAO = 800;

	private double cronoBomba;
	private int raio;
	private EstadoBomba estadoBomba;
	private Animation bombSetAnim;

	Jogador jogador;

	Bomba(float x, float y, char sigla, int raio, Jogador j) {
		super(x, y, sigla);
		this.raio = raio;
		this.jogador = j;
		this.cronoBomba = TEMPOARMADO;
		this.estadoBomba = EstadoBomba.ARMADA;
		this.bombSetAnim = new AnimBomb(j.getColor());
	}

	public double getCronoBomba() {
		return cronoBomba;
	}

	//TODO INICIALIZAR ANIMACAO EXPLOSAO
	public int updateCronoBomba(double decremento) {
		if (this.estadoBomba == EstadoBomba.ARMADA) {
			cronoBomba -= decremento;
			if (cronoBomba <= 0) {
				estadoBomba = EstadoBomba.EXPLODINDO;
				this.bombSetAnim = new AnimExplosion(this.jogador.getColor());
				cronoBomba = TEMPOEXPLOSAO;
				return 1;
			}
		} else if (this.estadoBomba == EstadoBomba.EXPLODINDO) {
			cronoBomba -= decremento;
			if (cronoBomba <= 0) {
				this.estado = Peca.Estado.INATIVO;
				jogador.addBomba();
				return 2;
			}
		}		
		return 0;
	}

	public int getRaio() {
		return raio;
	}

	public void setRaio(int raio) {
		this.raio = raio;
	}

	public EstadoBomba getEstadoBomba() {
		return estadoBomba;
	}

	public void setEstadoBomba(EstadoBomba estadoBomba) {
		this.estadoBomba = estadoBomba;
	}

	public Animation getBombAnim() {
		return bombSetAnim;
	}

	public void setBombAnim(Animation bombAnim) {
		this.bombSetAnim = bombAnim;
	}
}
