package bomberman.logic;

import bomberman.gui.AnimBomb;
import bomberman.gui.AnimExplosion;
import bomberman.gui.Animation;

/**
 * Classe representativa de uma bomba
 * @author Diogo Moura
 *
 */
public class Bomba extends Peca {
	/**
	 * Estado da Bomba (Armada ou a explodir)
	 * @author Diogo Mour
	 *
	 */
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

	/**
	 * Construtor Bomba
	 * @param x Posicao Horizontal da Bomba
	 * @param y Posicao Vertical da Bomba
	 * @param sigla Sigla
	 * @param raio Raio de Explosao
	 * @param j Jogador que colocou a bomba
	 */
	Bomba(float x, float y, char sigla, int raio, Jogador j) {
		super(x, y, sigla);
		this.raio = raio;
		this.jogador = j;
		this.cronoBomba = TEMPOARMADO;
		this.estadoBomba = EstadoBomba.ARMADA;
		this.bombSetAnim = new AnimBomb(j.getColor());
	}

	/**
	 * Obtem o temporizador da bomba
	 * @return temporizador da bomba
	 */
	public double getCronoBomba() {
		return cronoBomba;
	}

	/**
	 * Decrementa o contador da bomba e atualiza o seu estado e animacao
	 * @param decremento Valor a decrementar
	 * @return Novo Estado da Bomba 2-Explodindo 1- Aramado 0- Desactivada
	 */
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

	/**
	 * Obtem o raio de explosao da bomba
	 * @return Raio em quadriculas do mapa
	 */
	public int getRaio() {
		return raio;
	}

	/**
	 * Novo Raio de Explosao
	 * @param raio Novo Raio
	 */
	public void setRaio(int raio) {
		this.raio = raio;
	}

	/**
	 * Obtem o Estado da Bomba
	 * @return Estado da Bomba
	 */
	public EstadoBomba getEstadoBomba() {
		return estadoBomba;
	}

	/**
	 * Novo Estado da Bomba
	 * @param estadoBomba Novo Estado
	 */
	public void setEstadoBomba(EstadoBomba estadoBomba) {
		this.estadoBomba = estadoBomba;
	}

	/**
	 * Obtem a Animacao da Bomba
	 * @return Animacao Actual
	 */
	public Animation getBombAnim() {
		return bombSetAnim;
	}

	/**
	 * Nova Animação para a Bomba
	 * @param bombAnim Nova Animacao
	 */
	public void setBombAnim(Animation bombAnim) {
		this.bombSetAnim = bombAnim;
	}
}
