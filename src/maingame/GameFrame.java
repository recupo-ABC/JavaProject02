package maingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;


public class GameFrame extends JFrame {
	static int Z = 1;
	private int hp = 10;
	boolean end = false;
	private String result;
	private static Clip clip;
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
	
	public String getText() {
		return result;
	}
	
	public void setText(String result) {
		this.result = result;
	}
	public void playSoundEffect(URL url) {
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(url)){
			
			//ファイルの形式取得
			AudioFormat af = ais.getFormat();
			
			//単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
			DataLine.Info dataLine = new DataLine.Info(Clip.class,af);
			
			//指定された Line.Info オブジェクトの記述に一致するラインを取得
			clip = (Clip)AudioSystem.getLine(dataLine);
			
			//再生準備完了
			clip.open(ais);
			clip.start();
			return;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return;
	}

	
	public void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
	}
public void clear() {
	ClientMain.frame.stopMusic();
	getContentPane().removeAll();
	URL url = getClass().getResource("resources/images/tobira.gif");
	String text ="ミッションクリア！！\nついに、社長室への扉が開いた・・・\n\n\n\n\n\n";
	URL url2 = getClass().getResource("resources/music/教会の祈り.wav");
	ClientMain.frame.playSoundEffect(url2);
     // カスタムパネルを作成してフレームに追加
        TextAndImagePanel3 panel = new TextAndImagePanel3(url, TitleView.text);	    
	    this.add(panel);
	    
	    // フレームを表示
	    this.setVisible(true);

        // テキストを1文字ずつ0.1秒ごとに表示するためのタイマーをセットアップ
        Timer timer = new Timer(150, new ActionListener() {
            private int charIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (charIndex < text.length()) {
                	panel.appendChar(text.charAt(charIndex++));
                } else {
                    ((Timer) e.getSource()).stop();
                    	EndingView();
                    }
                }            
        });
        timer.start();
    }
    	 
        
public void run() {
		ClientMain.frame.stopMusic();
		String name = TitleView.name.getText();
		getContentPane().removeAll();
		URL url = getClass().getResource("resources/images/runaway2.png");
		URL url3 = getClass().getResource("resources/music/男衆「イヤッホー！」.wav");
		ClientMain.frame.playSoundEffect(url3);
		String text =name + "は逃げ出した！\nしかしHPが3減った。\n\n\n\n\n\n";
	     // カスタムパネルを作成してフレームに追加
		
	        TextAndImagePanel2 panel = new TextAndImagePanel2(url, TitleView.text);	    
		    this.add(panel);
		    
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
	                    if(ClientMain.frame.getHP() <= 0) {
	                    	BadEndingView();
	                    }else {
	                    	changeView(new SimpleRPG());
	                    }
	                }
	            }
	        });
	        timer.start();
	    }

	
public void pekopeko() {
		ClientMain.frame.stopMusic();
		String name = TitleView.name.getText();
		getContentPane().removeAll();
		URL url = getClass().getResource("resources/images/pekopeko.png");
		String text =name + "は感情をグッと抑え、冷静に対応することが出来た。\nHPが5増えた。\n\n\n\n\n\n";
		URL url2 = getClass().getResource("resources/music/LvUp.wav");
    	ClientMain.frame.playSoundEffect(url2);
	     // カスタムパネルを作成してフレームに追加
	        TextAndImagePanel3 panel = new TextAndImagePanel3(url, TitleView.text);	    
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
	                    if(ClientMain.frame.getHP() >= 20) {
	                    	clear();
	                    }else {
	                    	changeView(new SimpleRPG());
	                    }
	                }
	            }
	        });
	        timer.start();
	    }

	
	public void kikoenai() {
		ClientMain.frame.stopMusic();
		String name = TitleView.name.getText();
		getContentPane().removeAll();
		URL url = getClass().getResource("resources/images/kikoena~i2.png");
		String text =name + "は話にならないので耳を塞いだ。\n後で怒られてしまいHPが5減った。\n\n\n\n\n\n\n";
		URL url2 = getClass().getResource("resources/music/kyouteki.wav");
		ClientMain.frame.playSoundEffect(url2);
	     // カスタムパネルを作成してフレームに追加
	        TextAndImagePanel2 panel = new TextAndImagePanel2(url, TitleView.text);	    
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
	                    if(ClientMain.frame.getHP() <= 0) {
	                    	BadEndingView();
	                    }else {
	                    	changeView(new SimpleRPG());
	                    }
	                }
	            }
	        });
	        timer.start();
	    }

	public void tackle() {
	ClientMain.frame.stopMusic();
	String name = TitleView.name.getText();
	getContentPane().removeAll();
	URL url = getClass().getResource("resources/images/tackle.png");
	String text =name + "は我慢出来ずに飛び掛かった。\n当たり前だが、怒られてHPが5減った。\n\n\n\n\n\n\n";
	URL url2 = getClass().getResource("resources/music/「ぐああーーっ！」.wav");
	ClientMain.frame.playSoundEffect(url2);
     // カスタムパネルを作成してフレームに追加
        TextAndImagePanel2 panel = new TextAndImagePanel2(url, TitleView.text);	    
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
                    if(ClientMain.frame.getHP() <= 0) {                 	
                    	BadEndingView();
                    }else {
                    	changeView(new SimpleRPG());
                    }
                }
            }
        });
        timer.start();
    }
	public void nakayubi() {
	ClientMain.frame.stopMusic();
	String name = TitleView.name.getText();
	getContentPane().removeAll();
	URL url = getClass().getResource("resources/images/nakayubi2.png");
	String text =name + "は我慢出来ずに中指を立てて挑発した。\n後で怒られてしまいHPが5減った。\n\n\n\n\n\n\n";
	URL url2 = getClass().getResource("resources/music/「お相手しましょう」 (online-audio-converter.com).wav");
	ClientMain.frame.playSoundEffect(url2);
     // カスタムパネルを作成してフレームに追加
        TextAndImagePanel2 panel = new TextAndImagePanel2(url, TitleView.text);	    
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
                    if(ClientMain.frame.getHP() <= 0) {
                    	BadEndingView();
                    }else {
                    	changeView(new SimpleRPG());
                    }
                }
            }
        });
        timer.start();
    }

	public void PrologueView() {
		String name = new String(TitleView.name.getText());
		URL url = getClass().getResource("resources/images/prologue1.png");
		URL url1 = getClass().getResource("resources/images/skip-button.png");
		String text ="“イキり”…。\nそれは、隠し持つ\nガラスのハートの表れ…。\n\n"
	            + 
	            "ウサギのような\n小心者の新卒サラリーマン\n「"+ name+ "」は、\n"
	            + 
	            "今日もイキりまくったファッションで\n"
	            + "IT企業「ビッグジャーニー」に二日酔い＆大遅刻で現れた。\n\n"
	            +
	            "そんな「" + name + " 」に突然、社長室へ来るようにとの指示が…。\n\n"
	            + 
	            "“そんな…まさか、遅刻ぐらいで社長室へ…（涙）”\n"
	            + "ガラスのハートをびくびく震わせながらも、\n"
	            + "イキった顔で社長室へと向かう「"+ name + "」。\n\n"
	            + "その背中に、課長が声をかける。\n";
	     // カスタムパネルを作成してフレームに追加
	        TextAndImagePanel panel = new TextAndImagePanel(url, TitleView.text);
	        this.add(panel);
	     // スキップボタンの作成
		    JButton skipButton = new JButton(new ImageIcon(url1));

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
		        	URL url = getClass().getResource("resources/music/skip.wav");
		        	ClientMain.frame.playSoundEffect(url);
		            PrologueView1();
		        }
		    });
		    panel.add(skipButton);
	        // フレームを表示
	        this.setVisible(true);


	        // テキストを1文字ずつ0.1秒ごとに表示するためのタイマーをセットアップ
	        Timer timer = new Timer(35, new ActionListener() {
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
		URL url = getClass().getResource("resources/images/prologue2.png");
		URL url1 = getClass().getResource("resources/images/start-button.png");
		String text ="課長\n「社長室の前のミーティングルームで、\n"
	            + "　お前はパワハラ上司や\n"
				+"　クレーマークライアントに出会うだろう。\n\n"
	            +
	             "　そこで大人な対応を続けることができれば、\n"
	            + "　イキりから真の社会人になれたと認められる。\n\n"
	            +
	             "　そして、そのミッションをクリアできれば \n"	            
	            + "　向かいの壁のどこかにある、\n"
	            +"　社長室への扉が開くとのことだ…。」\n\n"	           
                +
	            "小物なハートを震わせながら、\n"
	            +"平静を装いミーティングルームへ向かう「"+name+"」。\n\n"
	            +
	            "果たして「"+name+"」はシン・社会人となり、\n"
	            +"社長に対面できるのだろうか…。\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
	     // カスタムパネルを作成してフレームに追加
	        TextAndImagePanel panel = new TextAndImagePanel(url, TitleView.text);
	        panel.setLayout(null); 
		    // 重要！レイアウトマネージャを無効化
		    
		    this.add(panel);
		    
		 // スキップボタンの作成
		    JButton skipButton = new JButton(new ImageIcon(url1));

		    int buttonWidth = 300;
		    int buttonHeight = 80;

		    skipButton.setBounds(480,450,buttonWidth,buttonHeight);
		    skipButton.setOpaque(false);
		    skipButton.setContentAreaFilled(false);
		    skipButton.setBorderPainted(false);

		   

		    skipButton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	URL url = getClass().getResource("resources/music/skip.wav");
		        	ClientMain.frame.playSoundEffect(url);
		            changeView(new SimpleRPG());
		        }
		    });
		    panel.add(skipButton);
		    
		    // フレームを表示
		    this.setVisible(true);

	        // テキストを1文字ずつ0.1秒ごとに表示するためのタイマーをセットアップ
	        Timer timer = new Timer(35, new ActionListener() {
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
	
public void BadEndingView() {
	ClientMain.frame.setText("lose");
	getContentPane().removeAll();
	URL url = getClass().getResource("resources/images/failure_epilogue.png");
	URL url1 = getClass().getResource("resources/images/RePLAY-button .png");
	URL url2 = getClass().getResource("resources/images/GAMEOVER.png");
	URL url3 = getClass().getResource("resources/music/GameOver.wav");
	ClientMain.frame.playSoundEffect(url3);
	String text1 = 
	"\n\n\n\n\n\n      「あ～あ・・・」";
        TextAndImagePanel4 panel = new TextAndImagePanel4(url,text1);
        add(panel);
        panel.requestFocusInWindow();
        validate();
     // スキップボタンの作成
        panel.setLayout(null); 
	    // 重要！レイアウトマネージャを無効化
	    JButton skipButton = new JButton(new ImageIcon(url1));
	    JLabel gameover = new JLabel(new ImageIcon(url2));
	    int buttonWidth = 200;
	    int buttonHeight = 80;
	    
	    skipButton.setBounds(500,450,buttonWidth,buttonHeight);
	    skipButton.setOpaque(false);
	    skipButton.setContentAreaFilled(false);
	    skipButton.setBorderPainted(false);
	    gameover.setBounds(30,50,400,89);
	    
	   

	    skipButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            ClientMain.frame.changeView(new TitleView());
	        }
	    });
        // フレームを表示
        this.setVisible(true);
        panel.add(skipButton);
        panel.add(gameover);
        // テキストを1文字ずつ0.1秒ごとに表示するためのタイマーをセットアップ
        Timer timer = new Timer(30, new ActionListener() {
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

	public void EndingView() {
    	ClientMain.frame.setText("win");
		getContentPane().removeAll();
		String name = new String(TitleView.name.getText());
		URL url = getClass().getResource("resources/images/success_epilogue.png");
		URL url1 = getClass().getResource("resources/images/skip-button.png");
		URL url3 = getClass().getResource("resources/music/sevillanotoride.wav");
		ClientMain.frame.playSoundEffect(url3);
		String text1 = 
		"社長\n" +
		"「待っていたよ…。『"+name+"』くん…」\n\n"+
		name + "\n"+	
		"「社長ッッ…！」\n\n"+
		"社長\n"+
		"「『男子たるもの、\n三日会わざれば刮目して見よ』と言うが…。\n"+
		"今日一日で、ずいぶん成長したようだね…」\n\n"+
		name + "\n"+
		"「めっそうもございませんッッ…！」\n\n"+
		"社長\n"+
		"「ところで、『福岡県ダイバーシティ計画』\nのことは知っているね？\n";		

	        TextAndImagePanel panel = new TextAndImagePanel(url,text1);
	        add(panel);
	        panel.requestFocusInWindow();
	        validate();
	     // スキップボタンの作成
	        panel.setLayout(null); 
		    // 重要！レイアウトマネージャを無効化
		    JButton skipButton = new JButton(new ImageIcon(url1));

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
	        Timer timer = new Timer(30, new ActionListener() {
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
		URL url1 = getClass().getResource("resources/images/RePLAY-button .png");
		URL url = getClass().getResource("resources/images/success_epilogue.png");
		String text2 =
		"今後、我が社も大きなプロジェクトを\n担当する予定だ。\n"+
		"実は君に、そのリーダーを任せたい」\n\n"+
		name + "\n"+
		"「ありがたき幸せッッ…！」\n"+
		"\n"+
		"社長\n"+
		"「そして、もうひとつ…。\n"+
		"この会社を、明日から君に任せたい。\n"+
		"未来を若者に託したいのだ。\n"+
		"なってくれるね？\n株式会社『ビッグジャーニー』の新しい社長に…」\n\n"+

		"静かに頷き、頬を涙で濡らす「"+name+"」。\n"+
		"その赤い瞳は、\n『ビッグジャーニー』の行く末を\n明るく照らしていた…。\n\n\n\n\n\n\n\n\n\n\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n";

	        TextAndImagePanel panel = new TextAndImagePanel(url,text2);
	        add(panel);
	        panel.requestFocusInWindow();
	        validate();
	     // スキップボタンの作成
		    JButton skipButton = new JButton(new ImageIcon(url1));

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
		            ClientMain.frame.stopMusic();
		        }
		    });
		    panel.add(skipButton);
	        // フレームを表示
	        this.setVisible(true);
	        ClientMain.frame.setHP(10);
	        // テキストを1文字ずつ0.1秒ごとに表示するためのタイマーをセットアップ
	        Timer timer = new Timer(30, new ActionListener() {
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