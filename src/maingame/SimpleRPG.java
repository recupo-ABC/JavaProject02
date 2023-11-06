//1031_リファクタリング：済

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

    // タイマーは定期的なアクションをトリガーします。
    private Timer timer;
    
    // プレイヤーや敵、バトルメッセージなど、ゲーム内の主要なオブジェクトを管理します。
    private Player player;
    private Enemy currentEnemy;
    private Enemy enemyX;
    private Enemy enemyY;
    
    // バトルの進行状況やメッセージ表示などの制御フラグを管理します。
    private boolean inBattle = false;
    private boolean showEnemy = false;
    private boolean showChoices = false;
    private int currentChoice = 0;
    private long currentTime = System.currentTimeMillis();
    
    // 障害物の配列を管理し、バトルの背景画像を保持します。
    private Obstacle[] obstacles;
    private BufferedImage battleModeBackground;
    
    // バトルメッセージや選択肢表示などの情報を保持します。
    private String battleMessage = "カーソルキーで動きます";
    private long battleMessageTimestamp = 0;
    
    
    // コンストラクタは初期化を行います。
    public SimpleRPG() {
        this.setFocusable(true);
        
        // キーリスナーやマウスリスナーの追加
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPressed(e);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClicked(e);
            }
        });

     // 画像の読み込みや障害物の初期化
        loadImages();
        initializeObstacles();

        initGame();
    }

    // キーが押されたときの処理を制御します。
    private void handleKeyPressed(KeyEvent e) {
        if (inBattle && showChoices) {
            handleBattleChoicesKeyPressed(e);
        } else {
            player.keyPressed(e);
        }
    }

    
    // マウスがクリックされたときの処理を制御します。
    private void handleMouseClicked(MouseEvent e) {
        if (showChoices) {
            handleBattleChoicesMouseClicked(e);
        } else {
            player.mouseClicked(e);
        }
    }

    
    // バトル中の選択肢がキー入力されたときの処理を制御します。
    private void handleBattleChoicesKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentChoice = 1 - currentChoice;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            handleChoiceEnter();
        }
    }

    // バトル中の選択肢がマウスでクリックされたときの処理を制御します。
    private void handleBattleChoicesMouseClicked(MouseEvent e) {
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
            handleChoiceEnter();
        }
    }

    private void handleChoiceEnter() {
        if (currentChoice == 0) {
            handleChoiceZero();
        } else {
            handleChoiceOne();
        }
    }

    // 選択肢0が選択されたときの処理
    private void handleChoiceZero() {
        new String(TitleView.name.getText());
        String technique = player.useTechnique();
        boolean victory = technique.endsWith("！");
        if (!victory) {
            GameFrame.h += 1;
            battleMessage = technique + "\n\n" + "勝利！イキリーマンはHPが5追加された！";
        } else {
            GameFrame.h -= 1;
            battleMessage = technique + "\n\n" + "敗北... イキリーマンはHPを5失った...";
        }
        handleChoiceCompletion();
    }

    // 選択肢1が選択されたときの処理
    private void handleChoiceOne() {
        inBattle = false;
        showChoices = false;
        showEnemy = false;
        battleMessage = "";
        GameFrame.h -= 3;
        ClientMain.getFrame().changeView(new SimpleRPG(), currentChoice);
    }

    
    // 選択肢が完了したときの処理を制御します。
    private void handleChoiceCompletion() {
        battleMessageTimestamp = currentTime;
        showChoices = false;
        Timer timer = new Timer(2000, paramActionEvent -> {
            ClientMain.getFrame().changeView(new SimpleRPG(), 0);
        });
        timer.setRepeats(false);
        timer.start();
    }

    
    // 画像の読み込み
    private void loadImages() {
        try {
            battleModeBackground = ImageIO.read(getClass().getResource("resources/battle_background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
 // 障害物の初期化
    private void initializeObstacles() {
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
    }

    
    // ゲームの初期化
    private void initGame() {
        player = new Player(375, 370, 10, obstacles);
        enemyX = new Enemy(0, 0, Color.RED, "モンスタークレーマーに遭遇！", obstacles);
        enemyY = new Enemy(770, 0, Color.BLUE, "パワハラ上司に遭遇！", obstacles);
        currentEnemy = new Random().nextBoolean() ? enemyX : enemyY;
        timer = new Timer(100, this);
        timer.start();
        showEnemy = true;
    }

    // タイマーがアクションをトリガーしたときの処理を制御
    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.currentTimeMillis();
        if (inBattle) {
            int prevPlayerX = player.x;
            int prevPlayerY = player.y;

            player.x = currentEnemy.getX() + (currentEnemy.getSize() - player.SIZE) / 2;
            player.y = currentEnemy.getY() + currentEnemy.getSize();

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
                        showChoices = true;
                        battleMessage = "";
                    }
                } else if (battleMessage.equals(currentEnemy.getEncounterMessage())) {
                    battleMessage = currentEnemy.getNextSecondaryMessage();
                    battleMessageTimestamp = currentTime;
                }
            } else if (battleMessage.startsWith("必殺、") && currentTime - battleMessageTimestamp > 2500) {
                // 省略.
            }
        } else {
            currentEnemy.move(player);
            checkCollisions();
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
        Rectangle enemyBounds = new Rectangle(currentEnemy.getX(), currentEnemy.getY(), currentEnemy.getSize(), currentEnemy.getSize());
        if (playerBounds.intersects(enemyBounds)) {
            inBattle = true;
        }

        for (Obstacle obstacle : obstacles) {
            if (player.intersects(obstacle)) {
                player.undoMove();
            }
        }
    }

    private boolean isSecondaryMessage(String message) {
        for (String secondaryMsg : currentEnemy.getSecondaryMessages()) {
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

        if (!inBattle) {
            for (Obstacle obstacle : obstacles) {
                obstacle.draw(g);
            }
        }

        player.draw(g);
        if (showEnemy) {
            currentEnemy.draw(g);
        }

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

    
    
//   ************************** メッセージスペース  ************************** 
    private void drawChoices(Graphics g, int x, int y, int width, int height) {
        String[] choices = {"対応する", "逃げる"};

        g.setFont(new Font("SansSerifc", Font.PLAIN, 16));
        int lineHeight = g.getFontMetrics().getHeight();
        int choiceWidth = width / 2;
        for (int i = 0; i < choices.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.RED);
                g.drawString("▶", x + choiceWidth * 3 / 5 - 10, y + i * (height / 5) + (3 * lineHeight));
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(choices[i], x + choiceWidth * 3 / 5, y + i * (height / 5) + (3 * lineHeight));
        }
    }

    private void drawBattleMessageWindow(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);
    }


    private void drawBattleMessage(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);
        g.setFont(new Font("SansSerif", Font.PLAIN, 18));

        String[] lines = battleMessage.split("\n");
        int lineHeight = g.getFontMetrics().getHeight();
        for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], x + 10, y + (height / 4) + i * lineHeight);
        }
    }
    
    private void drawStatusWindow(Graphics g, int x, int y, int width, int height) {
    	g.setColor(Color.BLACK);
    	g.fillRect(x, y, width, height);
    	g.setColor(Color.WHITE);
    	g.drawRect(x, y, width, height);
    	String name = TitleView.name.getText();
    	g.drawString(name + "HP初期値: 10", x + 10, y + 20);
    	g.drawString("HPが20になるとゲームクリア", x + 10, y + 40);
    	g.drawString("HPが0になるとゲーム終了", x + 10, y + 60);
    	g.drawString("現在のHP: " + Player.getHp(), x + 10, y + 80);
    }
    
    
}
