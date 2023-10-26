package game;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Lvup extends BaseClass {

    public Lvup() {
        super("レベルアップ画面", "images/syouri.jpg", "Lvが●●になりました");

        BaseCharaPanel charaPanel = new BaseCharaPanel("images/Lvup.png");
        charaPanel.setBounds(250, 50, 300, 300);

        add(charaPanel);
        add(imagePanel);

        // 中央にテキストを表示するパネルを作成
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // テキストを中央に配置
        JLabel textLabel = new JLabel("Lvが●●になりました");
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("MONOSPACED", Font.PLAIN, 20));

        centerPanel.add(textLabel, BorderLayout.CENTER);

        add(centerPanel);

        // 5秒後に画面を閉じるタイマーを設定
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });
        timer.setRepeats(false); // 一度だけ実行
        timer.start();

        repaint();
    }

    private void closeWindow() {
        // ウィンドウを閉じる
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Lvup();
        });
    }
}
