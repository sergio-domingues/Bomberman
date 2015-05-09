package bomberman.logic;

public class Jogador extends Peca {
	private static int nextId = 1;
	private int id;

	public static enum EstadoJogador {
		MOVER, PARADO
	};

	private double velocidade = 0.4;
	private int vidas;
	private EstadoJogador estadoJogador;

	public double getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public EstadoJogador getEstadoJogador() {
		return estadoJogador;
	}

	public void setEstadoJogador(EstadoJogador estadoJogador) {
		this.estadoJogador = estadoJogador;
	}

	Jogador(float x, float y, char sigla) {
		super(x, y, sigla);
		id = nextId;
		nextId++;
		estadoJogador = EstadoJogador.PARADO;
	}

	static int getNextId() {
		return nextId;
	}

	int getId() {
		return id;
	}
}
