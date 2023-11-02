package maingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.JButton;



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
        if(view instanceof TitleView) {
            // TitleViewへの遷移時は何もしない
            return;
        }

        
        
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
	    String name = TitleView.name.getText();
	    String text = "“イキり”…。\n"
	            + "それは、ガラスのハートを覆い隠す薄氷の鎧…。\n\n"
	            + "ウサギのような小心者の新卒サラリーマン「" + name + "」は、\n"
	            + "今日もイキったファッションで\n"
	            + "IT企業「ZOO」に遅刻かつ二日酔いで現れた。\n\n"
	            + "そんな「" + name + "」に突然、社長室へ来るようにとの指示が…。\n\n"
	            + "“そんな…、遅刻ぐらいで社長室へ…（涙）”\n"
	            + "小さなハートをびくびく震わせながらも、\n"
	            + "イキった顔で社長室へと向かう「" + name + "」。\n\n"
	            + "その背中に、きりん課長が声をかける。\n";

	    // カスタムパネルを作成してフレームに追加
	    TextAndImagePanel panel = new TextAndImagePanel("src/maingame/resources/prologue2.png", TitleView.text);
	    
	    panel.setLayout(null); 
	    // 重要！レイアウトマネージャを無効化
	    
	    this.add(panel);
	    
	 // スキップボタンの作成
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
	                // プロローグ終了後、PrologueView1画面に遷移
	                PrologueView1();
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
		"らいおん社長ッッ…！」\n\n"+
		"らいおん社長\n"+
		"「『男子たるもの、\n三日会わざれば刮目して見よ』と言うが…。\n"+
		"今日一日で、ずいぶん成長したようだね…」\n\n"+
		"「めっそうもございません…！」\n\n"+
		"らいおん社長\n"+
		"「ところで、『福岡県ダイバーシティ計画』\nのことは知っているね？\n"+
		"今後、我が社も大きなプロジェクトを\n担当する予定だ。\n"+
		"実は君に、そのリーダーを任せたい」\n";



	        TextAndImagePanel panel = new TextAndImagePanel("src/maingame/resources/boss1.jpg",text1);
	        add(panel);
	        panel.requestFocusInWindow();
	        validate();
//	         フレームを表示
	        this.setVisible(true);
	        
	        
	        panel.setLayout(null); 
		    // 重要！レイアウトマネージャを無効化
		    
		    this.add(panel);
		    
		 // スキップボタンの作成
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
		            changeView(new TitleView());
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
		"「なんと、私ごときに…。\nありがたき幸せ…！」\n"+
		"\n"+
		"らいおん社長\n"+
		"「そして、もうひとつ…。\n\n"+
		"この会社を、明日から君に任せたい。\n"+
		"老兵は去るのみ、\n未来を若いうさぎに託したいのだ。\n\n"+
		"なってくれるね？\n株式会社『ZOO』の新しい社長に…」\n\n"+

		"静かに頷き、頬を涙で濡らす「"+name+"」。\n\n"+
		"そのうさぎの赤い瞳は、\n『ZOO』の行く末を明るく照らしていた…。\n\n\n\n\n\n\n\n\n\n\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n";

	        TextAndImagePanel panel = new TextAndImagePanel("src/maingame/resources/boss1.jpg",text2);
	        add(panel);
	        panel.requestFocusInWindow();
	        validate();
	        // フレームを表示
	        this.setVisible(true);
	        
	        
	        panel.setLayout(null); 
		    // 重要！レイアウトマネージャを無効化
		    
		    this.add(panel);
		    
		 // スキップボタンの作成
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
		            changeView(new TitleView());
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