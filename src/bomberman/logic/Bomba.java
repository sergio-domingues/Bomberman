package bomberman.logic;

public class Bomba extends Peca {
	public static enum EstadoBomba {
		ARMADA, EXPLODINDO
	};

	public static final double TEMPOARMADO = 2000;
	public static final double TEMPOEXPLOSAO = 800;

	private double cronoBomba;
	private int raio;
	private EstadoBomba estadoBomba;

	Jogador jogador;

	Bomba(float x, float y, char sigla, int raio, Jogador j) {
		super(x, y, sigla);
		this.raio = raio;
		this.jogador = j;
		this.cronoBomba = TEMPOARMADO;
		this.estadoBomba = EstadoBomba.ARMADA;
	}

	public double getCronoBomba() {
		return cronoBomba;
	}

	public int updateCronoBomba(double decremento) {
		if (this.estadoBomba == EstadoBomba.ARMADA) {
			cronoBomba -= decremento;
			if (cronoBomba <= 0) {
				estadoBomba = EstadoBomba.EXPLODINDO;
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

}
