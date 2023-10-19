package game;

public class Ending extends BaseClass {
    public Ending() {
        super("エンディング画面", "images/boss.jpg", "｛ playerName｝くん、とても成長したな。\n"
                + "福岡県ダイバーシティ計画については知っているね？\n"
                + "今後、我が社にも大きなプロジェクトが立ち上がる\n"
                + "｛ playerName｝くん。\n"
                + "君にそのプロジェクトリーダーを任せたい\n"
                + "これからも君の力を信じてるよ\n");
    }

    public static void main(String[] args) {
        new Ending();
    }
}