package maingame;

public class ClientMain {
    public static GameFrame frame;
    public static void main(String[] args) {
        frame = new GameFrame();
        frame.changeView(new TitleView());
    }
}