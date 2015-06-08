package bomberman.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bomberman.gui.Animation;
import bomberman.logic.*;
import bomberman.logic.Builder.Difficulty;

public class Teste {

	Bomberman bm;
	
	
	@Before
	public void setUp() throws Exception {
		
		bm = new Bomberman();		
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	
	
	@Test
	public void test1() throws Exception {
		
		setUp();
		
		assertEquals('X',bm.getMapa().getTab()[0][0]);
		
		bm.adicionarJogador();
		
		assertEquals("PARADO", bm.getJogadores().get(0).getEstadoJogador().toString());
		assertEquals("ACTIVO",bm.getJogadores().get(0).getEstado().toString());
		assertEquals('1',bm.getJogadores().get(0).getSigla());
		
		
		bm.getJogadores().get(0).setColor(Animation.ColorPlayer.BLUE);	
		assertEquals("BLUE",bm.getJogadores().get(0).getColor().toString());
		
		bm.getJogadores().get(0).setVelocidade(1);
		assertEquals(1, (int) bm.getJogadores().get(0).getVelocidade());
		
		bm.moveJogador(bm.getJogadores().get(0), Jogador.Direcao.DIREITA);		
		assertEquals(2, (int) bm.getJogadores().get(0).getPos().getX());		
		assertEquals("DIREITA",bm.getJogadores().get(0).getUltimaDirecao().toString());
		
		bm.getJogadores().get(0).setVelocidade(0.125);
		bm.getJogadores().get(0).updateVelocidade();
		
		assertEquals(0.20,bm.getJogadores().get(0).getVelocidade(),0.001);
		
		bm.colocarBomba(bm.getJogadores().get(0));
		assertEquals(1, bm.getJogadores().get(0).getNrBombas());
		
		
		assertEquals("ACTIVO",bm.getBombas().get(0).getEstado().toString());
		assertEquals("ARMADA", bm.getBombas().get(0).getEstadoBomba().toString());
		assertEquals(2,(int) bm.getBombas().get(0).getPos().getX());
		assertEquals(true,bm.getBombas().get(0).colide(bm.getJogadores().get(0)));
		
		bm.updateBomba(2000);		
		assertEquals("EXPLODINDO", bm.getBombas().get(0).getEstadoBomba().toString());
		assertEquals(1,bm.getJogadores().get(0).getVidas());
		assertEquals("INVULNERAVEL",bm.getJogadores().get(0).getEstadoVuln().toString());	
		
		bm.updateBomba(800);
		bm.updateBomba(0);
		assertEquals(0,bm.getBombas().size());		
	}

	@Test
	public void Test2() throws Exception{
		
		setUp();	
		bm.adicionarJogador();
		
		bm.getJogadores().get(0).setVelocidade(1);	
		
		bm.colocarBomba(bm.getJogadores().get(0));
		bm.moveJogador(bm.getJogadores().get(0), Jogador.Direcao.BAIXO);
		
		bm.updateBomba(2000);
		bm.updateBomba(800);
		bm.updateBomba(0);
		
		
	}
	
}
