package bomberman.logic;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import bomberman.gui.SoundAnimation;
import bomberman.logic.Builder.Difficulty;
import bomberman.logic.Peca.Estado;

/**
 * Classe Principal do Jogo Faz a ligancao de todos os componentes e executa
 * operacoes relevantes para este
 * 
 * @author Diogo Moura
 *
 */
public class Bomberman {
	private Mapa mapa;
	private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	private ArrayList<Bomba> bombas = new ArrayList<Bomba>();
	private ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();

	private SoundAnimation powerupSound;
	private int numPwUps = 3;
	private boolean gameover = false;
	private long elapsedTime = 0;

	/**
	 * Construtor do Bomberman
	 */
	public Bomberman() {
		mapa = new Mapa(15);
		mapa.setTabuleiro(new Builder(Difficulty.EASY, 15).createEasyMap());
		Jogador.resetNextId();
		loadSounds();
	}

	/**
	 * Obtem Nr de PowerUp Diferentes
	 * 
	 * @return Nr de PowerUps Diferentes
	 */
	public int getNumPwUps() {
		return numPwUps;
	}

	/**
	 * Modifica o Nr de PowerUp Diferentes
	 * 
	 * @param numPwUps
	 *            Novo Nr
	 */
	public void setNumPwUps(int numPwUps) {
		this.numPwUps = numPwUps;
	}

	/**
	 * Obtem o Mapa de Jogo
	 * 
	 * @return Mapa
	 */
	public Mapa getMapa() {
		return mapa;
	}

	/**
	 * Modifica o Mapa de jogo
	 * 
	 * @param mapa
	 *            Novo Mapa de Jogo
	 */
	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

	/**
	 * Obtem os Jogadores
	 * 
	 * @return ArrayList com todos os jogadores activos
	 */
	public ArrayList<Jogador> getJogadores() {
		return jogadores;
	}

	/**
	 * Obtem as Bombas activas em jogo
	 * 
	 * @return ArrayList com as Bombas
	 */
	public ArrayList<Bomba> getBombas() {
		return bombas;
	}

	/**
	 * Carrega o Som De Apanhar PowerUp
	 */
	public void loadSounds() {
		this.powerupSound = new SoundAnimation(new File(System.getProperty("user.dir") + "\\resources\\powerupSound.mp3").toURI().toString());
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		Builder b = new Builder(Difficulty.EASY, 15);
		imprimeMapa(b.createEasyMap(), 15);

		Bomberman bmb = new Bomberman();
	}

	/**
	 * Imprime o Mapa de jogo na consola
	 * 
	 * @param tab
	 *            Mapa
	 * @param tamanho
	 *            Tamanho do Mapa
	 */
	public static void imprimeMapa(char[][] tab, int tamanho) {
		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				System.out.print(tab[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Move um jogador numa direcao
	 * 
	 * @param j
	 *            Jogador
	 * @param dir
	 *            Direcao
	 */
	public void moveJogador(Jogador j, Jogador.Direcao dir) {

		if (j.getEstado() == Peca.Estado.INATIVO)
			return;

		j.move(dir, mapa);
	}

	/**
	 * Adiciona um Jogador ao jogo
	 */
	public void adicionarJogador() {
		Jogador j;

		if (Jogador.getNextId() > 4)
			return;

		if (Jogador.getNextId() == 1) {
			// canto sup esq
			j = new Jogador(1, 1, '1');
			jogadores.add(j);
		} else if (Jogador.getNextId() == 2) {
			// canto inf dir
			j = new Jogador(mapa.getTamanho() - 2, mapa.getTamanho() - 2, '2');
			jogadores.add(j);
		} else if (Jogador.getNextId() == 3) {
			// canto inf esq
			j = new Jogador(1, mapa.getTamanho() - 2, '3');
			jogadores.add(j);
			// canto sup dir
		} else if (Jogador.getNextId() == 4) {
			j = new Jogador(mapa.getTamanho() - 2, 1, '4');
			jogadores.add(j);
		}
	}

	/**
	 * Coloca uma Bomba por um certo Jogador
	 * 
	 * @param j
	 *            Jogador que vai colocar a bomba
	 */
	public void colocarBomba(Jogador j) {

		if (j.getEstado() == Peca.Estado.INATIVO)
			return;

		if (j.getNrBombas() == 0) {
			return;
		} else {
			this.bombas.add(j.armarBomba());
		}
	}

	/**
	 * Faz updade ao crono de todas as bombas activas
	 * 
	 * @param decremento
	 *            Valor a decrementas aos varios cronos
	 */
	public void updateBomba(double decremento) {
		int bombFlag = 0;
		for (Iterator<Bomba> it = bombas.iterator(); it.hasNext();) {
			Bomba b = it.next();
			if (b.getEstado() == Estado.INATIVO) {
				it.remove();
			} else {
				bombFlag = b.updateCronoBomba(decremento);
				explodirBomba(b, bombFlag);
			}
		}
	}

	/**
	 * Explode uma bomba
	 * 
	 * @param b
	 *            Bomba a explodir
	 * @param flag
	 *            Estado da Bomba 0- Inactiva 1- Aramda 2 - Explodir
	 */
	public void explodirBomba(Bomba b, int flag) {

		if (flag == 0)
			return;

		// CIMA
		for (int i = 0; i <= b.getRaio(); i++) {
			if ((int) b.getPos().getY() - i < 0 || mapa.getTab()[(int) b.getPos().getY() - i][(int) b.getPos().getX()] == 'X') {
				break;
			}
			if (mapa.getTab()[(int) b.getPos().getY() - i][(int) b.getPos().getX()] == 'W') {

				if (flag == 2) {
					PowerUp p;

					if ((p = geraPowerUp(b.getPos().getX(), b.getPos().getY() - i)) != null) {
						mapa.getTab()[(int) p.getPos().getY()][(int) p.getPos().getX()] = p.getSigla();
					} else
						mapa.getTab()[(int) b.getPos().getY() - i][(int) b.getPos().getX()] = ' ';
					break;
				}
			}
		}

		// BAIXO
		for (int i = 0; i <= b.getRaio(); i++) {
			if ((int) b.getPos().getY() + i >= mapa.getTamanho() || mapa.getTab()[(int) b.getPos().getY() + i][(int) b.getPos().getX()] == 'X') {
				break;
			}
			if (mapa.getTab()[(int) b.getPos().getY() + i][(int) b.getPos().getX()] == 'W') {

				if (flag == 2) {
					PowerUp p;

					if ((p = geraPowerUp(b.getPos().getX(), b.getPos().getY() + i)) != null) {
						mapa.getTab()[(int) p.getPos().getY()][(int) p.getPos().getX()] = p.getSigla();
					} else
						mapa.getTab()[(int) b.getPos().getY() + i][(int) b.getPos().getX()] = ' ';
					break;
				}
			}
		}

		// ESQUERDA
		for (int i = 0; i <= b.getRaio(); i++) {
			if ((int) b.getPos().getX() - i < 0 || mapa.getTab()[(int) b.getPos().getY()][(int) b.getPos().getX() - i] == 'X') {
				break;
			}
			if (mapa.getTab()[(int) b.getPos().getY()][(int) b.getPos().getX() - i] == 'W') {

				if (flag == 2) {
					PowerUp p;

					if ((p = geraPowerUp(b.getPos().getX() - i, b.getPos().getY())) != null) {
						mapa.getTab()[(int) p.getPos().getY()][(int) p.getPos().getX()] = p.getSigla();
					} else
						mapa.getTab()[(int) b.getPos().getY()][(int) b.getPos().getX() - i] = ' ';
					break;
				}
			}
		}

		// DIREITA
		for (int i = 0; i <= b.getRaio(); i++) {
			if ((int) b.getPos().getY() + i >= mapa.getTamanho() || mapa.getTab()[(int) b.getPos().getY()][(int) b.getPos().getX() + i] == 'X') {
				break;
			}
			if (mapa.getTab()[(int) b.getPos().getY()][(int) b.getPos().getX() + i] == 'W') {

				if (flag == 2) {
					PowerUp p;

					if ((p = geraPowerUp(b.getPos().getX() + i, b.getPos().getY())) != null) {
						mapa.getTab()[(int) p.getPos().getY()][(int) p.getPos().getX()] = p.getSigla();
					} else
						mapa.getTab()[(int) b.getPos().getY()][(int) b.getPos().getX() + i] = ' ';
					break;
				}
			}
		}

		if (flag == 2)
			return;

		// verifica colisoes com a bomba
		for (Iterator<Jogador> it = jogadores.iterator(); it.hasNext();) {

			Jogador j = it.next();

			if (j.getEstado() != Peca.Estado.ACTIVO)
				continue;

			if (j.ver(b, mapa, b.getRaio()) && j.getEstadoVuln() == Jogador.EstadoVulnerabilidade.VULNERAVEL) {

				j.decVidas();
				j.setEstadoVuln(Jogador.EstadoVulnerabilidade.INVULNERAVEL);

				if (j.getVidas() == 0) {
					j.setEstado(Peca.Estado.INATIVO);
					verificaEstadoJogo();
				}
			}
		}
	}

	/**
	 * verifica se um Jogador Aanhou um PowerUp
	 * 
	 * @param j
	 *            Jogador a verificar colisao
	 * @return Apanhou ou nao o algum PowerUp
	 */
	public boolean checkPowerUp(Jogador j) {

		for (Iterator<PowerUp> it = powerUps.iterator(); it.hasNext();) {

			PowerUp p = it.next();

			if (j.colide(p)) {

				if (p.getClass() == SpeedPowerUp.class) {
					j.updateVelocidade();
				} else if (p.getClass() == IncRangePowerUp.class) {
					j.updateRangeBomba(this.mapa.getTamanho());
				} else if ((p.getClass() == ExtraBombPowerUp.class)) {
					j.addBomba();
				}

				this.powerupSound.getPlayer().stop();
				this.powerupSound.getPlayer().setVolume(0.5);
				this.powerupSound.getPlayer().play();
				this.mapa.setChar((int) p.getPos().getX(), (int) p.getPos().getY(), ' ');
				it.remove(); // remove pwup
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica se os diferentes jogadores foram afectados por alguma explosao
	 * 
	 * @param dec
	 *            Milisegundos a decrementar ao crono do Jogador
	 */
	public void verificaJogador(int dec) {

		for (Iterator<Jogador> it = this.jogadores.iterator(); it.hasNext();) {

			Jogador j = it.next();

			if (j.getEstado() == Peca.Estado.INATIVO)
				continue;

			if (j.getEstadoVuln() == Jogador.EstadoVulnerabilidade.VULNERAVEL) {

				for (int k = 0; k < this.bombas.size(); k++) {

					if (this.bombas.get(k).getEstado() != Peca.Estado.ACTIVO)
						continue;

					if (this.bombas.get(k).getEstadoBomba() != Bomba.EstadoBomba.EXPLODINDO)
						continue;

					if (j.ver(this.bombas.get(k), this.mapa, this.bombas.get(k).getRaio())) {

						j.decVidas();
						j.setEstadoVuln(Jogador.EstadoVulnerabilidade.INVULNERAVEL);

						if (j.getVidas() == 0) {
							j.setEstado(Peca.Estado.INATIVO);
							verificaEstadoJogo();
						}
					}
				}
			}

			else
				j.updateTempoJogador(dec);
		}
	}

	/**
	 * Gera um PowerUp quando uma caixa explode
	 * 
	 * @param x
	 *            Posicao Horizontal PowerUp
	 * @param y
	 *            Posicao Vertical PowerUp
	 * @return PowerUp gerado
	 */
	public PowerUp geraPowerUp(double x, double y) {

		Random gerador = new Random();
		int prob = gerador.nextInt(2); // 50% prob criar pwup

		if (prob == 1) {
			int prob_pwup = gerador.nextInt(numPwUps);

			switch (prob_pwup) {

			case 0: {// speed pwup
				SpeedPowerUp s = new SpeedPowerUp(x, y, 'S');
				this.powerUps.add(s);
				return s;
			}
			case 1: {
				ExtraBombPowerUp s = new ExtraBombPowerUp(x, y, 'E');
				this.powerUps.add(s);
				return s;
			}
			case 2: {
				IncRangePowerUp s = new IncRangePowerUp(x, y, 'R');
				this.powerUps.add(s);
				return s;
			}
			default: {
				break;
			}
			}
		}
		return null;
	}

	/**
	 * Obtem a Lista dos PowerUps activos
	 * @return ArrayList PowerUp
	 */
	public ArrayList<PowerUp> getPowerUps() {
		return powerUps;
	}

	/**
	 * Modifica a Lista dos PowerUp activos 
	 * @param powerUps Nova Lista de PowerUps
	 */
	public void setPowerUps(ArrayList<PowerUp> powerUps) {
		this.powerUps = powerUps;
	}

	/**
	 * Verifica se o Jogo ainda está activo ou se só resta um jogador
	 */
	public void verificaEstadoJogo() {

		int counter = 0;

		for (int i = 0; i < this.jogadores.size(); i++) {
			if (this.jogadores.get(i).getEstado() == Peca.Estado.ACTIVO)
				counter++;
		}

		// last player alive
		if (counter == 1) {
			gameover = true;

		}
	}

	/**
	 * Obtem o tempo total de jogo
	 * @return Tempo em Milisegundos
	 */
	public long getElapsedTime() {
		return elapsedTime;
	}

	/**
	 * Atrinui Novo tempo de jogo
	 * @param elapsedTime Novo tempo
	 */
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	/**
	 * Incrementa o Tempo de Jogo
	 * @param updaterate Incremento
	 */
	public void updateElapsedTime(int updaterate) {
		this.elapsedTime += updaterate;
	}

	/**
	 * Verifica quem ganhou o jogo
	 * @return Id do Vencedor
	 */
	public int isGameover() {
		if (gameover) {
			for (int i = 0; i < this.jogadores.size(); i++) {
				if (this.jogadores.get(i).getEstado() == Peca.Estado.ACTIVO)
					return this.jogadores.get(i).getId();
			}
		}
		return -1;
	}

	/**
	 * Gera o Highscore do Vencedor
	 * @return Highscore
	 */
	public int generateHighscore() {
		return (int) ((this.jogadores.size() * 100000) / (this.elapsedTime / 1000));
	}
}
