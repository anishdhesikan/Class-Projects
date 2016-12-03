package cs3500.music.view.console;

import java.util.Collection;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * Model for backing the ConsoleView. This abstract class is designed to remove
 * the direct dependency of the view on the model; instead, the controller
 * implements this interface with information from the view and then passes the
 * ConsoleViewModel object for the view to render concretely
 */
public abstract class ConsoleViewModel {
  private final int width;
  private final int height;

  protected ConsoleViewModel(int width, int height) {
    this.width = width;
    this.height = height;
  }

  /**
   * Get the width (range of pitches)
   */
  public final int getWidth() {
    return width;
  }


  /**
   * Get the height (total beats)
   */
  public final int getHeight() {
    return height;
  }

  public abstract Collection<Note> getNotesStartingAt(int beat);
  public abstract Collection<Note> getNotesContinuingAt(int beat);
  public abstract Pitch getLowestPitch();
  public abstract Pitch getHighestPitch();
}
