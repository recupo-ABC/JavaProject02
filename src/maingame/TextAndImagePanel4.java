package maingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextAndImagePanel4 extends JPanel {
    private Image image;
    JLabel label;
    private StringBuilder currentLine = new StringBuilder();
    private StringBuilder currentText = new StringBuilder();

    public TextAndImagePanel4(URL url,String initialText) {
    	image = Toolkit.getDefaultToolkit().createImage(url);
        currentText.append(initialText);
    }

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        g.setColor(Color.WHITE); // ----------------------------- 文字色
        g.setFont(new Font("MONOSPACED", Font.BOLD, 24)); // -----------フォント・文字太さ・文字サイズ
        int margin = 20; // ----------------------------- 
        int lineHeight = 28; // ------------------------行間
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
