package bomberman.logic;

import java.util.ArrayList;

public class Jogador extends Peca {
	public static enum Direcao {
		DIREITA, ESQUERDA, CIMA, BAIXO
	};

	private Direcao ultimaDirecao;
	private int nrBombas = 2; // TODO Alterar pra valor desejado

	private static int nextId = 1;
	private int id;

	public static enum EstadoJogador {
		MOVER, PARADO
	};
	
	public static enum EstadoVulnerabilidade{
		INVULNERAVEL, VULNERAVEL
	};

	// apenas sao possiveis valores cujo seu somatorio dê origem ao valor 1(exacto)
	private double velocidade = 0.2; 
	private int vidas = 2;
	private int tempo_invulneravel = 1500;
	private EstadoJogador estadoJogador;
	private EstadoVulnerabilidade estadoVuln;
	
	private int raioBomba = 2;
	private ArrayList<PowerUp> powerUps;

//=======================================================
	public EstadoVulnerabilidade getEstadoVuln() {
		return estadoVuln;
	}

	public void setEstadoVuln(EstadoVulnerabilidade estadoVuln) {
		this.estadoVuln = estadoVuln;
	}
	
	public double getVelocidade() {
		return velocidade;
	}

	public Direcao getUltimaDirecao() {
		return ultimaDirecao;
	}

	public void setUltimaDirecao(Direcao ultimaDirecao) {
		this.ultimaDirecao = ultimaDirecao;
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
		estadoVuln = EstadoVulnerabilidade.VULNERAVEL;
	}
	
	public void updateTempoJogador(int decremento){
		
		this.tempo_invulneravel -= decremento;
		
		if(this.tempo_invulneravel <= 0){
			this.tempo_invulneravel = 1500;
			this.estadoVuln = EstadoVulnerabilidade.VULNERAVEL;
			System.out.println("renova tempo");
		}		
	}
	
	public void updateVelocidade(){	
		
		if( this.velocidade < 0.25 ){
			this.velocidade = 0.25;		
		}
		else if(this.velocidade < 0.50){
			this.velocidade = 0.50;
		}
		else if (this.velocidade < 1){
			this.velocidade = 1;
		}
	}
	
	public void updateRangeBomba(int tamanho_mapa){
		
		if(this.raioBomba + 1 < tamanho_mapa - 2)
			this.raioBomba++;		
	}
	

	static int getNextId() {
		return nextId;
	}

	public int getId() {
		return id;
	}

	public void move(Direcao d, Mapa mapa) {

		double x, y, deltax, deltay, deltay_ceil;

		// arredondamento correctivo : 2.99999 -> 3.0 | 2.00003 = 2.0
		x = Math.round(this.pos.getX() * 100.0) / 100.0;
		y = Math.round(this.pos.getY() * 100.0) / 100.0;
		deltax = x - Math.floor(x);
		deltay = y - Math.floor(y);
		deltay_ceil = Math.ceil(y) - y;
//
//		System.out.println("x,y:");
//		System.out.println(x);
//		System.out.println(y);
//		System.out.println("dx,dy:");
//		System.out.println(deltax);
//		System.out.println(deltay);

		if (d == Direcao.CIMA) {
			if (deltax == 0) { // coincide com inicio da celula
				if ((int) (y - velocidade) > 0) {
					if (mapa.getTab()[(int) Math.floor(y - velocidade)][(int) x] == ' ') {
						this.pos.setY(y - velocidade);
					}
				}
			} else if (deltax < 0.5 && deltax > 0.0 && mapa.getTab()[(int) (y - 1)][(int) Math.floor(x)] == ' ')
				this.move(Direcao.ESQUERDA, mapa);
			else if (deltax > 0.5 && deltax < 1.0 && mapa.getTab()[(int) (y - 1)][(int) Math.ceil(x)] == ' ')
				this.move(Direcao.DIREITA, mapa);

			this.ultimaDirecao = Direcao.CIMA;

		} else if (d == Direcao.BAIXO) {
			if (deltax == 0) {
				if ((int) (y + velocidade) < mapa.getTamanho()) {
					if (mapa.getTab()[(int) Math.ceil(y + velocidade)][(int) x] == ' ') {
						this.pos.setY(y + velocidade);
					}
				}
			} else if (deltax < 0.5 && deltax > 0.0 && mapa.getTab()[(int) (y + 1)][(int) Math.floor(x)] == ' ')
				this.move(Direcao.ESQUERDA, mapa);
			else if (deltax > 0.5 && deltax < 1.0 && mapa.getTab()[(int) (y + 1)][(int) Math.ceil(x)] == ' ')
				this.move(Direcao.DIREITA, mapa);

			this.ultimaDirecao = Direcao.BAIXO;

		} else if (d == Direcao.ESQUERDA) {
			if (deltay == 0) {
				if ((int) (x - velocidade) > 0) {
					if (mapa.getTab()[(int) y][(int) Math.floor(x - velocidade)] == ' ') {
						this.pos.setX(x - velocidade);
					}
				}
			} else if (deltay > 0.0 && deltay < 0.5 && mapa.getTab()[(int) Math.floor(y)][(int) (x - 1)] == ' ')
				this.move(Direcao.CIMA, mapa);
			else if (deltay > 0.5 && deltay < 1.0 && mapa.getTab()[(int) Math.ceil(y)][(int) (x - 1)] == ' ')
				this.move(Direcao.BAIXO, mapa);

			this.ultimaDirecao = Direcao.ESQUERDA;

		} else if (d == Direcao.DIREITA) {
			if (deltay == 0) {
				if ((int) (x + velocidade) < mapa.getTamanho()) {
					if (mapa.getTab()[(int) y][(int) Math.ceil(x + velocidade)] == ' ') {
						this.pos.setX(x + velocidade);
					}
				}
			} else if (deltay_ceil > 0.0 && deltay_ceil < 0.5 && mapa.getTab()[(int) Math.ceil(y)][(int) (x + 1)] == ' ')
				this.move(Direcao.BAIXO, mapa);
			else if (deltay_ceil > 0.5 && deltay_ceil < 1.0 && mapa.getTab()[(int) Math.floor(y)][(int) (x + 1)] == ' ')
				this.move(Direcao.CIMA, mapa);

			this.ultimaDirecao = Direcao.DIREITA;
		}
	}

	public void decBomba() {
		nrBombas--;
	}

	public void adiBomba() {
		nrBombas++;
	}

	public void decVidas() {
		vidas--;
	}

	public void addVidas() {
		vidas++;
	}

	public int getNrBombas() {
		return nrBombas;
	}

	public Bomba armarBomba() {
		Bomba temp = new Bomba(Math.round(this.pos.getX()), Math.round(this.pos.getY()), 'B', this.raioBomba, this);
		this.nrBombas--;
		return temp;
	}

	public ArrayList<PowerUp> getPowerUps() {
		return powerUps;
	}

	public void setPowerUps(ArrayList<PowerUp> powerUps) {
		this.powerUps = powerUps;
	}

	public int getTempo_invulneravel() {
		return tempo_invulneravel;
	}

	public void setTempo_invulneravel(int tempo_invulneravel) {
		this.tempo_invulneravel = tempo_invulneravel;
	}
}
