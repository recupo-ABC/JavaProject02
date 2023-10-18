package game;


public class GameOver extends baseClass {
    public GameOver() {
        super("ゲームオーバー画面", "images/gameOver.jpg", "あーーー。\n"
                + "やっちゃったわぁ・・・・\n"
                + "明日からどうしよう・・・\n");
    }

    public static void main(String[] args) {
        new GameOver();
    }
}