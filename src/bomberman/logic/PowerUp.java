package bomberman.logic;

public class PowerUp extends Peca {

	public static enum Type{RAIO,VELOCIDADE,BOMBA};
	
	PowerUp(float x, float y, char sigla) {
		super(x, y, sigla);
		
	}

}
