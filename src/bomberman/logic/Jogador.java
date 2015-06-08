package bomberman.logic;

import java.util.ArrayList;

import bomberman.gui.Animation;
import bomberman.gui.PlayerMovingAnim;
import bomberman.gui.PlayerStaticAnim;

/**
 * Classe responsavel pela logica do jogador
 * 
 * @author Diogo Moura
 *
 */
public class Jogador extends Peca {

	// velocidades possiveis
	private static final double velocidade_1 = 0.125, velocidade_2 = 0.2, velocidade_3 = 0.25;

	/**
	 * Direção em que o jogador esta a "olhar"
	 * 
	 * @author Diogo MOura
	 *
	 */
	public static enum Direcao {
		DIREITA, ESQUERDA, CIMA, BAIXO
	};

	private Animation.ColorPlayer color;
	private Direcao ultimaDirecao;
	private int nrBombas = 2; // TODO Alterar pra valor desejado
	private static int nextId = 1;
	private int id;
	private Animation animation;

	/**
	 * Estado do Movimento Jogador
	 * 
	 * @author Diogo Moura
	 *
	 */
	public static enum EstadoJogador {
		MOVER, PARADO
	};

	/**
	 * Estado da Susceptibilidade a perder vidas
	 * 
	 * @author Utilizador
	 *
	 */
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

	/**
	 * Contrutor do Jogador
	 * 
	 * @param x
	 *            Posicao Horizontal Inicial
	 * @param y
	 *            Posicao Vertical Inicial
	 * @param sigla
	 *            Sigla do Jogador
	 */
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

	/**
	 * Obtem a Cor do Jogador
	 * 
	 * @return Cor
	 */
	public Animation.ColorPlayer getColor() {
		return color;
	}

	/**
	 * Define Cor do jogador
	 * 
	 * @param color
	 *            Nova Cor
	 */
	public void setColor(Animation.ColorPlayer color) {
		this.color = color;
	}

	/**
	 * Reinicia o contador de Id's
	 */
	public static void resetNextId() {
		Jogador.nextId = 1;
	}

	/**
	 * Obtem a Vulnerabilidade do Jogador a Perder Vidas
	 * 
	 * @return Vulnerabilidade
	 */
	public EstadoVulnerabilidade getEstadoVuln() {
		return estadoVuln;
	}

	/**
	 * Modifica a Vulnerabilidade do Jogador
	 * 
	 * @param estadoVuln
	 *            Nova Vunerabilidade
	 */
	public void setEstadoVuln(EstadoVulnerabilidade estadoVuln) {
		this.estadoVuln = estadoVuln;
	}

	/**
	 * Obtem a velocidade actual do jogador
	 * 
	 * @return Velocidade
	 */
	public double getVelocidade() {
		return velocidade;
	}

	/**
	 * Direcao para qual esta virado o jogador
	 * 
	 * @return Direcao
	 */
	public Direcao getUltimaDirecao() {
		return ultimaDirecao;
	}

	/**
	 * Modifica a Direcao do Jogador
	 * 
	 * @param ultimaDirecao
	 *            Nova Direcao
	 */
	public void setUltimaDirecao(Direcao ultimaDirecao) {
		this.ultimaDirecao = ultimaDirecao;
	}

	/**
	 * Modifica a Velocidade do Jogador
	 * 
	 * @param velocidade
	 *            Nova Velocidade
	 */
	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}

	/**
	 * Obtem o Numero de vidas do Jogador
	 * 
	 * @return
	 */
	public int getVidas() {
		return vidas;
	}

	/**
	 * Modifica o numero de vidas
	 * 
	 * @param vidas
	 *            Novo numero de vidas
	 */
	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	/**
	 * Obtem o estado do jogador
	 * 
	 * @return Estado actual do jogador
	 */
	public EstadoJogador getEstadoJogador() {
		return estadoJogador;
	}

	/**
	 * Atrinui Novo estado ao jogador e modifica a animacao
	 * 
	 * @param estadoJogador
	 *            Novo estado
	 */
	public void setEstadoJogador(EstadoJogador estadoJogador) {
		this.estadoJogador = estadoJogador;
		if (estadoJogador == EstadoJogador.PARADO) {
			this.animation = new PlayerStaticAnim(color, ultimaDirecao);
		} else if (estadoJogador == EstadoJogador.MOVER) {
			this.animation = new PlayerMovingAnim(color, ultimaDirecao);
		}
	}

	/**
	 * Actualiza o tempo restante que o jogador esta vulneravel
	 * 
	 * @param decremento
	 *            milisegundos a decrementar
	 */
	public void updateTempoJogador(int decremento) {

		this.tempo_invulneravel -= decremento;

		if (this.tempo_invulneravel <= 0) {
			this.tempo_invulneravel = 1500;
			this.estadoVuln = EstadoVulnerabilidade.VULNERAVEL;
		}
	}

	/**
	 * Update da velocidadequando é apanhado um power up
	 */
	public void updateVelocidade() {
		if (this.velocidade < velocidade_2) {
			this.velocidade = velocidade_2;
		} else if (this.velocidade < velocidade_3) {
			this.velocidade = velocidade_3;
		}
	}

	/**
	 * Aumenta o raio da bomba
	 * 
	 * @param tamanho_mapa
	 */
	public void updateRangeBomba(int tamanho_mapa) {
		// -2 -> parede
		if (this.raioBomba + 1 < tamanho_mapa - 2)
			this.raioBomba++;
	}

	/**
	 * Obtem o proximo Id que vai ser atribuido ao jogador
	 * 
	 * @return Id
	 */
	static int getNextId() {
		return nextId;
	}

	/**
	 * Obtem o Id do jogador
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Verifica se o jogado colidiu com algum objecto no mapa
	 * 
	 * @param x
	 *            Posicao Horizontal do Objecto
	 * @param y
	 *            Posicao vertical do Objecto
	 * @param map
	 *            Mapa
	 * @return True se colidiu / false se nao colidiu
	 */
	public boolean checkCollidePowerup(int x, int y, Mapa map) {
		return map.getTab()[y][x] == 'E' || map.getTab()[y][x] == 'S' || map.getTab()[y][x] == 'R';
	}

	/**
	 * Moviemnta o Jogador numa Diracao e actualiza a animacao deste
	 * 
	 * @param d
	 *            Direcao a mover
	 * @param mapa
	 *            Mapa
	 */
	public void move(Direcao d, Mapa mapa) {

		if (this.estado == Peca.Estado.INATIVO)
			return;

		double x, y, deltax, deltay;// , deltay_ceil;

		// arredondamento correctivo : 2.99999 -> 3.0 | 2.00003 = 2.0
		x = Math.round(this.pos.getX() * 1000.0) / 1000.0;
		y = Math.round(this.pos.getY() * 1000.0) / 1000.0;
		deltax = x - Math.floor(x);
		deltay = y - Math.floor(y);
		// deltay_ceil = Math.ceil(y) - y;

		if (d == Direcao.CIMA) {
			if (deltax == 0) { // coincide com inicio da celula
				if ((int) (y - velocidade) > 0) {
					if (mapa.getTab()[(int) Math.floor(y - velocidade)][(int) x] == ' '
							|| checkCollidePowerup((int) x, (int) Math.floor(y - velocidade), mapa)) {
						this.pos.setY(y - velocidade);
					}
				}

				if (d != this.ultimaDirecao) {
					((PlayerMovingAnim) animation).updateDir(d);
				}

				this.ultimaDirecao = Direcao.CIMA;

			} else if (deltax < 0.5 && deltax > 0.0
					&& (mapa.getTab()[(int) (y - 1)][(int) Math.floor(x)] == ' ' || checkCollidePowerup((int) Math.floor(x), (int) (y - 1), mapa)))
				this.move(Direcao.ESQUERDA, mapa);
			else if (deltax > 0.5 && deltax < 1.0
					&& (mapa.getTab()[(int) (y - 1)][(int) Math.ceil(x)] == ' ' || checkCollidePowerup((int) Math.ceil(x), (int) (y - 1), mapa)))
				this.move(Direcao.DIREITA, mapa);

		} else if (d == Direcao.BAIXO) {
			if (deltax == 0) {
				if ((int) (y + velocidade) < mapa.getTamanho()) {
					if (mapa.getTab()[(int) Math.ceil(y + velocidade)][(int) x] == ' '
							|| checkCollidePowerup((int) x, (int) Math.ceil(y + velocidade), mapa)) {
						this.pos.setY(y + velocidade);
					}
				}

				if (d != this.ultimaDirecao) {
					((PlayerMovingAnim) animation).updateDir(d);
				}
				this.ultimaDirecao = Direcao.BAIXO;

			} else if (deltax > 0.0 && deltax < 0.5
					&& (mapa.getTab()[(int) (y + 1)][(int) Math.floor(x)] == ' ' || checkCollidePowerup((int) Math.floor(x), (int) (y + 1), mapa)))
				this.move(Direcao.ESQUERDA, mapa);
			else if (deltax > 0.5 && deltax < 1.0
					&& (mapa.getTab()[(int) (y + 1)][(int) Math.ceil(x)] == ' ' || checkCollidePowerup((int) Math.ceil(x), (int) (y + 1), mapa)))
				this.move(Direcao.DIREITA, mapa);

		} else if (d == Direcao.ESQUERDA) {
			if (deltay == 0) {
				if ((int) (x - velocidade) > 0) {
					if (mapa.getTab()[(int) y][(int) Math.floor(x - velocidade)] == ' '
							|| checkCollidePowerup((int) Math.floor(x - velocidade), (int) y, mapa)) {
						this.pos.setX(x - velocidade);
					}
				}
				if (d != this.ultimaDirecao) {
					((PlayerMovingAnim) animation).updateDir(d);
				}

				this.ultimaDirecao = Direcao.ESQUERDA;
			} else if (deltay > 0.0 && deltay < 0.5
					&& (mapa.getTab()[(int) Math.floor(y)][(int) (x - 1)] == ' ' || checkCollidePowerup((int) (x - 1), (int) Math.floor(y), mapa)))
				this.move(Direcao.CIMA, mapa);
			else if (deltay > 0.5 && deltay < 1.0
					&& (mapa.getTab()[(int) Math.ceil(y)][(int) (x - 1)] == ' ' || checkCollidePowerup((int) (x - 1), (int) Math.ceil(y), mapa)))
				this.move(Direcao.BAIXO, mapa);

		} else if (d == Direcao.DIREITA) {
			if (deltay == 0) {
				if ((int) (x + velocidade) < mapa.getTamanho()) {
					if (mapa.getTab()[(int) y][(int) Math.ceil(x + velocidade)] == ' '
							|| checkCollidePowerup((int) Math.ceil(x + velocidade), (int) y, mapa)) {
						this.pos.setX(x + velocidade);
					}
				}
				if (d != this.ultimaDirecao) {
					((PlayerMovingAnim) animation).updateDir(d);
				}

				this.ultimaDirecao = Direcao.DIREITA;

			} else if (deltay > 0.0 && deltay < 0.5
					&& (mapa.getTab()[(int) Math.floor(y)][(int) (x + 1)] == ' ' || checkCollidePowerup((int) (x + 1), (int) Math.floor(y), mapa)))
				this.move(Direcao.CIMA, mapa);
			else if (deltay > 0.5 && deltay < 1.0
					&& (mapa.getTab()[(int) Math.ceil(y)][(int) (x + 1)] == ' ' || checkCollidePowerup((int) (x + 1), (int) Math.ceil(y), mapa)))
				this.move(Direcao.BAIXO, mapa);

		}
	}

	/**
	 * Diminui em um o nr de Bombas
	 */
	public void decBomba() {
		nrBombas--;
	}

	/**
	 * Aumenta em um o nr de Bombas
	 */
	public void addBomba() {
		nrBombas++;
	}

	/**
	 * Diminui em um o nr de Vidas
	 */
	public void decVidas() {
		vidas--;
	}

	/**
	 * Aumenta em um o nr de Vidas
	 */
	public void addVidas() {
		vidas++;
	}

	/**
	 * Obtem o Nr de Bombas disponiveis
	 * @return Nr de Bombas
	 */
	public int getNrBombas() {
		return nrBombas;
	}

	/**
	 * Arma uma Bomba
	 * @return Bomba Armada
	 */
	public Bomba armarBomba() {
		Bomba temp = new Bomba(Math.round(this.pos.getX()), Math.round(this.pos.getY()), 'B', this.raioBomba, this);
		this.nrBombas--;
		return temp;
	}

	/**
	 * Obtem a lista de PowerUps do Jogador
	 * @return
	 */
	public ArrayList<PowerUp> getPowerUps() {
		return powerUps;
	}

	/**
	 * Adiciona um lista de PowerUps ao jogador
	 * @param powerUps
	 */
	public void setPowerUps(ArrayList<PowerUp> powerUps) {
		this.powerUps = powerUps;
	}

	/**
	 * Obtem o tempo de Invulnerabilidade do Jogador
	 * @return
	 */
	public int getTempo_invulneravel() {
		return tempo_invulneravel;
	}

	/**
	 * Modifica o tempo de invulnerabilidade do jogador
	 * @param tempo_invulneravel
	 */
	public void setTempo_invulneravel(int tempo_invulneravel) {
		this.tempo_invulneravel = tempo_invulneravel;
	}

	/**
	 * Obtem a animacao actual do jogador
	 * @return animacao
	 */
	public Animation getAnimation() {
		return animation;
	}

	/**
	 * Atribui uma nova Animacao
	 * @param animation Nova Animacao
	 */
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	/**
	 * Iterpreta a cor do jogador
	 * @param color idCor
	 * @return Cor
	 */
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

	/**
	 * Interpreta a Direcao do Jogador
	 * @param id idDirecao
	 * @return Direcao
	 */
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
