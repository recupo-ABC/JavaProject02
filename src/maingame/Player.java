package maingame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;



public class Player {
    public int x;
	public int y;
    public final int SIZE = 75;  // サイズを20x20に変更
    final int SPEED = 5;
    private int prevX, prevY;
    private Obstacle[] obstacles; 
    private BufferedImage playerImage;  // 画像を読み込むための変数を追加
    public Player(int x, int y, Obstacle[] obstacles) { 
        this.x = x;
        this.y = y;
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
    move();  // この行を追加    	
}

public void move() { // シグネチャを変更
	if (x < 0) x = 0;
	if (x > 730) x = 730;  // 750から740に変更
	if (y < 0) y = 0;
	if (y > 380) y = 380;  // 550から540に変更

    for (Obstacle obstacle : obstacles) {
        if (this.intersects(obstacle)) {
            this.undoMove();
            break;
        }
    }
}


public String useTechnique() {
    Random random = new Random();
    if (random.nextBoolean()) {
        return "イキり舌打ち！";
    } else {
        return "おとなぺこぺこ（笑）";
    }
}

public String useTechnique1() {
    Random random = new Random();
    int randomValue = random.nextInt(2);
    if (randomValue == 0) {
        return "往復ビンタ！";
    } else if(randomValue == 1) {
        return "おとなぺこぺこ（笑）";
    }else if(randomValue == 2) {
    	return "のしかかり！";
    }
	return null;
}

public void draw(Graphics g) {
    g.drawImage(playerImage, x, y, SIZE, SIZE, null);  // 画像を描画
}

public void undoMove() {
    x = prevX;
    y = prevY;
}

public boolean intersects(Obstacle obstacle) {
    Rectangle playerBounds = new Rectangle(x, y, SIZE, SIZE);
    return playerBounds.intersects(obstacle.getBounds());
}

}


