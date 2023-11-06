// 1030_リファクタリング：済

package maingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class GameFrame extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int DELAY = 50;
    public static int h;

    private TextAndImagePanel currentPanel;

    public GameFrame() {
        initializeFrame();
    }

    public GameFrame(ImageIcon icon) {
        initializeFrame();
    }

    public GameFrame(ImageIcon icon, String initialText) {
        initializeFrame();
    }

    // フレームの初期化メソッド
    private void initializeFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("TypingEats");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ゲーム画面を切り替えるメソッド
    public void changeView(SimpleRPG simpleRPG, int healthChange) {
        getContentPane().removeAll();
        add(simpleRPG);
        simpleRPG.requestFocusInWindow();
        validate();

        // Healthの変更
        Player.setHp(Player.getHp() + healthChange);

        if (healthChange != 0) {
            // Ending画面に遷移
            ClientMain.getFrame().changeView(new SimpleRPG(), 0);
        }
    }

    // プロローグ画面表示メソッド
    public void PrologueView() {
        String text = "“イキり”…。それは、隠し持つガラスのハートの表れ…。\n\n" 
	            + 
	            "ウサギのような小心者の新卒サラリーマン「うさお」は、\n"
	            + 
	            "今日もイキりまくったファッションで\n"
	            + "IT企業「ZOO」に二日酔い＆大遅刻で現れた。\n\n"
	            +
	            "そんな「うさお」に突然、社長室へ来るようにとの指示が…。\n\n"
	            + 
	            "“そんな…まさか、遅刻ぐらいで社長室へ…（涙）”\n"
	            + "ウサギのハートをびくびく震わせながらも、\n"
	            + "イキった顔で社長室へと向かう「うさお」。\n\n"
	            + "その背中に、きりん課長が声をかける。\n"
	            + "“社長室の前のミーティングルームで、\n"
	            + "お前はパワハラ上司やクレーマークライアントに出会うだろう。\n\n"
	            + "そこで大人な対応を続けることができれば、\n"
	            + "イキりから真の社会人になれたと認められる。";

        currentPanel = new TextAndImagePanel("src/maingame/resources/019.jpg", text);
        add(currentPanel);
        setVisible(true);

        Timer timer = new Timer(DELAY, new TextDisplayTimerAction(text));
        timer.start();
    }

    // エンディング画面表示メソッド
    public void EndingView(String text) {
        getContentPane().removeAll();
        currentPanel = new TextAndImagePanel("src/maingame/resources/boss1.jpg", text);
        add(currentPanel);
        currentPanel.requestFocusInWindow();
        validate();

        Timer timer = new Timer(DELAY, new TextDisplayTimerAction(text));
        timer.start();
    }

    // テキスト表示のためのタイマーアクションクラス
    private class TextDisplayTimerAction implements ActionListener {
        private final String text;
        private int charIndex = 0;

        // コンストラクタ
        public TextDisplayTimerAction(String text) {
            this.text = text;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (charIndex < text.length()) {
                currentPanel.appendChar(text.charAt(charIndex++));
            } else {
                ((Timer) e.getSource()).stop();
                if (text.contains("イキりから真の社会人になれたと認められる。")) {
                    // プロローグ終了後、Ending画面に遷移
                    ClientMain.getFrame().changeView(new SimpleRPG(), 0);
                }
            }
        }
    }
}
