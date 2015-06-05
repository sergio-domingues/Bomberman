package bomberman.gui;

import java.awt.Graphics;

public interface Animation {
	
	public enum ColorPlayer {
		RED, BLUE, YELLOW, GREEN
	}
	
	void render(Graphics g, double posx, double posy);

	void update(int tempo);
}
