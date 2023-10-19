
package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Proligue {
    public static void main(String[] args) {
        JFrame baseFrame = new JFrame("プロローグ画面");
        baseFrame.setSize(800, 600);
        baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String text = "みなさんは岐阜にはどういった観光スポットがあるかご存知ですか？\n" +
//                "観光スポットや温泉、旅館が多くあるのは、飛騨地方です。\n" +
//                "飛騨地方には高山、下呂温泉、白川郷（世界遺産）といった\n" +
//                "観光スポットが多くあります。高山は昔ながらの街並みが有名で、\n" +
//                "「さるぼぼ」という人形がおみやげの定番です。\n" +
//                "街には温泉、旅館もあり、下呂温泉につぐ温泉街として岐阜では有名です。\n" +
//                "下呂温泉については日本でも有数な温泉街として有名です。\n" +
//                "温泉街には昔ながらの旅館も多く、ゆっくりと過ごしたい方にはおすすめです。\n" +
//                "また白川郷は世界遺産にも登録された、合掌造りの家が並ぶ古い町です。\n" +
//                "このように、岐阜県では飛騨地方に多くの観光スポットや旅館があります。\n" +
//                "岐阜駅から約1～2時間でいけますので、" +
                "興味のある方は是非ご参考ください。";

        // カスタムパネルを作成してフレームに追加
        TextAndImagePanel panel = new TextAndImagePanel("images/tokai03.jpg", text);
        baseFrame.add(panel);

        // フレームを表示
        baseFrame.setVisible(true);

        // テキストを1文字ずつ0.1秒ごとに表示するためのタイマーをセットアップ
        Timer timer = new Timer(100, new ActionListener() {
            private int charIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (charIndex < text.length()) {
                    panel.appendChar(text.charAt(charIndex++));
                } else {
                    ((Timer) e.getSource()).stop();
                    // プロローグ終了後、Ending画面に遷移
                    showEnding();
                }
            }
        });
        timer.start();
    }
    
    private static void showEnding() {
        // Ending画面を表示
        JFrame endingFrame = new Ending();
        endingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}

class TextAndImagePanel extends JPanel {
    private Image image;
    private StringBuilder currentLine = new StringBuilder();
    private StringBuilder currentText = new StringBuilder();

    public TextAndImagePanel(String imagePath, String initialText) {
        ImageIcon icon = new ImageIcon(imagePath);
        image = icon.getImage();
        currentText.append(initialText);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        g.setColor(Color.WHITE); // ----------------------------- 文字色
        g.setFont(new Font("MONOSPACED", Font.PLAIN, 20)); // -----------フォント・文字太さ・文字サイズ
        int margin = 20; // ----------------------------- 
        int lineHeight = 40; // ------------------------行間
//        int x = margin;
        int x = getWidth() / 15;
        int y = getHeight() / 10;

        String[] lines = currentLine.toString().split("\n");
        for (String line : lines) {
            g.drawString(line, x, y);
            y += lineHeight;
        }
    }

    // 1文字追加して再描画
    public void appendChar(char c) {
        currentLine.append(c);
        if (c == '\n') {
            currentText.delete(0, currentLine.length());
            currentText.append(currentLine.toString());
        }
        repaint();
    }
}


