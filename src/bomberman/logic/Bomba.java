package bomberman.logic;

public class Bomba extends Peca {
	public static enum EstadoBomba {
		ARMADA, EXPLODINDO
	};

	private static final double TEMPOARMADO = 2.0;
	private static final double TEMPOEXPLOSAO = 0.5;

	private double cronoBomba;
	private int raio;
	private EstadoBomba estadoBomba;

	Jogador jogador;

	Bomba(float x, float y, char sigla, int raio, Jogador j) {
		super(x, y, sigla);
		this.raio = raio;
		this.jogador = j;
		this.cronoBomba = TEMPOARMADO;
	}

	public double getCronoBomba() {
		return cronoBomba;
	}

	public void updateCronoBomba(double decremento) {
		cronoBomba -= decremento;
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
