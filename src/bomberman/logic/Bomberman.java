package bomberman.logic;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import bomberman.logic.Builder.Difficulty;
import bomberman.logic.Peca.Estado;

public class Bomberman {
	private Mapa mapa;
	private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	private ArrayList<Bomba> bombas = new ArrayList<Bomba>();
	private ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();

	private int numPwUps = 3; // TODO ALTERAR PARA VALOR PRETENDIDO

	public int getNumPwUps() {
		return numPwUps;
	}

	public void setNumPwUps(int numPwUps) {
		this.numPwUps = numPwUps;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

	public ArrayList<Jogador> getJogadores() {
		return jogadores;
	}

	public ArrayList<Bomba> getBombas() {
		return bombas;
	}

	public Bomberman() {
		mapa = new Mapa(15);
		mapa.setTabuleiro(new Builder(Difficulty.EASY, 15).createEasyMap());

		adicionarJogador();	
//		adicionarJogador();
	}

	//MUSICA
	public void teste() throws URISyntaxException {

		// System.out.print(System.getProperty("user.dir")+"\\resources\\sound.wav");
		new javafx.embed.swing.JFXPanel();
		
		String s = new File(System.getProperty("user.dir") + "\\resources\\Bomb.mp3").toURI().toString();
		MediaPlayer player = new MediaPlayer(new Media(s));
		//player.setVolume(0.8);
		player.play();

		// try{
		// AudioInputStream audioInputStream =
		// AudioSystem.getAudioInputStream(this.getClass().getResource("/resources/sound.wav"));
		//
		// Clip clip = AudioSystem.getClip();
		// clip.open(audioInputStream);
		// clip.start( );
		// }
		// catch(Exception ex)
		// {System.out.println("lol"); }
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		Builder b = new Builder(Difficulty.EASY, 15);
		imprimeMapa(b.createEasyMap(), 15);

		Bomberman bmb = new Bomberman();
		bmb.teste();
		// open the sound file as a Java input stream
		// String songFile =
		// System.getProperty("user.dir")+"\\resources\\sound.wav";
		// InputStream in;
		// AudioStream audioStream;
		//
		// in = new FileInputStream(songFile);
		//
		// audioStream = new AudioStream(in);
		// // play the audio clip with the audioplayer class
		// AudioPlayer.player.start(audioStream);

	}

	public static void imprimeMapa(char[][] tab, int tamanho) {
		for (int i = 0; i < tamanho; i++) {

			for (int j = 0; j < tamanho; j++) {
				System.out.print(tab[i][j] + " ");
			}

			System.out.print("\n");
		}
	}

	public void moveJogador(Jogador j, Jogador.Direcao dir) {
		j.move(dir, mapa);
	}

	void adicionarJogador() {
		Jogador j;

		if (Jogador.getNextId() > 4)
			return;

		if (Jogador.getNextId() == 1) { 
			//canto sup esq
			j = new Jogador(1, 1, '1');
			jogadores.add(j);			
		} else if (Jogador.getNextId() == 2) {
			//canto inf dir
			j = new Jogador(mapa.getTamanho() - 2, mapa.getTamanho() - 2, '2');
			jogadores.add(j);			
		} else if (Jogador.getNextId() == 3) {
			//canto inf esq
			j = new Jogador(1, mapa.getTamanho() - 2, '3');
			jogadores.add(j);
			//canto sup dir
		} else if (Jogador.getNextId() == 4) {
			j = new Jogador(mapa.getTamanho() - 2, 1, '4');
			jogadores.add(j);
		}
	}

	public void colocarBomba(Jogador j) {
		if (j.getNrBombas() == 0) {
			return;
		} else {
			this.bombas.add(j.armarBomba());
		}
	}

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
		for (int i = 0; i < jogadores.size(); i++) {
			if (jogadores.get(i).getEstado() != Peca.Estado.ACTIVO)
				continue;

			if (jogadores.get(i).ver(b, mapa, b.getRaio())) {

				jogadores.get(i).decVidas();
				jogadores.get(i).setEstadoVuln(Jogador.EstadoVulnerabilidade.INVULNERAVEL);

				System.out.println(jogadores.get(i).getVidas());

				if (jogadores.get(i).getVidas() == 0) {
					jogadores.get(i).setEstado(Peca.Estado.INATIVO);
				}
			}
		}
	}

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

				this.mapa.setChar((int) p.getPos().getX(), (int) p.getPos().getY(), ' ');
				it.remove(); // remove pwup
				return true;
			}
		}
		return false;
	}

	public void verificaJogador(int dec) {

		for (int i = 0; i < this.jogadores.size(); i++) {

			if (this.jogadores.get(i).getEstado() == Peca.Estado.INATIVO)
				continue;

			if (this.jogadores.get(i).getEstadoVuln() == Jogador.EstadoVulnerabilidade.VULNERAVEL) {

				for (int j = 0; j < this.bombas.size(); j++) {

					if (this.bombas.get(j).getEstado() != Peca.Estado.ACTIVO)
						continue;

					if (this.bombas.get(j).getEstadoBomba() != Bomba.EstadoBomba.EXPLODINDO)
						continue;

					if (this.jogadores.get(i).ver(this.bombas.get(j), this.mapa, this.bombas.get(j).getRaio())) {

						System.out.println(this.jogadores.get(i).getEstadoVuln());

						this.jogadores.get(i).decVidas();
						this.jogadores.get(i).setEstadoVuln(Jogador.EstadoVulnerabilidade.INVULNERAVEL);

						if (this.jogadores.get(i).getVidas() == 0) {
							System.out.println("morreu");
							jogadores.get(i).setEstado(Peca.Estado.INATIVO);
						}
					}
				}
			}

			else
				this.jogadores.get(i).updateTempoJogador(dec);
		}
	}

	public PowerUp geraPowerUp(double x, double y) {

		Random gerador = new Random();
		int prob = gerador.nextInt(2); // 50% prob criar pwup

		if (prob == 1) {
			int prob_pwup = gerador.nextInt(numPwUps);

			switch (prob_pwup) {

			case 0: {// speed pwup
				SpeedPowerUp s = new SpeedPowerUp(x, y, 'P');
				this.powerUps.add(s);
				return s;
			}
			case 1: {
				ExtraBombPowerUp s = new ExtraBombPowerUp(x, y, 'P');
				this.powerUps.add(s);
				return s;
			}
			case 2: {
				IncRangePowerUp s = new IncRangePowerUp(x, y, 'P');
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

	public ArrayList<PowerUp> getPowerUps() {
		return powerUps;
	}

	public void setPowerUps(ArrayList<PowerUp> powerUps) {
		this.powerUps = powerUps;
	}
}
