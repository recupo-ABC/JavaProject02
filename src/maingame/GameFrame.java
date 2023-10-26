package maingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;


public class GameFrame extends JFrame {
	static int h = 0;
	Player player;
    public GameFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("TypingEats");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
//        setBackground(Color.GRAY);
        setVisible(true);
    }
    public GameFrame(ImageIcon icon) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("TypingEats");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
//        setBackground(Color.GRAY);
        setVisible(true);
    }
    public GameFrame(ImageIcon icon, String initialText) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("TypingEats");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
//        setBackground(Color.GRAY);
        setVisible(true);
    }
    
	public void changeView(JPanel view) {
        getContentPane().removeAll();
        add(view);
        view.requestFocusInWindow();
        validate();
        if(h == 1) {
//        	Player.hp +=5;
        	EndingView();
        }else if(h == 2) {
        	Player.hp +=10;
        }else if(h == -1){
//        	Player.hp -=5;
        	EndingView();
        }
        else if(h == -2) {
        	Player.hp -=10;
        }
    }
	
	
	public void PrologueView() {

		
		String text ="“イキり”…。それは、隠し持つガラスのハートの表れ…。\n\n";
//	            + 
//	            "ウサギのような小心者の新卒サラリーマン「うさお」は、\n"
//	            + 
//	            "今日もイキりまくったファッションで\n"
//	            + "IT企業「ZOO」に二日酔い＆大遅刻で現れた。\n\n"
//	            +
//	            "そんな「うさお」に突然、社長室へ来るようにとの指示が…。\n\n"
//	            + 
//	            "“そんな…まさか、遅刻ぐらいで社長室へ…（涙）”\n"
//	            + "ウサギのハートをびくびく震わせながらも、\n"
//	            + "イキった顔で社長室へと向かう「うさお」。\n\n"
//	            + "その背中に、きりん課長が声をかける。\n"
//	            + "“社長室の前のミーティングルームで、\n"
//	            + "お前はパワハラ上司やクレーマークライアントに出会うだろう。\n\n"
//	            + "そこで大人な対応を続けることができれば、\n"
//	            + "イキりから真の社会人になれたと認められる。”";
	     // カスタムパネルを作成してフレームに追加
	        TextAndImagePanel panel = new TextAndImagePanel("src/maingame/resources/019.jpg", TitleView.text);
	        this.add(panel);
	        // フレームを表示
	        this.setVisible(true);


	        // テキストを1文字ずつ0.1秒ごとに表示するためのタイマーをセットアップ
	        Timer timer = new Timer(50, new ActionListener() {
	            private int charIndex = 0;

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (charIndex < text.length()) {
	                	panel.appendChar(text.charAt(charIndex++));
	                } else {
	                    ((Timer) e.getSource()).stop();
	                    // プロローグ終了後、Ending画面に遷移
	                    ClientMain.frame.changeView(new SimpleRPG());
	                    
	                }
	            }
	        });
	        timer.start();
	    }
	public void EndingView() {
		getContentPane().removeAll();
        
		String name = new String(TitleView.name.getText());
		String text1 = 
		"らいおん社長\n" +
		"「待っていたよ…。『"+name+"』くん…」\n\n"+
		name + "\n"+
		"「大変お待たせして、\n申し訳ありませんでした！\n" +
		"らいおん社長ッッ…！」\n\n"+
		"らいおん社長\n"+
		"「『男子たるもの、\n三日会わざれば刮目して見よ』と言うが…。\n"+
		"今日一日で、ずいぶん成長したようだね…」\n\n"+
		name + "\n"+
		"「めっそうもございませんッッ…！」\n\n"+
		"らいおん社長\n"+
		"「ところで、『福岡県ダイバーシティ計画』\nのことは知っているね？\n"+
		"今後、我が社も大きなプロジェクトを\n担当する予定だ。\n"+
		"\n"+
		"実は君に、そのリーダーを任せたい」\n";

//		うさお
//		「なんと、私ごときに…。ありがたき幸せッッ…！」
//
//		らいおん社長
//		「そして、もうひとつ…。
//		この会社を、明日から君に任せたい。
//		老兵は去るのみ、未来を若いうさぎに託したいのだ。
//
//		なってくれるね？株式会社『ZOO』の新しい社長に…」
//
//		静かに頷き、頬を涙で濡らす「うさお」。
//		そのうさぎの赤い瞳は、『ZOO』の行く末を明るく照らしていた…。

	        TextAndImagePanel panel = new TextAndImagePanel("src/maingame/resources/boss1.jpg",text1);
	        add(panel);
	        panel.requestFocusInWindow();
	        validate();
	        // フレームを表示
	        this.setVisible(true);


	        // テキストを1文字ずつ0.1秒ごとに表示するためのタイマーをセットアップ
	        Timer timer = new Timer(50, new ActionListener() {
	            private int charIndex = 0;

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (charIndex < text1.length()) {
	                	panel.appendChar(text1.charAt(charIndex++));
	                } else {
	                    ((Timer) e.getSource()).stop();
	                    // プロローグ終了後、Ending画面に遷移
	                    ClientMain.frame.EndingView1();
	                    
	                }
	            }
	        });
	        timer.start();
	    }
	public void EndingView1() {
		getContentPane().removeAll();
        
		String name = new String(TitleView.name.getText());
		String text2 =

		name + "\n"+
		"「なんと、私ごときに…。\nありがたき幸せッッ…！」\n"+
		"\n"+
		"らいおん社長\n"+
		"「そして、もうひとつ…。\n\n"+
		"この会社を、明日から君に任せたい。\n"+
		"老兵は去るのみ、\n未来を若いうさぎに託したいのだ。\n\n"+
		"なってくれるね？\n\n株式会社『ZOO』の新しい社長に…」\n\n"+

		"静かに頷き、頬を涙で濡らす「"+name+"」。\n\n"+
		"そのうさぎの赤い瞳は、\n『ZOO』の行く末を明るく照らしていた…。\n\n\n\n\n\n\n\n\n\n\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n";

	        TextAndImagePanel panel = new TextAndImagePanel("src/maingame/resources/boss1.jpg",text2);
	        add(panel);
	        panel.requestFocusInWindow();
	        validate();
	        // フレームを表示
	        this.setVisible(true);


	        // テキストを1文字ずつ0.1秒ごとに表示するためのタイマーをセットアップ
	        Timer timer = new Timer(50, new ActionListener() {
	            private int charIndex = 0;

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (charIndex < text2.length()) {
	                	panel.appendChar(text2.charAt(charIndex++));
	                } else {
	                    ((Timer) e.getSource()).stop();
	                    // プロローグ終了後、Ending画面に遷移
	                    ClientMain.frame.changeView(new TitleView());
	                    
	                }
	            }
	        });
	        timer.start();
	    }
	
}