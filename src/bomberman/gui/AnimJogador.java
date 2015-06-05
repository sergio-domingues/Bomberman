package bomberman.gui;

import java.util.Observable;
import java.util.Observer;

import bomberman.connection.ConnectionId;
import bomberman.logic.Jogador;
import bomberman.logic.Jogador.Direcao;

public class AnimJogador implements Observer {
	private ConnectionId conn;

	enum Instruction {
		PLANTBOMB, MOVE, STOP
	}

	private Jogador.Direcao dir = null;
	private Instruction nextInstruction = null;


	public AnimJogador(ConnectionId conn) {
		this.conn = conn;
		conn.addObserver(this);	
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String message = (String) arg1;

		System.out.println("update");
		
		if (message.equals("moveLeftPressed")) {
			nextInstruction = Instruction.MOVE;
			dir = Direcao.ESQUERDA;
		} else if (message.equals("moveLeftReleased")) {
			nextInstruction = Instruction.STOP;
		} else if (message.equals("moveRightPressed")) {
			nextInstruction = Instruction.MOVE;
			dir = Direcao.DIREITA;
		} else if (message.equals("moveRightReleased")) {
			nextInstruction = Instruction.STOP;
		} else if (message.equals("moveUpPressed")) {
			nextInstruction = Instruction.MOVE;
			dir = Direcao.CIMA;
		} else if (message.equals("moveUpReleased")) {
			nextInstruction = Instruction.STOP;
		} else if (message.equals("moveDownPressed")) {
			nextInstruction = Instruction.MOVE;
			dir = Direcao.BAIXO;
		} else if (message.equals("moveDownReleased")) {
			nextInstruction = Instruction.STOP;
		} else if (message.equals("plantBomb")) {
			nextInstruction = Instruction.PLANTBOMB;
		}
		
	}
	
	public Jogador.Direcao getDir() {
		return dir;
	}

	public Instruction getNextInstruction() {
		return nextInstruction;
	}
}
