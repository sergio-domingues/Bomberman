package bomberman.logic;

import java.util.Observable;
import java.util.Observer;

import bomberman.connection.ConnectionId;
import bomberman.logic.Jogador.Direcao;

/**
 * Classe responsavel pela ligação entre a logica do jogo e o sistema de rede 
 * @author Diogo Moura
 *
 */
public class JogadorHandler implements Observer {
	private ConnectionId conn;

	/**
	 * Lista de Instrucoes recebidas
	 * @author Diogo Moura
	 *
	 */
	public enum Instruction {
		PLANTBOMB, MOVE, STOP
	}

	private Jogador.Direcao dir = null;
	private Instruction nextInstruction = null;
	private Instruction previousInstruction = null;

	public JogadorHandler(ConnectionId conn) {
		this.conn = conn;
		conn.addObserver(this);
	}

	
	@Override
	public void update(Observable arg0, Object arg1) {
		String message = (String) arg1;
		previousInstruction = nextInstruction;

		if (message.equals("moveLeft")) {
			nextInstruction = Instruction.MOVE;
			dir = Direcao.ESQUERDA;
		} else if (message.equals("moveRight")) {
			nextInstruction = Instruction.MOVE;
			dir = Direcao.DIREITA;

		} else if (message.equals("moveUp")) {
			nextInstruction = Instruction.MOVE;
			dir = Direcao.CIMA;
		} else if (message.equals("moveDown")) {
			nextInstruction = Instruction.MOVE;
			dir = Direcao.BAIXO;
		} else if (message.equals("plantBomb")) {
			nextInstruction = Instruction.PLANTBOMB;
		} else if (message.equals("Stop")) {
			nextInstruction = Instruction.STOP;
		}

	}

	/**
	 * Obtem a Direcao da do Proximo Movimento do Jogador 
	 * @return Direcao
	 */
	public Jogador.Direcao getDir() {
		return dir;
	}

	/**
	 * Obtem a proxima Instrução a ser executada
	 * @return Proxima Instrução
	 */
	public Instruction getNextInstruction() {
		return nextInstruction;
	}

	/**
	 * Elimina a Intrucao actual e altera a para a anterior
	 */
	public void resetInstruction() {
		nextInstruction = previousInstruction;
	}
}
