//1030_リファクタリング：済

package maingame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy {
    // 固定のサイズと速度
    private static final int SIZE = 75;
    private static final int SPEED = 15;

    // 敵の位置、色、メッセージ、障害物、画像などのプロパティ
    private int x;
    private int y;
    private Color color;
    private String encounterMessage;
    private Obstacle[] obstacles;
    private BufferedImage enemyImage;
    private int prevX, prevY;
    private String[] secondaryMessages;
    private int currentSecondaryMessageIndex = 0;

    // コンストラクタ
    public Enemy(int x, int y, Color color, String encounterMessage, Obstacle[] obstacles) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.encounterMessage = encounterMessage;
        this.obstacles = obstacles;
        loadEnemyImage();
        initSecondaryMessages();
    }

    // 画像の読み込み
    private void loadEnemyImage() {
        try {
            // 色によって異なる画像を選択
            String imagePath = (color == Color.RED) ? "resources/クレーマー.png" : "resources/パワハラ上司.png";
            enemyImage = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 補助メッセージの初期化
    private void initSecondaryMessages() {
        if (color == Color.RED) {
            setSecondaryMessages(new String[]{
                "モンスタークレーマー：\n「あなたの会社いったいどうなってるの？」",
                "「頼んだ商品と違うじゃない！」",
                "「今すぐ謝罪しに来なさい！」"
            });
        } else if (color == Color.BLUE) {
            setSecondaryMessages(new String[]{
                "パワハラ上司：\n「おい!あの資料はまだ出来ていないのか！？",
                "あぁ！？誰が喋って良いと言った？",
                "貴様共のくだらぬ意思で物を言うな",
                "私に聞かれたことにのみ答えよ"
            });
        }
    }

    // 次の補助メッセージを取得
    public String getNextSecondaryMessage() {
        return (currentSecondaryMessageIndex < getSecondaryMessages().length)
                ? getSecondaryMessages()[currentSecondaryMessageIndex++]
                : null;
    }

    // エンカウントメッセージの取得
    public String getEncounterMessage() {
        return encounterMessage;
    }

    // プレイヤーに追従して移動
    public void move(Player player) {
        storePreviousPosition();
        x += Integer.compare(player.x, x) * SPEED;
        y += Integer.compare(player.y, y) * SPEED;
        handleObstacleCollision();
    }

    // 以前の位置を保存
    private void storePreviousPosition() {
        prevX = x;
        prevY = y;
    }

    // 障害物との衝突の処理
    private void handleObstacleCollision() {
        for (Obstacle obstacle : obstacles) {
            if (intersects(obstacle)) {
                undoMove();
                findClearPath(obstacle);
                break;
            }
        }
    }

    // 障害物を避けて新しい位置を見つける
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

    // 敵の描画
    public void draw(Graphics g) {
        g.drawImage(enemyImage, x, y, SIZE, SIZE, null);
    }

    // 移動を元に戻す
    public void undoMove() {
        x = prevX;
        y = prevY;
    }

    // 障害物との交差判定
    public boolean intersects(Obstacle obstacle) {
        Rectangle enemyBounds = new Rectangle(x, y, SIZE, SIZE);
        return enemyBounds.intersects(obstacle.getBounds());
    }

    // プレイヤーに対して方向を変更
    public void changeDirection(Player player) {
        int deltaX = player.x - x;
        int deltaY = player.y - y;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            x += Integer.compare(deltaX, 0) * SPEED;
        } else {
            y += Integer.compare(deltaY, 0) * SPEED;
        }
    }

    // Getter methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return SIZE;
    }

    public String[] getSecondaryMessages() {
        return secondaryMessages;
    }

    public void setSecondaryMessages(String[] secondaryMessages) {
        this.secondaryMessages = secondaryMessages;
    }
}
