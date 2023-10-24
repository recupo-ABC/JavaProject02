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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class SimpleRPG {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple RPG");
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class GamePanel extends JPanel implements ActionListener {
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

    // 障害物の配列を追加
    private Obstacle[] obstacles;
    private BufferedImage battleModeBackground;

    public GamePanel() {
        this.setFocusable(true);
        
        this.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        	    if (inBattle && showChoices) {
        	        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
        	            currentChoice = 1 - currentChoice; // 選択肢を切り替える
        	        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        	            if (currentChoice == 0) {
        	                String technique = player.useTechnique();
        	                battleMessage = "必殺、" + technique + "！";
        	                showChoices = false;
        	            } else {
        	                inBattle = false;
        	                showChoices = false;
        	                showEnemy = false;
        	                battleMessage = "";
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
                        GamePanel.this.keyPressed(new KeyEvent(GamePanel.this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));

                    }
                }
            }
        });

    
         // 画像の読み込み
        try {
            battleModeBackground = ImageIO.read(getClass().getResource("battle_background.jpg"));
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

    private void initGame() {
        if (player != null) {
            player.resetHP(); 
        } else {
            player = new Player(375, 370, 10, obstacles); // obstacles を引数として追加
        }
        enemyX = new Enemy(0, 0, Color.RED, "モンスタークレーマーに遭遇！", obstacles); // x座標とy座標を0に設定
        enemyY = new Enemy(770, 0, Color.BLUE, "パワハラ上司に遭遇！", obstacles); // x座標を770に、y座標を0に設定
        currentEnemy = random.nextBoolean() ? enemyX : enemyY;  
        lastEnemyWasX = (currentEnemy == enemyX);
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

            if (battleMessage.equals("")) {
                battleMessage = currentEnemy.getEncounterMessage();
                battleMessageTimestamp = currentTime;
                showEnemy = true;
            } else if (currentTime - battleMessageTimestamp > 2500) {
                if (isSecondaryMessage(battleMessage)) {
                    String nextMessage = currentEnemy.getNextSecondaryMessage();
                    if (nextMessage != null) {
                        battleMessage = nextMessage;
                        battleMessageTimestamp = currentTime;
                    } else {
                        showChoices = true; // 選択肢を表示
                        battleMessage = ""; // 敵のメッセージを消去
                    }
                } else if (battleMessage.equals(currentEnemy.getEncounterMessage())) {
                    battleMessage = currentEnemy.getNextSecondaryMessage();
                    battleMessageTimestamp = currentTime;
                }
            } else if (battleMessage.startsWith("必殺、") && currentTime - battleMessageTimestamp > 2500) {
                // この部分のコードは変更せずそのまま続けます
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
        if (!battleMessage.equals("") && !showChoices) {
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

        g.drawString("イキリーマンHP初期値: 10", x + 10, y + 20);
        g.drawString("HPが20になるとゲームクリア", x + 10, y + 40);
        g.drawString("HPが0になるとゲーム終了", x + 10, y + 60);
        g.drawString("現在のHP: " + player.hp, x + 10, y + 80);
    }

    private void drawBattleMessage(Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        g.setFont(new Font("MS UI Gothic", Font.BOLD, 16));
        
        String[] lines = battleMessage.split("\n");
        int lineHeight = g.getFontMetrics().getHeight();
        for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], x + 10, y + (height / 4) + i * lineHeight);
        }
    }


    class Player {
        int x, y, hp;
        final int SIZE = 75;  // サイズを20x20に変更
        final int SPEED = 5;
        private int prevX, prevY;
        private Obstacle[] obstacles; 
        private BufferedImage playerImage;  // 画像を読み込むための変数を追加

        public Player(int x, int y, int hp, Obstacle[] obstacles) { 
            this.x = x;
            this.y = y;
            this.hp = hp;
            this.obstacles = obstacles; 

            // 画像の読み込み
            try {
                playerImage = ImageIO.read(getClass().getResource("主人公.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void keyPressed(KeyEvent e) {
        prevX = x;
        prevY = y;

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            x -= SPEED;
        } else if (key == KeyEvent.VK_RIGHT) {
            x += SPEED;
        } else if (key == KeyEvent.VK_UP) {
            y -= SPEED;
        } else if (key == KeyEvent.VK_DOWN) {
            y += SPEED;
        }
        move();  // この行を追加
    }

    public void move() { // シグネチャを変更
    	if (x < 0) x = 0;
    	if (x > 730) x = 730;  // 750から740に変更
    	if (y < 0) y = 0;
    	if (y > 380) y = 380;  // 550から540に変更

        for (Obstacle obstacle : obstacles) {
            if (this.intersects(obstacle)) {
                this.undoMove();
                break;
            }
        }
    }


    public String useTechnique() {
        Random random = new Random();
        if (random.nextBoolean()) {
            return "イキり舌打ち！";
        } else {
            return "おとなぺこぺこ（笑）";
        }
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, x, y, SIZE, SIZE, null);  // 画像を描画
    }

    public void undoMove() {
        x = prevX;
        y = prevY;
    }

    public boolean intersects(Obstacle obstacle) {
        Rectangle playerBounds = new Rectangle(x, y, SIZE, SIZE);
        return playerBounds.intersects(obstacle.getBounds());
    }
    
    public void resetHP() {
        this.hp = 10; // 初期HP値に設定
    }
    
}




    class Enemy {
        int x, y;
        Color color;
        final int SIZE = 75;  // サイズを50x50に変更
        final int SPEED = 15;
        private String encounterMessage;
        private Obstacle[] obstacles;
        private BufferedImage enemyImage;  // 敵の画像を読み込むための変数を追加

        private int prevX, prevY;
       
        private String[] secondaryMessages; // 3つのセカンダリメッセージを格納するための配列
        private int currentSecondaryMessageIndex = 0; // 現在表示しているセカンダリメッセージのインデックス
        

        public Enemy(int x, int y, Color color, String encounterMessage, Obstacle[] obstacles) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.encounterMessage = encounterMessage;
            this.obstacles = obstacles;

            // 画像の読み込み
            try {
                if (color == Color.RED) {
                    enemyImage = ImageIO.read(getClass().getResource("クレーマー.png"));
                } else if (color == Color.BLUE) {
                    enemyImage = ImageIO.read(getClass().getResource("パワハラ上司.png"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        
            if (color == Color.RED) {
                secondaryMessages = new String[]{
                    "モンスタークレーマー：\n「あなたの会社いったいどうなってるの？」",
                    "「私はあなたの会社のためを思って\n言ってるのよ！」",
                    "「今すぐひざまずいて\n私のけつをなめなさいよ！」"
                };
            } else if (color == Color.BLUE) {
                secondaryMessages = new String[]{
                    "パワハラ上司：\n「おいコラお前は何様のつもりだ！」",
                    "「俺がお前に怒鳴るのも殴るのも\n全てお前を思って言ってるんだぞ！」",
                    "「今すぐひざまずいて俺様のけつをなめろ！」"
                };
            }
        }

        public String getNextSecondaryMessage() {
            if (currentSecondaryMessageIndex < secondaryMessages.length) {
                return secondaryMessages[currentSecondaryMessageIndex++];
            } else {
                return null; // これにより、すべてのセカンダリメッセージが表示された後にnullを返す
            }
        }


    public String getEncounterMessage() {
        return encounterMessage;
    }

    public void move(Player player) {
        prevX = x;
        prevY = y;

        if (x < player.x) x += SPEED;
        else if (x > player.x) x -= SPEED;

        if (y < player.y) y += SPEED;
        else if (y > player.y) y -= SPEED;

        for (Obstacle obstacle : obstacles) {
            if (this.intersects(obstacle)) {
                this.undoMove();
                this.findClearPath(obstacle);
                break;
            }
        }
    }

    private void findClearPath(Obstacle obstacle) {
        int[] dx = {-SPEED, 0, SPEED, 0};
        int[] dy = {0, -SPEED, 0, SPEED};

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            Rectangle newPos = new Rectangle(newX, newY, SIZE, SIZE);
            if (!newPos.intersects(obstacle.getBounds())) {
                x = newX;
                y = newY;
                return;
            }
        }
    }


    public void draw(Graphics g) {
        g.drawImage(enemyImage, x, y, SIZE, SIZE, null);  // 画像を描画
    }

    public void undoMove() {
        x = prevX;
        y = prevY;
    }

    public boolean intersects(Obstacle obstacle) {
        Rectangle enemyBounds = new Rectangle(x, y, SIZE, SIZE);
        return enemyBounds.intersects(obstacle.getBounds());
    }
    
 // 既存のコードの中での位置： Enemy クラスの最後に追加
    public void changeDirection(Player player) {
        int deltaX = player.x - x;
        int deltaY = player.y - y;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            x += deltaX > 0 ? -SPEED : SPEED;
        } else {
            y += deltaY > 0 ? -SPEED : SPEED;
        }
    }

}

class Obstacle {
    int x, y, width, height;

    public Obstacle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(244,164,96));
        g.fillRect(x, y, width, height);
    }
}
}