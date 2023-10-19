// CharaPanel.java
package game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BaseCharaPanel extends JPanel {
    private Image image;

    public BaseCharaPanel(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        image = icon.getImage();
        
        // 背景を透明に設定
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
