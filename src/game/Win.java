package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Win extends BaseClass {

    private Timer timer; // タイマーを使用

    public Win() {
        super("勝利画面", "images/syouri.jpg", "●●に勝ちました!");

        // タイマーを設定し、3.5秒後に LevelUp クラスに画面遷移
        timer = new Timer(3500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // タイマーが発火したら LevelUp クラスに画面遷移
                dispose(); // 現在のウィンドウを閉じる
                Lvup levelUp = new Lvup();
                levelUp.setVisible(true);
            }
        });
        timer.setRepeats(false); // タイマーは一度だけ発火
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // カスタムな描画コードを追加
        g.setColor(Color.WHITE);
        g.setFont(new Font("MONOSPACED", Font.PLAIN, 20)); // フォントサイズを20pxに設定
        ImageIcon imageIcon = new ImageIcon("images/teki1.png");

        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconHeight();
        int imageX = (getWidth() - imageWidth) / 2;
        int imageY = (getHeight() - imageHeight) / 2;

        Image image = imageIcon.getImage();
        g.drawImage(image, imageX, imageY, this);

        // タイマーを開始
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new Win();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}
