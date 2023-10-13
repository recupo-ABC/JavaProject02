import javax.swing.JFrame;
import javax.swing.JLabel;

public class sample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Game");
        JLabel label = new JLabel("Hello, Swing!");

        frame.getContentPane().add(label);

        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
