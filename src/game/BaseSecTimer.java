
package game;

import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class BaseSecTimer extends JPanel {
 protected Timer timer;

 public void startTimer(int delay, ActionListener actionListener) {
     timer = new Timer(delay, actionListener);
     timer.start();
 }

 public void stopTimer() {
     if (timer != null && timer.isRunning()) {
         timer.stop();
     }
 }
}
