package bomberman.logic;

public class Bomba extends Peca {
	public static enum EstadoBomba{ARMADA,EXPLODINDO};
	private static final double TEMPOARMADO=2.0;
	private static final double TEMPOEXPLOSAO= 0.5;
	
	private float CronoBomba;
	private int raio;
	private EstadoBomba estadoBomba;
	
	Bomba(float x, float y, char sigla,int raio) {
		super(x, y, sigla);
		this.raio=raio;
	}

	public float getCronoBomba() {
		return CronoBomba;
	}

	public void updateCronoBomba(float decremento) {
		CronoBomba -= decremento;
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
