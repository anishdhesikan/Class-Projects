package cs3500.music.controller;


import javafx.scene.input.MouseEvent;

/**
 * General Mouse Listener for JavaFX MouseEvents
 */
public interface FXMouseListener {
  void mouseClicked(MouseEvent e);
  void mousePressed(MouseEvent e);
  void mouseReleased(MouseEvent e);
  void mouseEntered(MouseEvent e);
  void mouseExited(MouseEvent e);
}
