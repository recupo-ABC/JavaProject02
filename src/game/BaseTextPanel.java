 // テキストを1文字ずつ0.15秒ごとに表示するためのタイマーをセットアップ

// TextPanel.java
package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BaseTextPanel extends BaseSecTimer {
    private String text;
    private StringBuilder currentText = new StringBuilder();
    private int currentLineIndex = 0;
    private int currentCharIndex = 0;

    public BaseTextPanel(String text) {
        this.text = text;
        // 背景色を黒に設定
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 現在の行を取得
        String currentLine = getCurrentLine();

        // テキストを描画
        g.setColor(Color.WHITE);
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
    private boolean appendNextChar() {
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

    public void startTextTimer(int delay) {
        startTimer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (appendNextChar()) {
                    repaint();
                } else {
                    stopTimer();
                }
            }
        });
    }
}

