
package Proligue01;

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

public class Proligue01 {
    public static void main(String[] args) {
        JFrame baseFrame = new JFrame("プロローグ画面");
        baseFrame.setSize(800, 600);
        baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String text = "みなさんは岐阜にはどういった観光スポットがあるかご存知ですか？岐阜は実は美濃地方と飛騨地方に\n" +
                "分かれており、観光スポットや温泉、旅館が多くあるのは、飛騨地方です。そのため、岐阜に行こうと\n" +
                "思ったときは、 まず飛騨地方で探してください。飛騨地方には高山、下呂温泉、白川郷（世界遺産）\n" +
                "といった観光スポットが多くあります。高山は昔ながらの街並みが有名で、「さるぼぼ」という人形が\n" +
                "おみやげの定番です。\n" +
                "街には温泉、旅館もあり、下呂温泉につぐ温泉街として岐阜では有名です。下呂温泉については日本\n" +
                "でも有数な温泉街として有名なので、聞いたことがある方も多いことでしょう。\n" +
                "温泉街には昔ながらの旅館も多く、ゆっくりと過ごしたい方にはおすすめです。また白川郷は世界遺産\n" +
                "にも登録された、合掌造りの家が並ぶ古い町です。雪国ならではの風景が見たい方にはおすすめです。\n" +
                "このように、岐阜県では飛騨地方に多くの観光スポットや旅館があります。岐阜駅から約1～2時間で\n" +
                "いけますので、興味のある方は是非ご参考ください。";

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
                }
            }
        });
        timer.start();
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
        g.setFont(new Font("MONOSPACED", Font.BOLD, 20)); // -----------フォント・文字太さ・文字サイズ
        int margin = 20; // ----------------------------- 
        int lineHeight = 40; // ------------------------行間
//        int x = margin;
        int x = getWidth() / 6 + margin;
        int y = getHeight() / 4 + margin;

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



//--------------------------------------ぱたーーん２

//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.RenderingHints;
//import java.awt.font.FontRenderContext;
//import java.awt.geom.Rectangle2D;
//
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.Timer;
//
//public class Proligue01 {
//    public static void main(String[] args) {
//        JFrame baseFrame = new JFrame("岐阜の観光スポット");
//        baseFrame.setSize(800, 600);
//        baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//      String text = "みなさんは岐阜にはどういった観光スポットがあるかご存知ですか？岐阜は実は美濃地方と飛騨地方に\n" +
//      "分かれており、観光スポットや温泉、旅館が多くあるのは、飛騨地方です。そのため、岐阜に行こうと\n" +
//      "思ったときは、 まず飛騨地方で探してください。飛騨地方には高山、下呂温泉、白川郷（世界遺産）\n" +
//      "といった観光スポットが多くあります。高山は昔ながらの街並みが有名で、「さるぼぼ」という人形が\n" +
//      "おみやげの定番です。\n" +
//      "街には温泉、旅館もあり、下呂温泉につぐ温泉街として岐阜では有名です。下呂温泉については日本\n" +
//      "でも有数な温泉街として有名なので、聞いたことがある方も多いことでしょう。\n" +
//      "温泉街には昔ながらの旅館も多く、ゆっくりと過ごしたい方にはおすすめです。また白川郷は世界遺産\n" +
//      "にも登録された、合掌造りの家が並ぶ古い町です。雪国ならではの風景が見たい方にはおすすめです。\n" +
//      "このように、岐阜県では飛騨地方に多くの観光スポットや旅館があります。岐阜駅から約1～2時間で\n" +
//      "いけますので、興味のある方は是非ご参考ください。";
//
//        // カスタムパネルを作成してフレームに追加
//        TextAndImagePanel panel = new TextAndImagePanel("images/tokai02.jpg", text);
//        baseFrame.add(panel);
//
//        // フレームを表示
//        baseFrame.setVisible(true);
//
//        // テキストを1文字ずつ0.1秒ごとに表示するためのタイマーをセットアップ
//        Timer timer = new Timer(100, null);
//        timer.addActionListener(e -> {
//            if (panel.appendNextChar()) {
//                panel.repaint();
//            } else {
//                timer.stop();
//            }
//        });
//        timer.start();
//    }
//}
//
//class TextAndImagePanel extends JPanel {
//    private Image image;
//    private String text;
//    private StringBuilder currentText = new StringBuilder();
//    private int currentLineIndex = 0;
//    private int currentCharIndex = 0;
//
//    public TextAndImagePanel(String imagePath, String text) {
//        ImageIcon icon = new ImageIcon(imagePath);
//        image = icon.getImage();
//        this.text = text;
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
//
//        // 影の色とフォントを指定
//        g.setColor(Color.WHITE);
//        g.setFont(new Font("SansSerif", Font.PLAIN, 40));
//
//        // Graphics2Dにキャストしてアンチエイリアスを有効にする
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        int margin = 20;
//        int lineHeight = 40;
//        int x = getWidth() / 6 + margin;
//        int y = getHeight() / 4 + margin;
//
//        String currentLine = getCurrentLine();
//        // 影を描画
//        drawString(g2d, currentLine, x + 2, y + 2);
//
//        // テキストを描画
//        g.setColor(Color.BLACK);
//        drawString(g2d, currentLine, x, y);
//    }
//
//    // 1文字追加して再描画
//    public boolean appendNextChar() {
//        if (currentCharIndex < text.length()) {
//            char c = text.charAt(currentCharIndex++);
//            if (c == '\n') {
//                currentText.append('\n');
//                currentLineIndex++;
//            } else {
//                currentText.append(c);
//            }
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private String getCurrentLine() {
//        String[] lines = currentText.toString().split("\n");
//        if (currentLineIndex < lines.length) {
//            return lines[currentLineIndex];
//        }
//        return "";
//    }
//
//    private void drawString(Graphics2D g, String text, int x, int y) {
//        FontRenderContext frc = g.getFontRenderContext();
//        Rectangle2D bounds = g.getFont().getStringBounds(text, frc);
//        int ascent = (int) -bounds.getY();
//
//        g.drawString(text, x, y + ascent);
//    }
//}


