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

    

class SimpleRPG extends JPanel implements ActionListener {
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
    private int currentChoice = 0;  
    boolean monster = false;// NEW: 現在の選択肢 (0: 相手に対応する, 1: 逃げる)
    long currentTime = System.currentTimeMillis();
    // 障害物の配列を追加
    private Obstacle[] obstacles;
    private BufferedImage battleModeBackground;
    String name = new String(TitleView.name.getText());
    public SimpleRPG() {
        this.setFocusable(true);
        
        this.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		
        	    if (inBattle && showChoices && monster) {
        	        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
        	            currentChoice = 1 - currentChoice; // 選択肢を切り替える
        	        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        	            if (currentChoice == 0) {
        	            	String name = new String(TitleView.name.getText());
        	                String technique = player.useTechnique();
        	                boolean victory = technique.endsWith("！");
        	                if (!victory) {
        	                    GameFrame.h += 1;
        	                    battleMessage = technique + "\n\n" +"勝利！イキリーマンはHPが5追加された！";
        	                } else {
        	                    GameFrame.h -= 1;
        	                    battleMessage = technique + "\n\n" + "敗北... イキリーマンはHPを5失った...";
        	                }
        	                battleMessageTimestamp = currentTime;
        	                showChoices = false;
        	                Timer timer = new Timer(2000, new ActionListener() {
        	                	@Override
        	                	public void actionPerformed(ActionEvent paramActionEvent) {
        	                	// ここに1秒後に実行する処理
        	                		ClientMain.frame.changeView(new SimpleRPG());
        	                	}
        	                	});
        	                	timer.setRepeats(false); // 1回だけ実行する場合
        	                	timer.start();
        	                
        	            } else {
        	            	battleMessage = name + "は逃げ出した";
        	            	GameFrame.n++;
        	                battleMessageTimestamp = currentTime;
        	                showChoices = false;
        	                Timer timer = new Timer(2000, new ActionListener() {
        	                	@Override
        	                	public void actionPerformed(ActionEvent paramActionEvent) {
        	                	// ここに1秒後に実行する処理
        	                		ClientMain.frame.changeView(new SimpleRPG());
        	                	}
        	                	});
        	                	timer.setRepeats(false); // 1回だけ実行する場合
        	                	timer.start();
        	            }
        	        }  
        	    }if (inBattle && showChoices && !monster) {
        	        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
        	            currentChoice = 1 - currentChoice; // 選択肢を切り替える
        	        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        	            if (currentChoice == 0) {
        	            	String name = new String(TitleView.name.getText());
        	                String technique = player.useTechnique1();
        	                boolean victory = technique.endsWith("！");
        	                if (!victory) {
        	                    GameFrame.h += 1;
        	                    battleMessage = technique + "\n\n" +"勝利！イキリーマンはHPが5追加された！";
        	                } else {
        	                    GameFrame.h -= 1;
        	                    battleMessage = technique + "\n\n" + "敗北... イキリーマンはHPを5失った...";
        	                }
        	                battleMessageTimestamp = currentTime;
        	                showChoices = false;
        	                Timer timer = new Timer(2000, new ActionListener() {
        	                	@Override
        	                	public void actionPerformed(ActionEvent paramActionEvent) {
        	                	// ここに1秒後に実行する処理
        	                		ClientMain.frame.changeView(new SimpleRPG());
        	                	}
        	                	});
        	                	timer.setRepeats(false); // 1回だけ実行する場合
        	                	timer.start();
        	                
        	            } else {
        	            	battleMessage = name + "は逃げ出した";
        	            	GameFrame.n++;
        	            	battleMessageTimestamp = currentTime;
        	                showChoices = false;
        	                Timer timer = new Timer(2000, new ActionListener() {
        	                	@Override
        	                	public void actionPerformed(ActionEvent paramActionEvent) {
        	                	// ここに1秒後に実行する処理
        	                		ClientMain.frame.changeView(new SimpleRPG());
        	                	}
        	                	});
        	                	timer.setRepeats(false); // 1回だけ実行する場合
        	                	timer.start();
        	            }
        	        }  
        	    } else {
        	        player.keyPressed(e);
        	    }
        	}

        });
        
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (showChoices) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    int choiceWidth = getWidth() / 2;
                    int choiceHeight = (int) (getHeight() * 0.2) / 2;
                    int startY = (int) (getHeight() * 0.8);
                    if (mouseY > startY && mouseY < startY + choiceHeight) {
                        if (mouseX > getWidth() / 2 && mouseX < getWidth() / 2 + choiceWidth) {
                            currentChoice = 0;
                        } else if (mouseX > getWidth() / 2 + choiceWidth && mouseX < getWidth()) {
                            currentChoice = 1;
                        }
                        SimpleRPG.this.keyPressed(new KeyEvent(SimpleRPG.this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));

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
        
        // 障害物を初期化
        obstacles = new Obstacle[]{
            new Obstacle(150, 60, 40, 90),
            new Obstacle(300, 60, 40, 90),
            new Obstacle(450, 60, 40, 90),
            new Obstacle(600, 60, 40, 90),
            
            new Obstacle(150, 250, 40, 90),
            new Obstacle(300, 250, 40, 90),
            new Obstacle(450, 250, 40, 90),
            new Obstacle(600, 250, 40, 90)
        };
        
        initGame();

       
    }

    protected void keyPressed(KeyEvent keyEvent) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

    
	private void initGame() {
        if (player != null) {
            player.resetHP();
        } else {
            player = new Player(375, 370, 10, obstacles); // obstacles を引数として追加
        }
        enemyX = new Enemy(0, 0, Color.RED, "モンスタークレーマーに遭遇！", obstacles, 0); // x座標とy座標を0に設定
        enemyY = new Enemy(770, 0, Color.BLUE, "パワハラ上司に遭遇！", obstacles, 1); // x座標を770に、y座標を0に設定
        if(GameFrame.Z % 2 == 0) {
        	currentEnemy = enemyX;
        }else if(GameFrame.Z % 2 == 1) {
        	currentEnemy = enemyY;
        }
         
//        lastEnemyWasX = (currentEnemy == enemyX);
        timer = new Timer(100, this);
        timer.start();
        showEnemy = true;
    }

	

    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.currentTimeMillis();
        if (inBattle) {
            int prevPlayerX = player.x;
            int prevPlayerY = player.y;

            // Position the player directly below the enemy during a battle
//            player.x = currentEnemy.x + (currentEnemy.SIZE - player.SIZE) / 2;
//            player.y = currentEnemy.y + currentEnemy.SIZE;

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
            } else if (currentTime - battleMessageTimestamp > 1500) {
                if (isSecondaryMessage(battleMessage)) {
                    String nextMessage = currentEnemy.getNextSecondaryMessage();
                    if (nextMessage != null) {
                        battleMessage = nextMessage;
                        battleMessageTimestamp = currentTime;
                    } else {
                        showChoices = true; // 選択肢を表示
                        monster = battleMessage.endsWith("よ！」");
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



private void checkCollisions() {
        Rectangle playerBounds = new Rectangle(player.x, player.y, player.SIZE, player.SIZE);
        Rectangle enemyBounds = new Rectangle(currentEnemy.x, currentEnemy.y, currentEnemy.SIZE, currentEnemy.SIZE);
        if (playerBounds.intersects(enemyBounds)) {
            inBattle = true;
        }

        // 障害物との衝突判定を追加
     // 障害物との衝突判定を追加
        for (Obstacle obstacle : obstacles) {
            if (player.intersects(obstacle)) {
                player.undoMove();  // プレイヤーの移動を元に戻す
            }
        }
    }
    
    private boolean isSecondaryMessage(String message) {
        for (String secondaryMsg : currentEnemy.secondaryMessages) {
            if (message.equals(secondaryMsg)) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inBattle) {
            int gameAreaHeight = (int) (this.getHeight() * 0.8);
            g.drawImage(battleModeBackground, 0, 0, this.getWidth(), gameAreaHeight, null);
        }

        // 戦闘中でない場合のみ、障害物を描画
        if (!inBattle) {
            for (Obstacle obstacle : obstacles) {
                obstacle.draw(g);
            }
        }

        player.draw(g);
        if (showEnemy) {
            currentEnemy.draw(g);
        }

        // メッセージエリアの下部1/5部分の描画
        int gameAreaHeight = (int) (this.getHeight() * 0.8);
        drawStatusWindow(g, 0, gameAreaHeight, this.getWidth() / 2, this.getHeight() - gameAreaHeight);
        drawBattleMessageWindow(g, this.getWidth() / 2, gameAreaHeight, this.getWidth() / 2, this.getHeight() - gameAreaHeight);
        if (!showChoices) {
            drawBattleMessage(g, this.getWidth() / 2, gameAreaHeight, this.getWidth() / 2, this.getHeight() - gameAreaHeight);
        }

        if (showChoices) {
            drawChoices(g, this.getWidth() / 2, (int) (this.getHeight() * 0.8), this.getWidth() / 2, this.getHeight() - (int) (this.getHeight() * 0.8));
        }
    }


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
            if (i == currentChoice) {
                g.setColor(Color.RED);
                g.drawString("▶", x + width/2 - g.getFontMetrics().stringWidth("対応する"), y + i * (height / 5) + (3 * lineHeight));
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(choices[i], x + width/2 - g.getFontMetrics().stringWidth("逃げる"), y + i * (height / 5) + (3 * lineHeight));
        }
    }


    private void drawBattleMessageWindow(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);
    }

    private void drawStatusWindow(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);
        String name = TitleView.name.getText();
        g.drawString(name +"HP初期値: 10", x + 10, y + 20);
        g.drawString("HPが20になるとゲームクリア", x + 10, y + 40);
        g.drawString("HPが0になるとゲーム終了", x + 10, y + 60);
        g.drawString("現在のHP: " + player.hp, x + 10, y + 80);
    }

    private void drawBattleMessage(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);
        g.setFont(new Font("MS UI Gothic", Font.BOLD, 16));
        
        String[] lines = battleMessage.split("\n");
        int lineHeight = g.getFontMetrics().getHeight();
        for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], x + 10, y + (height / 4) + i * lineHeight);
        }
    }
}
