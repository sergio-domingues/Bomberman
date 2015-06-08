package bomberman.gui;

import java.awt.Graphics;

/**
 * Interface para a aplicacao de Animacoes aos Objectos
 * Aplicacao do Strategy Pattern nesses objectos
 * 
 * @author Diogo Moura
 *
 */
public interface Animation {
	
	/**
	 * Cor dos Objectos
	 * @author Diogo Moura
	 *
	 */
	public enum ColorPlayer {
		RED, BLUE, YELLOW, GREEN
	}
	
	/**
	 * Executa renderizacao da animacao
	 * @param g Grafico onde vai ser executada a rederizacao
	 * @param posx Posicao Horiontal
	 * @param posy Posicao Vertical
	 */
	void render(Graphics g, double posx, double posy);

	/**
	 * Update do tempo da Animacao
	 * @param tempo tempo a incrementar à animacao
	 */
	void update(int tempo);
}
