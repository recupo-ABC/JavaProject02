// 1031_リファクタリング：済


package maingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


	// テキストと画像を表示するパネルクラス
	public class TextAndImagePanel extends JPanel {
	    private Image image;
	    private StringBuilder currentLine = new StringBuilder();
	    private StringBuilder currentText = new StringBuilder();


    //コンストラクタ
    //@param imagePath   画像のファイルパス
    //@param initialText 初期テキスト
    public TextAndImagePanel(String imagePath, String initialText) {
        loadImage(imagePath);
        currentText.append(initialText);
    }

    //画像の読み込み
    private void loadImage(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        image = icon.getImage();
    }


    //パネルの描画処理
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawImage(g);
        drawText(g);
    }


    //画像の描画
    private void drawImage(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }


    //テキストの描画
    private void drawText(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        int lineHeight = 25;
        int x = getWidth() / 15;
        int y = getHeight() / 10;

        String[] lines = currentLine.toString().split("\n");
        for (String line : lines) {
            g.drawString(line, x, y);
            y += lineHeight;
        }
    }


    //1文字追加して再描画
    public void appendChar(char c) {
        currentLine.append(c);
        if (c == '\n') {
            updateCurrentText();
        }
        repaint();
    }


    //currentLineをcurrentTextに反映し、currentLineをクリア
    private void updateCurrentText() {
        currentText.delete(0, currentLine.length());
        currentText.append(currentLine.toString());
    }
}

