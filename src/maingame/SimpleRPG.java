package maingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

//音楽再生用_以下5つ
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

    

class SimpleRPG extends JPanel implements ActionListener {
	private Clip backgroundMusicClip; // 追加: 背景音楽用のクリップ
	private Timer timer;
    private Player player;
    private Enemy currentEnemy;
    private Enemy enemyX;
    private Enemy enemyY;
    private boolean inBattle = false;
    private Random random = new Random();
    private String battleMessage = "";
    private long battleMessageTimestamp = 0;
    private boolean showEnemy = false;
    private long enemyRespawnTimestamp = 0;
    private boolean lastEnemyWasX = false;
    private boolean showChoices = false; // NEW: 選択肢を表示するかどうかのフラグ
    private int currentChoice = 0;       // NEW: 現在の選択肢 (0: 相手に対応する, 1: 逃げる)
    long currentTime = System.currentTimeMillis();
    // 障害物の配列を追加
    private Obstacle[] obstacles;
    private BufferedImage battleModeBackground;
    
    
    public SimpleRPG() {
        this.setFocusable(true);
        
 // キーボードリスナー
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (inBattle && showChoices) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    //  VK=Virtual Key　KeyEvent クラスにおける定数の一つ
                    	
                      currentChoice = 1 - currentChoice; // 選択肢を切り替える
                                          
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        executeAction();
                    }
                } else {
                    player.keyPressed(e);
                    // プレイヤーが戦闘中でなく、選択肢を表示していない状態でキーが押されたときにplayerクラスのkeyPressedメソッドを実行
                }
            }
        });
     
 // マウスリスナー   
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (showChoices) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    int width = getWidth();
                    int height = getHeight();
                    int choiceHeight = (int) (height * 0.2) / 2;
                    int startY = (int) (height * 0.8);
                    int messageWindowX = width / 2;
                    int halfWidth = messageWindowX / 2;

                    // 「対応する」の選択肢の領域をクリックした場合
                    if (mouseY > startY && mouseY < startY + choiceHeight && mouseX > messageWindowX && mouseX < messageWindowX + halfWidth) 
//                    	「mouseY > startY」 &&（かつ）「 mouseY < startY + choiceHeight」ということ                 	
                    {
                        currentChoice = 0;
                        executeAction();
                    }
                    // 「逃げる」の選択肢の領域をクリックした場合
                    else if (mouseY > startY && mouseY < startY + choiceHeight && mouseX > messageWindowX + halfWidth && mouseX < messageWindowX + 2 * halfWidth) {
                        currentChoice = 1;
                        executeAction();
                    }
                }
            }
        });

    
 // 画像の読み込み
        try {
            battleModeBackground = ImageIO.read(getClass().getResource("resources/battle_background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        // 音楽の読み込みは行うが、再生はしない
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource("resources/preparation.wav"));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioIn);
            // start() と loop() の呼び出しは削除
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        
        // 障害物を初期化
        obstacles = new Obstacle[]{
            new Obstacle(150, 75, 40, 90),
            new Obstacle(300, 75, 40, 90),
            new Obstacle(450, 75, 40, 90),
            new Obstacle(600, 75, 40, 90),
            
            new Obstacle(150, 250, 40, 90),
            new Obstacle(300, 250, 40, 90),
            new Obstacle(450, 250, 40, 90),
            new Obstacle(600, 250, 40, 90)
        };
        
        initGame();

       
    }

  
    
 // 対応の選択アクション
    private void executeAction() {
        if (inBattle && showChoices) {
            if (currentChoice == 0) {
                // 「対応する」が選択された時の処理
                String technique = player.useTechnique();
                boolean victory = technique.endsWith("！");
                if (!victory) {
                    GameFrame.h += 1;
                    battleMessage = technique + "\n\n" + "勝利！イキリーマンはHPが5追加された！";
                } else {
                    GameFrame.h -= 1;
                    battleMessage = technique + "\n\n" + "敗北... イキリーマンはHPを5失った...";
                }
                battleMessageTimestamp = currentTime;
                showChoices = false;
                Timer timer = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent paramActionEvent) {
                        // 戦闘終了時の処理
                        inBattle = false;
                        showChoices = false;
                        showEnemy = false;
                        battleMessage = "";
                        if (backgroundMusicClip.isRunning()) {
                            backgroundMusicClip.stop();
                        }
                        ClientMain.frame.changeView(new SimpleRPG());
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                // 「逃げる」が選択された時の処理
                inBattle = false;
                showChoices = false;
                showEnemy = false;
                battleMessage = "";
                if (backgroundMusicClip.isRunning()) {
                    backgroundMusicClip.stop();
                }
                ClientMain.frame.changeView(new SimpleRPG());
            }
        }
    }


//初期化    
	private void initGame() {
        if (player != null) {
            player.resetHP();
        } else {
            player = new Player(375, 370, 10, obstacles); // obstacles を引数として追加
        }
        enemyX = new Enemy(0, 0, Color.RED, "モンスタークレーマーに遭遇！", obstacles); // x座標とy座標を0に設定
        enemyY = new Enemy(770, 0, Color.BLUE, "パワハラ上司に遭遇！", obstacles); // x座標を770に、y座標を0に設定
        currentEnemy = random.nextBoolean() ? enemyX : enemyY;  
//        lastEnemyWasX = (currentEnemy == enemyX);
        timer = new Timer(100, this);
        timer.start();
        showEnemy = true;
        
        
 // ゲーム開始時に音楽を停止
        if (backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
        }
    
    }

	
//キャラのアクション
    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.currentTimeMillis();
        if (inBattle) {
            int prevPlayerX = player.x;
            int prevPlayerY = player.y;

            // Position the player directly below the enemy during a battle
            player.x = currentEnemy.x + (currentEnemy.SIZE - player.SIZE) / 2;
            player.y = currentEnemy.y + currentEnemy.SIZE;

            // Check for collisions with obstacles
            for (Obstacle obstacle : obstacles) {
                if (player.intersects(obstacle)) {
                    player.x = prevPlayerX;
                    player.y = prevPlayerY;
                    break;
                }
            }

            if (battleMessage.equals("") && !showChoices) {
                battleMessage = currentEnemy.getEncounterMessage();
                battleMessageTimestamp = currentTime;
                showEnemy = true;
            } else if (currentTime - battleMessageTimestamp > 500) {
                if (isSecondaryMessage(battleMessage)) {
                    String nextMessage = currentEnemy.getNextSecondaryMessage();
                    if (nextMessage != null) {
                        battleMessage = nextMessage;
                        battleMessageTimestamp = currentTime;
                    } else {
                        showChoices = true; // 選択肢を表示
                        battleMessage = "";
                    }
                } else if (battleMessage.equals(currentEnemy.getEncounterMessage())) {
                    battleMessage = currentEnemy.getNextSecondaryMessage();
                    battleMessageTimestamp = currentTime;
                }
            } else if (battleMessage.startsWith("必殺、") && currentTime - battleMessageTimestamp > 2500) {
            	//この部分のコードは変更せずそのまま続けます
            }

        } else {
            currentEnemy.move(player);  // 敵がプレイヤーに近づくように移動
            checkCollisions();  // 衝突判定

            if (inBattle) {
                battleMessage = "";
                showEnemy = true;
                battleMessageTimestamp = currentTime;
            }
        }

        repaint();
    }



 // 障害物との衝突判定
    private void checkCollisions() {
        Rectangle playerBounds = new Rectangle(player.x, player.y, player.SIZE, player.SIZE);
        Rectangle enemyBounds = new Rectangle(currentEnemy.x, currentEnemy.y, currentEnemy.SIZE, currentEnemy.SIZE);
        
        if (playerBounds.intersects(enemyBounds) && !inBattle) {
            inBattle = true;
            
         


        for (Obstacle obstacle : obstacles) {
            if (player.intersects(obstacle)) {
                player.undoMove();  // プレイヤーと障害物が衝突した場合、プレイヤーの移動を元に戻す
            }
        
    }
        }}


    
    private boolean isSecondaryMessage(String message) {
        for (String secondaryMsg : currentEnemy.secondaryMessages) {
            if (message.equals(secondaryMsg)) {
                return true;
            }
        }
        return false;
    }
    

//ゲーム本体の描画&音楽再生ロジック追加
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 戦闘中であれば、バトル背景画像を描画
        if (inBattle) {
            int gameAreaHeight = (int) (this.getHeight() * 0.8);
            g.drawImage(battleModeBackground, 0, 0, this.getWidth(), gameAreaHeight, null);

            // 戦闘中のみ音楽を再生
            if (!backgroundMusicClip.isRunning()) {
                backgroundMusicClip.start();
                backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } else {
            // 戦闘中でない場合、障害物を描画
            for (Obstacle obstacle : obstacles) {
                obstacle.draw(g);
            }

            // 戦闘が終了したら音楽を停止
            if (backgroundMusicClip.isRunning()) {
                backgroundMusicClip.stop();
            }
        }

        // プレイヤーと敵の描画
        player.draw(g);
        if (showEnemy) {
            currentEnemy.draw(g);
        }

        // メッセージエリアの描画
        int gameAreaHeight = (int) (this.getHeight() * 0.8);
        drawStatusWindow(g, 0, gameAreaHeight, this.getWidth() / 2, this.getHeight() - gameAreaHeight);
        drawBattleMessageWindow(g, this.getWidth() / 2, gameAreaHeight, this.getWidth() / 2, this.getHeight() - gameAreaHeight);

        // 選択肢の表示
        if (showChoices) {
            drawChoices(g, this.getWidth() / 2, (int) (this.getHeight() * 0.8), this.getWidth() / 2, this.getHeight() - (int) (this.getHeight() * 0.8));
        } else {
            drawBattleMessage(g, this.getWidth() / 2, gameAreaHeight, this.getWidth() / 2, this.getHeight() - gameAreaHeight, battleMessage);
        }
    }

   
    
    
    // その他の描画に関連するメソッド（drawStatusWindow, drawBattleMessageWindow, drawChoices, drawBattleMessage）



    // NEW: 選択肢の描画メソッド
    private void drawChoices(Graphics g, int x, int y, int width, int height) {
        String[] choices = {"対応する", "逃げる"};

        g.setFont(new Font("MS UI Gothic", Font.BOLD, 16));
        int lineHeight = g.getFontMetrics().getHeight();
        int choiceWidth = width / 2;
        int choiceHeight = height / 2;

        // "SELECT!"の描画
        g.drawString("SELECT!", x + width / 2 - g.getFontMetrics().stringWidth("SELECT!") / 2, y + lineHeight);

        for (int i = 0; i < choices.length; i++) {
            // 四角の線を描画
            g.drawRect(x + i * choiceWidth, y + lineHeight, choiceWidth, choiceHeight);
            if (i == currentChoice) {
                g.setColor(Color.RED);
                g.drawString("→", x + i * choiceWidth, y + (height / 4) + (2 * lineHeight) - 5);
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawString(choices[i], x + i * choiceWidth + 10, y + (height / 4) + (2 * lineHeight));
        }
    }


    private void drawBattleMessageWindow(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }

    private void drawStatusWindow(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        String name = TitleView.name.getText();
        g.drawString(name +"HP初期値: 10", x + 10, y + 20);
        g.drawString("HPが20になるとミッションクリア", x + 10, y + 40);
        g.drawString("HPが0になるとゲーム終了", x + 10, y + 60);
        g.drawString("現在のHP: " + player.hp, x + 10, y + 80);
    }

    private void drawBattleMessage(Graphics g, int x, int y, int width, int height, String message) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        g.setFont(new Font("MS UI Gothic", Font.BOLD, 16));
        
        String[] lines = message.split("\n");
        int lineHeight = g.getFontMetrics().getHeight();
        for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], x + 10, y + (height / 4) + i * lineHeight);
        }
    }

}
