package Proligue01;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Ending {
    public static void main(String[] args) {
        JFrame baseFrame = new JFrame("エンディング画面");
        baseFrame.setSize(800, 600);
        baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // baseFrame の背景色をグレーに設定
        baseFrame.getContentPane().setBackground(Color.GRAY);

        String text = "｛ playerName｝くん、とても成長したな。\n"
                + "福岡県ダイバーシティ計画については知っているね？\n"
                + "今後、我が社にも大きなプロジェクトが立ち上がる\n"
                + "｛ playerName｝くん。\n"
                + "君にそのプロジェクトリーダーを任せたい\n"
                + "これからも君の力を信じてるよ\n";
                
               


        // カスタムパネルを作成してフレームに追加
        ImagePanel imagePanel = new ImagePanel("images/boss.jpg");
        TextPanel textPanel = new TextPanel(text);
        
        // パネルをフレームに追加
        baseFrame.add(imagePanel);
        baseFrame.add(textPanel);

        // フレームを表示
        baseFrame.setLayout(null);  // レイアウトマネージャを無効にする
        
//        setBackground(Color.BLACK);
        imagePanel.setBounds(100, 0, 600, 400);
        textPanel.setBounds(0, 3 * baseFrame.getHeight() / 5,baseFrame.getWidth(), 1 * baseFrame.getHeight() / 3);
//        textPanel.setBounds(0, 3 * baseFrame.getHeight() / 5,baseFrame.getWidth(), 1 * baseFrame.getHeight() / 3);
        baseFrame.setVisible(true);

        // テキストを1文字ずつ0.15秒ごとに表示するためのタイマーをセットアップ
        Timer timer = new Timer(150, null);
        timer.addActionListener(e -> {
            if (textPanel.appendNextChar()) {
                textPanel.repaint();
            } else {
                timer.stop();
            }
        });
        timer.start();
    }
}

class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        image = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}

class TextPanel extends JPanel {
    private String text;
    private StringBuilder currentText = new StringBuilder();
    private int currentLineIndex = 0;
    private int currentCharIndex = 0;

    public TextPanel(String text) {
        this.text = text;
        // 背景色を黒に設定
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


//        現在の行を取得
        String currentLine = getCurrentLine();


        // テキストを描画
        g.setColor(Color. WHITE);
        g.setFont(new Font("MONOSPACED", Font.PLAIN, 20));
        
        int margin = 20;
        int lineHeight = 40;
        int x = getWidth() / 6 + margin;
        int y = getHeight() / 4 + margin;
        
        String[] lines = currentLine.toString().split("\n");
        for (String line : lines) {
            g.drawString(line, x, y);
            y += lineHeight;
        }
    }

    // 1文字追加して再描画
    public boolean appendNextChar() {
        if (currentCharIndex < text.length()) {
            char c = text.charAt(currentCharIndex++);
            if (c == '\n') {
                currentText.append('\n');
                currentLineIndex++;
            } else {
                currentText.append(c);
            }
            return true;
        }
        return false;
    }

    // 現在の行を取得
    private String getCurrentLine() {
        String[] lines = currentText.toString().split("\n");
        return lines[currentLineIndex];
    }


}

