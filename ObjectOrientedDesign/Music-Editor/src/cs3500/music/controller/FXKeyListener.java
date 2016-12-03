package cs3500.music.controller;

import javafx.scene.input.KeyEvent;

/**
 * General Key Listener for JavaFX KeyEvents
 */
public interface FXKeyListener {
  void keyTyped(KeyEvent e);
  void keyPressed(KeyEvent e);
  void keyReleased(KeyEvent e);
}
