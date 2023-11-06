// 1031_リファクタリング：済

package maingame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Player {
    int x;
    int y;
    private static int hp;
    final int SIZE = 75;
    private final int SPEED = 5;
    private int prevX;
    private int prevY;
    private Obstacle[] obstacles;
    private BufferedImage playerImage;

    public Player(int x, int y, int hp, Obstacle[] obstacles) {
        this.x = x;
        this.y = y;
        Player.setHp(hp);
        this.obstacles = obstacles;

        // 画像の読み込み
        try {
            playerImage = ImageIO.read(getClass().getResource("resources/主人公.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent e) {
        prevX = x;
        prevY = y;

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            x -= SPEED;
        } else if (key == KeyEvent.VK_RIGHT) {
            x += SPEED;
        } else if (key == KeyEvent.VK_UP) {
            y -= SPEED;
        } else if (key == KeyEvent.VK_DOWN) {
            y += SPEED;
        }
        move();
    }

    private void move() {
        x = Math.max(0, Math.min(730, x));
        y = Math.max(0, Math.min(380, y));

        for (Obstacle obstacle : obstacles) {
            if (intersects(obstacle)) {
                undoMove();
                break;
            }
        }
    }

    public String useTechnique() {
        Random random = new Random();
        return random.nextBoolean() ? "イキり舌打ち！" : "おとなぺこぺこ（笑）";
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, x, y, SIZE, SIZE, null);
    }

    void undoMove() {
        x = prevX;
        y = prevY;
    }

    boolean intersects(Obstacle obstacle) {
        Rectangle playerBounds = new Rectangle(x, y, SIZE, SIZE);
        return playerBounds.intersects(obstacle.getBounds());
    }



    public void mouseClicked(MouseEvent e) {
        // TODO 自動生成されたメソッド・スタブ
    }

	/**
	 * @return hp
	 */
	public static int getHp() {
		return hp;
	}

	/**
	 * @param hp セットする hp
	 */
	public static void setHp(int hp) {
		Player.hp = hp;
	}


}


