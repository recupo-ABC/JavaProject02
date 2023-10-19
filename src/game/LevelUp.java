package game;


public class LevelUp extends BaseClass {

    public LevelUp() {
//      // 背景画像とtextの内容(画面タイトル,背景画像パス,text)
        super("レベルアップ画面", "images/syouri.jpg", "Lvが●●になりました");

        // CharaPanelを作成
        BaseCharaPanel charaPanel = new BaseCharaPanel("images/Lvup.png");

//      // サイズと配置を明示的に指定(横開始位置,縦開始位置,横サイズ,縦サイズ)
        charaPanel.setBounds(250, 50, 300, 300);

        // imagePanel, charaPanel, textPanelの順にフレームに追加
        add(charaPanel);
        add(imagePanel);
        add(textPanel);

        // フレームを再描画
        repaint();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LevelUp();
        });
    }
}
