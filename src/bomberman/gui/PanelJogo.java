package bomberman.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import bomberman.logic.Bomberman;
import bomberman.logic.Jogador.Direcao;
import bomberman.logic.Jogador.EstadoJogador;

@SuppressWarnings("serial")
public class PanelJogo extends JPanel implements KeyListener{

	public static final int TILESIZE = 50;
	private Bomberman bm;

	private BufferedImage wall, fixedWall, floor, jogador,bomba;

	public PanelJogo(Bomberman bm) {
		setFocusable(true);
		this.setMinimumSize(new Dimension(TILESIZE * bm.getMapa().getTamanho(), TILESIZE * bm.getMapa().getTamanho()));
		this.loadImages();
		this.setLayout(new FlowLayout());
		this.setVisible(true);
		this.bm = bm;
		this.addKeyListener(this);
		
		Bomberman.imprimeMapa(bm.getMapa().getTab(), bm.getMapa().getTamanho());
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g); // limpa fundo ...

		BufferedImage img = floor; // default image

		int xi, yi;

		for (int i = 0; i < bm.getMapa().getTamanho(); i++) {
			for (int j = 0; j < bm.getMapa().getTamanho(); j++) {

				if (bm.getMapa().getTab()[i][j] == 'X') {
					img = fixedWall;
				} else if (bm.getMapa().getTab()[i][j] == ' ') {
					img = floor;
				} else if (bm.getMapa().getTab()[i][j] == 'W') {
					img = wall;
				}

				xi = j * TILESIZE;
				yi = i * TILESIZE;

				g.drawImage(img, xi, yi, TILESIZE, TILESIZE, null);
			}
		}
		
		for (int i = 0; i < bm.getBombas().size(); i++) {
			g.drawImage(bomba, (int) (bm.getBombas().get(i).getPos().getX() * TILESIZE), (int) (bm.getBombas().get(i).getPos().getY() * TILESIZE),
					TILESIZE, TILESIZE, null);
		}

		for (int i = 0; i < bm.getJogadores().size(); i++) {
			g.drawImage(jogador, (int) (bm.getJogadores().get(i).getPos().getX() * TILESIZE), (int) (bm.getJogadores().get(i).getPos().getY() * TILESIZE),
					TILESIZE, TILESIZE, null);
		}
		

	}

	public void loadImages() {
		try {
			wall = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\Wall.png"));
			fixedWall = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\FixedWall.png"));
			floor = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\Floor.png"));
			jogador = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\Jogador.png"));
			bomba=ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\Bomba.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		bm.getJogadores().get(0).setEstadoJogador(EstadoJogador.MOVER);
		if(e.getKeyCode()==KeyEvent.VK_UP){
			bm.getJogadores().get(0).move(Direcao.CIMA, bm.getMapa());
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN){
			bm.getJogadores().get(0).move(Direcao.BAIXO, bm.getMapa());
		}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			bm.getJogadores().get(0).move(Direcao.DIREITA, bm.getMapa());
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT){
			bm.getJogadores().get(0).move(Direcao.ESQUERDA, bm.getMapa());
		}else if(e.getKeyCode()==KeyEvent.VK_SPACE){
			bm.colocarBomba(bm.getJogadores().get(0));
		}
		
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		bm.getJogadores().get(0).setEstadoJogador(EstadoJogador.PARADO);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
