package maingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;


public class GameFrame extends JFrame {
	static int Z = 1;
	private int hp = 10;
	boolean end = false;
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
        GameFrame.Z++; 
    }	
	
	public int getHP() {
		return hp ;
	}
	
	public void setHP(int hp) {
		this.hp = hp;
	}
	
	public static void playSoundEffect(String soundFilePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundFilePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
	
	public void PrologueView() {
		String name = new String(TitleView.name.getText());
		String text ="“イキり”…。それは、隠し持つガラスのハートの表れ…。\n\n"
	            + 
	            "ウサギのような小心者の新卒サラリーマン「"+ name+ "」は、\n"
	            + 
	            "今日もイキりまくったファッションで\n"
	            + "IT企業「ZOO」に二日酔い＆大遅刻で現れた。\n\n"
	            +
	            "そんな「" + name + " 」に突然、社長室へ来るようにとの指示が…。\n\n"
	            + 
	            "“そんな…まさか、遅刻ぐらいで社長室へ…（涙）”\n"
	            + "ウサギのハートをびくびく震わせながらも、\n"
	            + "イキった顔で社長室へと向かう「うさお」。\n\n"
	            + "その背中に、きりん課長が声をかける。\n"
	            + "“社長室の前のミーティングルームで、\n"
	            + "お前はパワハラ上司やクレーマークライアントに出会うだろう。\n\n"
	            + "そこで大人な対応を続けることができれば、\n"
	            + "イキりから真の社会人になれたと認められる。”";
	     // カスタムパネルを作成してフレームに追加
	        TextAndImagePanel panel = new TextAndImagePanel("src/maingame/resources/prologue2.png", TitleView.text);
	        this.add(panel);
	     // スキップボタンの作成
		    JButton skipButton = new JButton(new ImageIcon("src/maingame/resources/skip_b.png"));

		    int buttonWidth = 200;
		    int buttonHeight = 80;
		    panel.setLayout(null); 
		    // 重要！レイアウトマネージャを無効化
		    skipButton.setBounds(500,450,buttonWidth,buttonHeight);
		    skipButton.setOpaque(false);
		    skipButton.setContentAreaFilled(false);
		    skipButton.setBorderPainted(false);

		   

		    skipButton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            PrologueView1();
		        }
		    });
		    panel.add(skipButton);
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
	                }
	            }
	        });
	        timer.start();
	    }
public void PrologueView1() {
		
		String name = TitleView.name.getText();
		getContentPane().removeAll();

		
		String text ="「社長室の前のミーティングルームで、\n"
	            + "お前はパワハラ上司や\n"
				+"クレーマークライアントに出会うだろう。\n\n"
	            +
	             "そこで大人な対応を続けることができれば、\n"
	            + "イキりから真の社会人になれたと認められる。\n\n"
	            +
	             "そして、そのミッションを\n"
	            +"クリアすることができれば \n"	            
	            + "向かいの壁のどこかにある、\n"
	            +"社長室への扉が開くとのことだ…。」\n\n"	           
                +
	            "小物なハートを震わせながら、\n"
	            +"平静を装いミーティングルームへ向かう「"+name+"」。\n\n"
	            +
	            "果たして「"+name+"」はシン・社会人となり、\n"
	            +"社長に対面できるのだろうか…。\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n」"
	       

	            ;
	     // カスタムパネルを作成してフレームに追加
	        TextAndImagePanel panel = new TextAndImagePanel("src/maingame/resources/prologue1.png", TitleView.text);
	        panel.setLayout(null); 
		    // 重要！レイアウトマネージャを無効化
		    
		    this.add(panel);
		    
		 // スキップボタンの作成
		    JButton skipButton = new JButton(new ImageIcon("src/maingame/resources/start-button.png"));

		    int buttonWidth = 300;
		    int buttonHeight = 80;

		    skipButton.setBounds(480,450,buttonWidth,buttonHeight);
		    skipButton.setOpaque(false);
		    skipButton.setContentAreaFilled(false);
		    skipButton.setBorderPainted(false);

		   

		    skipButton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            changeView(new SimpleRPG());
		        }
		    });
		    panel.add(skipButton);
		    
		    // フレームを表示
		    this.setVisible(true);

	        // テキストを1文字ずつ0.1秒ごとに表示するためのタイマーをセットアップ
	        Timer timer = new Timer(100, new ActionListener() {
	            private int charIndex = 0;

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (charIndex < text.length()) {
	                	panel.appendChar(text.charAt(charIndex++));
	                } else {
	                    ((Timer) e.getSource()).stop();
	                    // プロローグ終了後、Ending画面に遷移
	                    
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

	        TextAndImagePanel panel = new TextAndImagePanel("src/maingame/resources/prologue1.png",text1);
	        add(panel);
	        panel.requestFocusInWindow();
	        validate();
	     // スキップボタンの作成
	        panel.setLayout(null); 
		    // 重要！レイアウトマネージャを無効化
		    JButton skipButton = new JButton(new ImageIcon("src/maingame/resources/skip_b.png"));

		    int buttonWidth = 200;
		    int buttonHeight = 80;

		    skipButton.setBounds(500,450,buttonWidth,buttonHeight);
		    skipButton.setOpaque(false);
		    skipButton.setContentAreaFilled(false);
		    skipButton.setBorderPainted(false);

		   

		    skipButton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            EndingView1();
		        }
		    });
	        // フレームを表示
	        this.setVisible(true);
	        panel.add(skipButton);

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
	     // スキップボタンの作成
		    JButton skipButton = new JButton(new ImageIcon("src/maingame/resources/skip_b.png"));

		    int buttonWidth = 200;
		    int buttonHeight = 80;
		    panel.setLayout(null); 
		    // 重要！レイアウトマネージャを無効化
		    skipButton.setBounds(500,450,buttonWidth,buttonHeight);
		    skipButton.setOpaque(false);
		    skipButton.setContentAreaFilled(false);
		    skipButton.setBorderPainted(false);

		   

		    skipButton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            changeView(new TitleView());
		        }
		    });
		    panel.add(skipButton);
	        // フレームを表示
	        this.setVisible(true);
	        end = true;

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
	                }
	            }
	        });
	        timer.start();
	    }
	
}