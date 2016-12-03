package cs3500.music.view.gui;

import java.awt.event.KeyListener;

import cs3500.music.controller.FXKeyListener;
import cs3500.music.controller.FXMouseListener;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.Playable;
import cs3500.music.view.View;
import javafx.scene.Node;

/**
 * To represent a view with a JavaFX GUI interface
 */
public interface GuiView extends View {
  /**
   * Sets the beat marker to a specific beat
   *
   * @param beat the beat to be marked
   */
  void setBeatMarker(int beat);

  /**
   * Updates the view along the parameters of the given note
   *
   * @param startBeat the starting beat to start the update
   * @param endBeat   the ending beat for the update
   */
  public void updateViewAt(int startBeat, int endBeat);
  
  void addKeyListener (KeyListener keyListener);
  void addMouseListener (FXMouseListener mouseListener, int beatNumber, Pitch pitch);
  Node root();
}