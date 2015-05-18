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

		if (Jogador.getNextId() == 1) {
			j = new Jogador(1, 1, '1');
			jogadores.add(j);
		} else if (Jogador.getNextId() == 1) {
			j = new Jogador(mapa.getTamanho() - 1, mapa.getTamanho() - 1, '2');
			jogadores.add(j);
		} else if (Jogador.getNextId() == 1) {
			j = new Jogador(1, mapa.getTamanho() - 1, '3');
			jogadores.add(j);
		} else if (Jogador.getNextId() == 1) {
			j = new Jogador(mapa.getTamanho() - 1, 1, '4');
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
		for (Iterator<Bomba> it = bombas.iterator(); it.hasNext();) {
			Bomba b = it.next();
			if (b.getEstado() == Estado.INATIVO) {
				it.remove();
			} else {
				b.updateCronoBomba(decremento);
			}
		}
	}

}