package bomberman.logic;

import java.util.Observable;
import java.util.Observer;

import bomberman.connection.ConnectionId;
import bomberman.logic.Jogador.Direcao;

public class JogadorHandler implements Observer {
	private ConnectionId conn;

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

	public Jogador.Direcao getDir() {
		return dir;
	}

	public Instruction getNextInstruction() {
		return nextInstruction;
	}

	public void resetInstruction() {
		nextInstruction = previousInstruction;
	}
}
