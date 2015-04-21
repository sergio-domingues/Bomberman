package bomberman.logic;

public class Jogador extends Peca{
	public static enum EstadoJogador {MOVER,PARADO};
	private float velocidade;
	private int vidas;
	private EstadoJogador estadoJogador ;
	
	public float getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(float velocidade) {
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
		// TODO Auto-generated constructor stub
	}
	
}
