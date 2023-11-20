package maingame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Enemy {
    public int x;
	public int y;
    Color color;
    public final int SIZE = 75;  // サイズを50x50に変更
    final int SPEED = 15;
    private String encounterMessage;
    private Obstacle[] obstacles;
    private BufferedImage enemyImage;  // 敵の画像を読み込むための変数を追加

    private int prevX, prevY;
   
    public String[] secondaryMessages; // 3つのセカンダリメッセージを格納するための配列
    private int currentSecondaryMessageIndex = 0; // 現在表示しているセカンダリメッセージのインデックス
	static int z;
    

    public Enemy(int x, int y, Color color, String encounterMessage, Obstacle[] obstacles) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.encounterMessage = encounterMessage;
        this.obstacles = obstacles;

        // 画像の読み込み
        try {
            if (color == Color.RED) {
                enemyImage = ImageIO.read(getClass().getResource("resources/images/クレーマー.png"));
            } else if (color == Color.BLUE) {
                enemyImage = ImageIO.read(getClass().getResource("resources/images/パワハラ上司.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    
        if (color == Color.RED) {
            secondaryMessages = new String[]{
                "モンスタークレーマー：\n「あのねえお客様は神様だから！」",
                "「つまり私は神様だから！」",
                "「だから出来ないとかありえないから！」"
            };
        } else if (color == Color.BLUE) {
            secondaryMessages = new String[]{
                "パワハラ上司：\n「お前同じこと何度言わせんの？」",
                "「お前さあ小学校卒業してる？」",
                "「お前ほんとラ・フランスだな（笑）」"
            };
        }
    }

    public String getNextSecondaryMessage() {
        if (currentSecondaryMessageIndex < secondaryMessages.length) {
            return secondaryMessages[currentSecondaryMessageIndex++];
        } else {
            return null; // これにより、すべてのセカンダリメッセージが表示された後にnullを返す
        }
    }


public String getEncounterMessage() {
    return encounterMessage;
}

public void move(Player player) {
    prevX = x;
    prevY = y;
    if (x < player.x) x += SPEED;
    else if (x > player.x) x -= SPEED;

    if (y < player.y) y += SPEED;
    else if (y > player.y) y -= SPEED;

    for (Obstacle obstacle : obstacles) {
        if (this.intersects(obstacle)) {
            this.undoMove();
            this.findClearPath(obstacle);
            break;
        }
    }
}

private void findClearPath(Obstacle obstacle) {
    int[] dx = {-SPEED, 0, SPEED, 0};
    int[] dy = {0, -SPEED, 0, SPEED};
    for (int i = 0; i < 4; i++) {
        int newX = x + dx[i];
        int newY = y + dy[i];
        Rectangle newPos = new Rectangle(newX, newY, SIZE, SIZE);
        if (!newPos.intersects(obstacle.getBounds())) {
            x = newX;
            y = newY;
            return;
        }
    }
}


public void draw(Graphics g) {
    g.drawImage(enemyImage, x, y, SIZE, SIZE, null);  // 画像を描画
}

public void undoMove() {
    x = prevX;
    y = prevY;
}

public boolean intersects(Obstacle obstacle) {
    Rectangle enemyBounds = new Rectangle(x, y, SIZE, SIZE);
    return enemyBounds.intersects(obstacle.getBounds());
}

// 既存のコードの中での位置： Enemy クラスの最後に追加
public void changeDirection(Player player) {
    int deltaX = player.x - x;
    int deltaY = player.y - y;

    if (Math.abs(deltaX) > Math.abs(deltaY)) {
        x += deltaX > 0 ? -SPEED : SPEED;
    } else {
        y += deltaY > 0 ? -SPEED : SPEED;
    }
}
}
