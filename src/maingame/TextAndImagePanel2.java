package maingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextAndImagePanel2 extends JPanel {
    private Image image;
    JLabel label;
    private StringBuilder currentLine = new StringBuilder();
    private StringBuilder currentText = new StringBuilder();
    public TextAndImagePanel2(URL url, String initialText) {
    	image = Toolkit.getDefaultToolkit().createImage(url);
        currentText.append(initialText);
    }


	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);                    
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        g.fillRect(5, 405, getWidth() - 10, 150);
        
        g.setFont(new Font("MONOSPACED", Font.BOLD, 20));     
        
        
        g.setColor(Color.RED);
        g.fillRect(0, 400, 5, 200);
        g.fillRect(780, 400, 5, 200);
        g.fillRect(0, 400, getWidth(),5);
        g.fillRect(0, 555, getWidth(),5);
              
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        float baseline = getHeight() / 2f;
        int lineHeight = 40;
        int x = 100;
        int y =  450;

        String[] lines = currentLine.toString().split("\n");
        for (String line : lines) {
        	g.setColor(Color.WHITE);
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
