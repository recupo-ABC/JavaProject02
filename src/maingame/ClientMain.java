// 1031_リファクタリング：済

package maingame;

public class ClientMain {
    private static GameFrame frame;

    public static void main(String[] args) {
        // ゲームフレームの初期化
        initializeGameFrame();

        // ゲームの開始
        startGame();
    }

    // ゲームフレームを初期化するメソッド
    private static void initializeGameFrame() {
        setFrame(new GameFrame());
    }

    // ゲームの開始処理を行うメソッド
    private static void startGame() {
        // ゲームフレームに新しいビューを設定し、指定されたインデックスで表示する
        getFrame().changeView(new SimpleRPG(), 0);
    }


    // 現在のゲームフレームを取得する
    public static GameFrame getFrame() {
        return frame;
    }


    // ゲームフレームを設定する
    public static void setFrame(GameFrame newFrame) {
        frame = newFrame;
    }
}
