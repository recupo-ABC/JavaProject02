package maingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextAndImagePanel2 extends JPanel {
    private Image image;
    JLabel label;
    private StringBuilder currentLine = new StringBuilder();
    private StringBuilder currentText = new StringBuilder();

    public TextAndImagePanel2(String string, String initialText) {
        ImageIcon icon = new ImageIcon(string);
        image = icon.getImage();
        currentText.append(initialText);
    }


	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g.fillRect(0, 400, 800, 200);
        g.setColor(Color.BLACK);
        g.setColor(Color.WHITE);
        g.setFont(new Font("MONOSPACED", Font.PLAIN, 20));
        
        int lineHeight = 40;
        int x = 100;
        int y =  500;

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
