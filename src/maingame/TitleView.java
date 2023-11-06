
package maingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * タイトル画面を表示するパネルクラス
 */
public class TitleView extends JPanel implements ActionListener {
    private Image backgroundImage;
    static JTextField name;
    private JLabel label;
    private static JButton startButton;

    /**
     * コンストラクタ
     */
    public TitleView() {
        initializeComponents();
        setLayout(null);
        setBounds(0, 0, 800, 600);
        setOpaque(false);
        setFocusable(true);
        setVisible(true);
        new MyKeyListener(this);
    }

    /**
     * コンポーネントの初期化
     */
    private void initializeComponents() {
        // 画像の読み込み
        backgroundImage = Toolkit.getDefaultToolkit().createImage("src/maingame/resources/mainImg2.jpg");

        // タイトルラベル
        JLabel title = new JLabel("イキリーマン伝説");
        title.setForeground(Color.BLACK);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setBounds(260, 250, 400, 100);

        // サブタイトルラベル
        JLabel title1 = new JLabel("～シン・社会人へのキャリアップ～");
        title1.setForeground(Color.BLACK);
        title1.setFont(new Font("SansSerif", Font.BOLD, 24));
        title1.setBounds(200, 300, 500, 100);

        // 入力ラベル
        JLabel title2 = new JLabel("プレイヤーネームを入力して下さい");
        title2.setForeground(Color.BLACK);
        title2.setFont(new Font("SansSerif", Font.BOLD, 18));
        title2.setBounds(250, 500, 300, 30);

        // プレイヤーネーム入力フィールド
        name = new JTextField("", 20);
        name.setBounds(320, 470, 150, 30);

        // 開始ボタン
        startButton = new JButton("");
        startButton.setBounds(170, 210, 500, 200);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.addActionListener(this);
        startButton.setActionCommand("start");

        // コンポーネントの追加
        add(title);
        add(title1);
        add(title2);
        add(name);
        add(startButton);
    }

    /**
     * ボタンクリック時のアクション
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("start")) {
            label.setText(name.getText());
            remove(startButton);
            remove(name);
            ClientMain.getFrame().PrologueView();
            return;
        }
    }

    /**
     * パネルの描画処理
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, this);
        super.paint(g);
    }

    /**
     * キーリスナークラス
     */
    private class MyKeyListener implements KeyListener {
        // 貼り付け先を保持
        TitleView panel;

        // コンストラクタ
        MyKeyListener(TitleView p) {
            super();
            panel = p;
            panel.addKeyListener(this);
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            // エンターキーが押された場合の処理
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                ClientMain.getFrame().changeView(new SimpleRPG(), 0);
            }
        }
    }
}


