// 1031_リファクタリング：済

package maingame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Obstacle {
    private Rectangle bounds;

    /**
     * Obstacle クラスのコンストラクタ
     *
     * @param x      障害物の x 座標
     * @param y      障害物の y 座標
     * @param width  障害物の幅
     * @param height 障害物の高さ
     */
    public Obstacle(int x, int y, int width, int height) {
        // Rectangle インスタンスを作成し、障害物の位置とサイズを指定
        this.bounds = new Rectangle(x, y, width, height);
    }

    /**
     * 障害物の境界ボックスを取得するメソッド
     *
     * @return bounds 障害物の境界ボックス
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * 障害物を描画するメソッド
     *
     * @param g 描画対象の Graphics オブジェクト
     */
    public void draw(Graphics g) {
        // 障害物の描画色を設定
        g.setColor(new Color(244, 164, 96));
        // 障害物を描画
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}

