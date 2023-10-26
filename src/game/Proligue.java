//呼び出し＊Proligue.showProligue()

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
        showProligue();
    }

    public static void showProligue() {
        JFrame baseFrame = new JFrame("プロローグ画面");
        baseFrame.setSize(800, 600);
        baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String text = "「”イキり”…。それは、隠し持つガラスのハートの表れ…。\n" +
        
                "ウサギのような小心者の新卒サラリーマン「うさお」は、\n" +
                "今日もイキりまくったファッションで\n" +
                "IT企業「ZOO」に二日酔い＆大遅刻で現れた\n" +
                "\n" +
                
//                "そんな「うさお」に突然、社長室へ来るようにとの指示が…。\n" +
//                "\n" +
//                
//                "「そんな…まさか、遅刻ぐらいで社長室へ…（涙）」\n" +
//                "ウサギのハートをびくびく震わせながらも\n" +
//                "イキった顔で社長室へと向かう「うさお」。\n" +
//                "\n" +
//                
//                "その背中に、きりん課長が声をかける\n" +
//                "「社長室の前のミーティングルームで\n" +
//                "お前はパワハラ上司やクレーマークライアントに出会うだろう。\n" +
//                "\n" +
//                
//                "そこで大人な対応を続けることができれば、\n" +
//                "イキりから真の社会人になれたと認められる。\n" +
//                "\n" +
//                
//                "そして、そのミッションをクリアすることができれば、\n" +
//                "向かいの壁のどこかにある、社長室への扉が開くとのことだ…。\n" +
//                "\n" +
                
                "ウサギのハートをよりびくびくと震わせながら、\n" +
                "ミーティングルームへと入る「うさお」。\n" +
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


