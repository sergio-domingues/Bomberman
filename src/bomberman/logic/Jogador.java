package bomberman.logic;

import java.util.ArrayList;

public class Jogador extends Peca {
	public static enum Direcao {
		DIREITA, ESQUERDA, CIMA, BAIXO
	};

	private Direcao ultimaDirecao;
	private int nrBombas = 2; // TODO Alterar pra valor desejadoe

	private static int nextId = 1;
	private int id;

	public static enum EstadoJogador {
		MOVER, PARADO
	};

	private double velocidade = 0.5;
	private int vidas= 2;
	private EstadoJogador estadoJogador;
	private int raioBomba = 2;
	private ArrayList<PowerUp> powerUps;

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
	}

	static int getNextId() {
		return nextId;
	}

	public int getId() {
		return id;
	}

	public void move(Direcao d, Mapa mapa) {
		if (d == Direcao.CIMA) {
			if (this.pos.getX() - Math.floor(this.pos.getX()) == 0) {
				if ((int) (pos.getY() - velocidade) > 0) {
					if (mapa.getTab()[(int) Math.floor(pos.getY() - velocidade)][(int) pos.getX()] == ' ') {
						this.pos.setY(pos.getY() - velocidade);
					}
				}
			}
			this.ultimaDirecao = Direcao.CIMA;

		} else if (d == Direcao.BAIXO) {
			if (this.pos.getX() - Math.floor(this.pos.getX()) == 0) {
				if ((int) (pos.getY() + velocidade) < mapa.getTamanho()) {
					if (mapa.getTab()[(int) Math.ceil(pos.getY() + velocidade)][(int) pos.getX()] == ' ') {
						this.pos.setY(pos.getY() + velocidade);
					}
				}
			}
			this.ultimaDirecao = Direcao.BAIXO;

		} else if (d == Direcao.ESQUERDA) {
			if (this.pos.getY() - Math.floor(this.pos.getY()) == 0) {
				if ((int) (pos.getX() - velocidade) > 0) {
					if (mapa.getTab()[(int) pos.getY()][(int) Math.floor(pos.getX() - velocidade)] == ' ') {
						this.pos.setX(pos.getX() - velocidade);
					}
				}
			}
			this.ultimaDirecao = Direcao.ESQUERDA;

		} else if (d == Direcao.DIREITA) {
			if (this.pos.getY() - Math.floor(this.pos.getY()) == 0) {
				if ((int) (pos.getX() + velocidade) < mapa.getTamanho()) {
					if (mapa.getTab()[(int) pos.getY()][(int) Math.ceil(pos.getX() + velocidade)] == ' ') {
						this.pos.setX(pos.getX() + velocidade);
					}
				}
			}
			this.ultimaDirecao = Direcao.DIREITA;
		}
	}

	public void decBomba() {
		nrBombas--;
	}

	public void adiBomba() {
		nrBombas++;
	}

	public void decVidas(){
		vidas--;
	}
	
	public void addVidas(){
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
}
