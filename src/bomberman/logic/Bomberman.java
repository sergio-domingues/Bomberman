package bomberman.logic;

import bomberman.logic.Builder.Difficulty;

public class Bomberman {

	public static void main(String[] args) {
		Builder b = new Builder(Difficulty.EASY, 15);
	//	System.out.println("teste");
		imprimeMapa(b.createEasyMap(),15);
	}

	public static void imprimeMapa(char[][] tab, int tamanho) {
		for (int i = 0; i < tamanho; i++) {

			for (int j = 0; j < tamanho; j++) {
				System.out.print(tab[i][j] + " ");
			}

			System.out.print("\n");
		}
	}
}
