package bomberman.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bomberman.logic.Highscore;

@SuppressWarnings("serial")
public class HighScorePanel extends JPanel {
	private JFrame frame;
	private Image vermelho, verde, amarelo, azul, winner;

	HighScorePanel(int idWinner, int score, JFrame frame) {
		this.frame = frame;
		frame.setTitle("Game Over");
		this.setLayout(null);
		this.setBounds(100, 100, frame.getBounds().width - 100, frame.getBounds().height - 100);
		loadImagens();

		JLabel winner = new JLabel();
		ImageIcon iconWinner = new ImageIcon(getScaledImage(this.winner, 500, 500));
		winner.setIcon(iconWinner);
		winner.setBounds(0, 0, 600, 600);

		JLabel winnerPlayer = new JLabel();
		Image winnerImage = null;

		switch (idWinner) {
		case 1:
			winnerImage = vermelho;
			break;
		case 2:
			winnerImage = azul;
			break;
		case 3:
			winnerImage = verde;
			break;
		case 4:
			winnerImage = amarelo;
			break;
		}

		ImageIcon winnerPlayerIcon = new ImageIcon(getScaledImage(getPartImage(winnerImage, 70, 70), 150, 150));
		winnerPlayer.setIcon(winnerPlayerIcon);

		winnerPlayer.setBounds(170, 100, 300, 250);

		JLabel scoreLabel = new JLabel();
		scoreLabel.setBounds(100, 0, 400, 100);
		scoreLabel.setFont(new Font("Calibri", Font.BOLD, 40));
		scoreLabel.setForeground(Color.RED);

		Highscore highscore = new Highscore();

		if (highscore.getScore() <= score) {
			scoreLabel.setText("Nova Highscore: " + score);
			highscore.setScore(score);
			highscore.saveScore();
		} else {
			scoreLabel.setText("Pontuacao: " + score);
		}

		JButton back = new JButton("back");
		back.setBounds(500, 500, 100, 50);

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.gc();
				Gui.getInstance().initMainMenu();
			}
		});

		this.add(scoreLabel);
		this.add(winnerPlayer);
		this.add(winner);

		this.add(back);

	}

	private void loadImagens() {
		try {
			this.vermelho = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerVermelho.png"));
			this.verde = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerVerde.png"));
			this.azul = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerAzul.png"));
			this.amarelo = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\playerAmarelo.png"));
			this.winner = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\winner.png"));

		} catch (IOException e) {
			System.err.println("Erro carregar imagens!");
			e.printStackTrace();
		}

	}

	private Image getPartImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		// g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		// RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

	private Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

}
