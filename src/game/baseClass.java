// baseClass.java
package game;

import javax.swing.JFrame;

public class baseClass extends JFrame {
    protected ImagePanel imagePanel;
    protected TextPanel textPanel;

    public baseClass(String title, String imagePath, String text) {
        super(title);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 背景色を白に設定
        getContentPane().setBackground(java.awt.Color.WHITE);

        // カスタムパネルを作成してフレームに追加
        imagePanel = new ImagePanel(imagePath);
        textPanel = new TextPanel(text);

        // パネルをフレームに追加
        add(imagePanel);
        add(textPanel);

        // フレームを表示
        setLayout(null); // レイアウトマネージャを無効にする
        imagePanel.setBounds(100, 0, 600, 400);
        textPanel.setBounds(0, 3 * getHeight() / 5, getWidth(), 1 * getHeight() / 3);
        setVisible(true);

        // TimerPanelで定義されたタイマーを開始
        startBaseTimer();
    }

    private void startBaseTimer() {
        textPanel.startTextTimer(150); // タイマーの遅延を指定
    }

    public static void main(String[] args) {
        // テスト用：適切な画像ファイルパスとテキストを指定してください
        new baseClass("Title", "path/to/image.jpg", "This is a test text.");
    }
}
