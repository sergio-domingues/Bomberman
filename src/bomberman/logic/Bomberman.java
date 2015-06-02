package bomberman.logic;

import java.util.ArrayList;
import java.util.Iterator;

import bomberman.logic.Builder.Difficulty;
import bomberman.logic.Peca.Estado;

public class Bomberman {
	private Mapa mapa;
	private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	private ArrayList<Bomba> bombas = new ArrayList<Bomba>();

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

	public static void main(String[] args) {
		Builder b = new Builder(Difficulty.EASY, 15);
		imprimeMapa(b.createEasyMap(), 15);
	}

	public Bomberman() {
		mapa = new Mapa(15);
		mapa.setTabuleiro(new Builder(Difficulty.EASY, 15).createEasyMap());

		adicionarJogador();
		adicionarJogador();
	}

	public static void imprimeMapa(char[][] tab, int tamanho) {
		for (int i = 0; i < tamanho; i++) {

			for (int j = 0; j < tamanho; j++) {
				System.out.print(tab[i][j] + " ");
			}

			System.out.print("\n");
		}
	}

	void adicionarJogador() {
		Jogador j;

		if (Jogador.getNextId() > 4)
			return;

		if (Jogador.getNextId() == 1) {
			j = new Jogador(1, 1, '1');
			jogadores.add(j);
		} else if (Jogador.getNextId() == 2) {
			j = new Jogador(mapa.getTamanho() - 2, mapa.getTamanho() - 2, '2');
			jogadores.add(j);
		} else if (Jogador.getNextId() == 3) {
			j = new Jogador(1, mapa.getTamanho() - 2, '3');
			jogadores.add(j);
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
		boolean bombaExplodiu = false;
		for (Iterator<Bomba> it = bombas.iterator(); it.hasNext();) {
			Bomba b = it.next();
			if (b.getEstado() == Estado.INATIVO) {
				it.remove();
			} else {
				bombaExplodiu = b.updateCronoBomba(decremento);
				if (bombaExplodiu) {
					explodirBomba(b);
				}
			}
		}

	}

	public void explodirBomba(Bomba b) {
		// CIMA
		for (int i = 0; i <= b.getRaio(); i++) {
			if ((int) b.getPos().getY() - i < 0 || mapa.getTab()[(int) b.getPos().getY() - i][(int) b.getPos().getX()] == 'X') {
				break;
			}
			if (mapa.getTab()[(int) b.getPos().getY() - i][(int) b.getPos().getX()] == 'W') {
				mapa.getTab()[(int) b.getPos().getY() - i][(int) b.getPos().getX()] = ' ';
				break;
			}
		}

		// BAIXO
		for (int i = 0; i <= b.getRaio(); i++) {
			if ((int) b.getPos().getY() + i >= mapa.getTamanho() || mapa.getTab()[(int) b.getPos().getY() + i][(int) b.getPos().getX()] == 'X') {
				break;
			}
			if (mapa.getTab()[(int) b.getPos().getY() + i][(int) b.getPos().getX()] == 'W') {
				mapa.getTab()[(int) b.getPos().getY() + i][(int) b.getPos().getX()] = ' ';
				break;
			}
		}

		// ESQUERDA
		for (int i = 0; i <= b.getRaio(); i++) {
			if ((int) b.getPos().getX() - i < 0 || mapa.getTab()[(int) b.getPos().getY()][(int) b.getPos().getX() - i] == 'X') {
				break;
			}
			if (mapa.getTab()[(int) b.getPos().getY()][(int) b.getPos().getX() - i] == 'W') {
				mapa.getTab()[(int) b.getPos().getY()][(int) b.getPos().getX() - i] = ' ';
				break;
			}
		}

		// DIREITA
		for (int i = 0; i <= b.getRaio(); i++) {
			if ((int) b.getPos().getY() + i >= mapa.getTamanho() || mapa.getTab()[(int) b.getPos().getY()][(int) b.getPos().getX() + i] == 'X') {
				break;
			}
			if (mapa.getTab()[(int) b.getPos().getY()][(int) b.getPos().getX() + i] == 'W') {
				mapa.getTab()[(int) b.getPos().getY()][(int) b.getPos().getX() + i] = ' ';
				break;
			}
		}

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

	public void verificaJogador(int dec) {

		for (int i = 0; i < this.jogadores.size(); i++) {

			if (this.jogadores.get(i).getEstado() == Peca.Estado.INATIVO)
				continue;

			if (this.jogadores.get(i).getEstadoVuln() == Jogador.EstadoVulnerabilidade.VULNERAVEL) {

				for (int j = 0; j < this.bombas.size(); j++) {

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
}
