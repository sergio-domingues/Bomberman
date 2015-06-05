package bomberman.logic;

import java.util.ArrayList;

import bomberman.gui.Animation;
import bomberman.gui.PlayerMovingAnim;
import bomberman.gui.PlayerStaticAnim;

public class Jogador extends Peca {

	// velocidades possiveis
	private static final double velocidade_1 = 0.2, velocidade_2 = 0.25, velocidade_3 = 0.5, velocidade_4 = 1;

	public static enum Direcao {
		DIREITA, ESQUERDA, CIMA, BAIXO
	};

	private Animation.ColorPlayer color;
	private Direcao ultimaDirecao;
	private int nrBombas = 2; // TODO Alterar pra valor desejado
	private static int nextId = 1;
	private int id;
	private Animation animation;

	public static enum EstadoJogador {
		MOVER, PARADO
	};

	public static enum EstadoVulnerabilidade {
		INVULNERAVEL, VULNERAVEL
	};

	// apenas sao possiveis valores cujo seu somatorio dê origem ao valor
	// 1(exacto)
	private double velocidade = velocidade_1;
	private int vidas = 2;
	private int tempo_invulneravel = 2500;
	private EstadoJogador estadoJogador;
	private EstadoVulnerabilidade estadoVuln;

	private int raioBomba = 2;
	private ArrayList<PowerUp> powerUps;

	// =======================================================
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
		if (estadoJogador == EstadoJogador.PARADO) {
			this.animation = new PlayerStaticAnim(color, ultimaDirecao);
		} else if (estadoJogador == EstadoJogador.MOVER) {
			this.animation = new PlayerMovingAnim(color, ultimaDirecao);
		}
	}

	// TODO INICIALIZAR THIS.PLAYINGMOVEANIM
	Jogador(float x, float y, char sigla) {
		super(x, y, sigla);
		id = nextId;
		nextId++;
		estadoJogador = EstadoJogador.PARADO;
		estadoVuln = EstadoVulnerabilidade.VULNERAVEL;
		color = parseColor(id - 1);
		ultimaDirecao = parseDirection(id - 1);
		animation = new PlayerStaticAnim(color, ultimaDirecao);
	}

	public void updateTempoJogador(int decremento) {

		this.tempo_invulneravel -= decremento;

		if (this.tempo_invulneravel <= 0) {
			this.tempo_invulneravel = 1500;
			this.estadoVuln = EstadoVulnerabilidade.VULNERAVEL;
			System.out.println("renova tempo");
		}
	}

	// velocidadePowerUp associated
	public void updateVelocidade() {
		if (this.velocidade < velocidade_2) {
			this.velocidade = velocidade_2;
		} else if (this.velocidade < velocidade_3) {
			this.velocidade = velocidade_3;
		} else if (this.velocidade < velocidade_4) {
			this.velocidade = velocidade_4;
		}
	}

	// incRangePowerUp associated
	public void updateRangeBomba(int tamanho_mapa) {
		// -2 -> parede
		if (this.raioBomba + 1 < tamanho_mapa - 2)
			this.raioBomba++;
	}

	static int getNextId() {
		return nextId;
	}

	public int getId() {
		return id;
	}

	public void move(Direcao d, Mapa mapa) {

		double x, y, deltax, deltay;// , deltay_ceil;

		// arredondamento correctivo : 2.99999 -> 3.0 | 2.00003 = 2.0
		x = Math.round(this.pos.getX() * 100.0) / 100.0;
		y = Math.round(this.pos.getY() * 100.0) / 100.0;
		deltax = x - Math.floor(x);
		deltay = y - Math.floor(y);
		// deltay_ceil = Math.ceil(y) - y;

		if (d == Direcao.CIMA) {
			if (deltax == 0) { // coincide com inicio da celula
				if ((int) (y - velocidade) > 0) {
					if (mapa.getTab()[(int) Math.floor(y - velocidade)][(int) x] == ' '
							|| mapa.getTab()[(int) Math.floor(y - velocidade)][(int) x] == 'P') {
						this.pos.setY(y - velocidade);
					}
				}
			} else if (deltax < 0.5 && deltax > 0.0
					&& (mapa.getTab()[(int) (y - 1)][(int) Math.floor(x)] == ' ' || mapa.getTab()[(int) (y - 1)][(int) Math.floor(x)] == 'P'))
				this.move(Direcao.ESQUERDA, mapa);
			else if (deltax > 0.5 && deltax < 1.0
					&& (mapa.getTab()[(int) (y - 1)][(int) Math.ceil(x)] == ' ' || mapa.getTab()[(int) (y - 1)][(int) Math.ceil(x)] == ' '))
				this.move(Direcao.DIREITA, mapa);

			this.ultimaDirecao = Direcao.CIMA;

		} else if (d == Direcao.BAIXO) {
			if (deltax == 0) {
				if ((int) (y + velocidade) < mapa.getTamanho()) {
					if (mapa.getTab()[(int) Math.ceil(y + velocidade)][(int) x] == ' '
							|| mapa.getTab()[(int) Math.ceil(y + velocidade)][(int) x] == 'P') {
						this.pos.setY(y + velocidade);
					}
				}
			} else if (deltax > 0.0 && deltax < 0.5
					&& (mapa.getTab()[(int) (y + 1)][(int) Math.floor(x)] == ' ' || mapa.getTab()[(int) (y + 1)][(int) Math.floor(x)] == 'P'))
				this.move(Direcao.ESQUERDA, mapa);
			else if (deltax > 0.5 && deltax < 1.0
					&& (mapa.getTab()[(int) (y + 1)][(int) Math.ceil(x)] == ' ' || mapa.getTab()[(int) (y + 1)][(int) Math.ceil(x)] == 'P'))
				this.move(Direcao.DIREITA, mapa);

			this.ultimaDirecao = Direcao.BAIXO;

		} else if (d == Direcao.ESQUERDA) {
			if (deltay == 0) {
				if ((int) (x - velocidade) > 0) {
					if (mapa.getTab()[(int) y][(int) Math.floor(x - velocidade)] == ' '
							|| mapa.getTab()[(int) y][(int) Math.floor(x - velocidade)] == 'P') {
						this.pos.setX(x - velocidade);
					}
				}
			} else if (deltay > 0.0 && deltay < 0.5
					&& (mapa.getTab()[(int) Math.floor(y)][(int) (x - 1)] == ' ' || mapa.getTab()[(int) Math.floor(y)][(int) (x - 1)] == 'P'))
				this.move(Direcao.CIMA, mapa);
			else if (deltay > 0.5 && deltay < 1.0
					&& (mapa.getTab()[(int) Math.ceil(y)][(int) (x - 1)] == ' ' || mapa.getTab()[(int) Math.ceil(y)][(int) (x - 1)] == 'P'))
				this.move(Direcao.BAIXO, mapa);

			this.ultimaDirecao = Direcao.ESQUERDA;

		} else if (d == Direcao.DIREITA) {
			if (deltay == 0) {
				if ((int) (x + velocidade) < mapa.getTamanho()) {
					if (mapa.getTab()[(int) y][(int) Math.ceil(x + velocidade)] == ' '
							|| mapa.getTab()[(int) y][(int) Math.ceil(x + velocidade)] == 'P') {
						this.pos.setX(x + velocidade);
					}
				}
			} else if (deltay > 0.0 && deltay < 0.5
					&& (mapa.getTab()[(int) Math.floor(y)][(int) (x + 1)] == ' ' || mapa.getTab()[(int) Math.floor(y)][(int) (x + 1)] == 'P'))
				this.move(Direcao.CIMA, mapa);
			else if (deltay > 0.5 && deltay < 1.0
					&& (mapa.getTab()[(int) Math.ceil(y)][(int) (x + 1)] == ' ' || mapa.getTab()[(int) Math.ceil(y)][(int) (x + 1)] == 'P'))
				this.move(Direcao.BAIXO, mapa);

			this.ultimaDirecao = Direcao.DIREITA;
		}

		((PlayerMovingAnim) animation).updateDir(ultimaDirecao);

		// System.out.println("x,y:");
		// System.out.println(x);
		// System.out.println(y);
		// System.out.println("dx,dy:");
		// System.out.println(deltax);
		// System.out.println(deltay);
		// System.out.println("dyceil:");
		// System.out.println(deltay_ceil);

	}

	public void decBomba() {
		nrBombas--;
	}

	public void addBomba() {
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

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public Animation.ColorPlayer parseColor(int color) {

		if (color == 0)
			return Animation.ColorPlayer.RED;
		else if (color == 1)
			return Animation.ColorPlayer.BLUE;
		else if (color == 2)
			return Animation.ColorPlayer.GREEN;
		else
			return Animation.ColorPlayer.YELLOW;
	}

	public Jogador.Direcao parseDirection(int id) {

		if (id == 0)
			return Jogador.Direcao.DIREITA;
		else if (id == 1)
			return Jogador.Direcao.ESQUERDA;
		else if (id == 2)
			return Jogador.Direcao.DIREITA;
		else
			return Jogador.Direcao.ESQUERDA;
	}

}
