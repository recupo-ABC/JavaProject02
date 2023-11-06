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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class TitleView extends JPanel implements ActionListener{
	MyKeyListener myKeyListener;
	Image backgroundImage;
	static JTextField name;
	JLabel label;
	JLabel label1;
	static String text;
	static JButton startButton;
	Player player;
    public TitleView() {
    	
    	backgroundImage = Toolkit.getDefaultToolkit().createImage("src/maingame/resources/画像３.jpg");
    	startButton = new JButton("");
    	ImageIcon buttonIcon = new ImageIcon("src/maingame/resources/133.114.249.105 (1).gif");
    	JLabel title = new JLabel("イキリーマン伝説");
        JLabel title1= new JLabel("～シン・社会人へのキャリアップ～");
//        JLabel title2= new JLabel("プレイヤーネームを入力して下さい");

        label = new JLabel("");
        label1 = new JLabel("");
        name = new JTextField("", 20);
        name.setBounds(295,470,150,30);
    	setOpaque(false);
        setLayout(null);
        setFocusable(true);
        setVisible(true);
        setBounds(0,0,800,600);
        
        startButton.setIcon(buttonIcon);
        startButton.setBounds(445, 467, 63, 36);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.addActionListener(this);
        startButton.setActionCommand("start");
        add(startButton);
        add(name);
        title.setForeground(Color.BLACK);
        title1.setBounds(200,300,500,100);
        title1.setForeground(Color.BLACK);
        title.setBounds(260,250,400,100);
//        title2.setBounds(250,500,300,30);
//        title2.setForeground(Color.LIGHT_GRAY);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title1.setFont(new Font("SansSerif", Font.BOLD, 24));
//      title2.setFont(new Font("SansSerif", Font.BOLD, 18));
//        add(title);
//        add(title1);
//       add(title2);


        ClientMain.frame.getRootPane().setDefaultButton(startButton);
        myKeyListener = new MyKeyListener(this);
    }
	public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("start")) { 
        	GameFrame.playSoundEffect("src/maingame/resources/シャキーン1.wav");
        	label.setText(name.getText());
        	remove(startButton);
        	remove(name);
        	ClientMain.frame.PrologueView();
//        	ClientMain.frame.EndingView();
        	return;
        } 
	}
	
	public void paint(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, this); // 背景イメージを描画
		super.paint(g); // 子供コンポーネントの描画等、上位クラスで実現している表示内容の描画
		
		// テキストの描画
	    g.setFont(new Font("SansSerif", Font.BOLD, 18)); // フォントを設定
	    g.setColor(Color.WHITE); // テキストの色

	    String text = "プレイヤーネームを入力して下さい";
	    int x = 250; // X 座標
	    int y = 523; // Y 座標

	    // 外縁線の色と太さを設定
	    g.setColor(Color.BLACK);
	    g.drawString(text, x - 1, y - 1);
	    g.drawString(text, x + 1, y - 1);
	    g.drawString(text, x - 1, y + 1);
	    g.drawString(text, x + 1, y + 1);

	    // テキストを描画
	    g.setColor(Color.WHITE);
	    g.drawString(text, x, y);
	
	}
	
	private class MyKeyListener implements KeyListener{
		//貼り付け先を保持
		TitleView panel;
		
		//コンストラクタ
		MyKeyListener(TitleView p){
			super();
			panel = p;
			panel.addKeyListener(this);
		}
		
		public void keyTyped(KeyEvent e) {
			
		}
		
		public void keyReleased(KeyEvent e) {
			
		}
		
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {		
					ClientMain.frame.changeView(new SimpleRPG());
				}
		}
		}
	}
	
}
