package cs3500.music.view.console;

import java.util.Collection;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.Playable;

/**
 * Controller for a console text rendering of a music piece
 */
public final class ConsoleController {
  private final Playable playable;
  private final ConsoleView view;

  public ConsoleController(Playable playable, Appendable output) {
    this.playable = playable;
    view = new ConsoleView(output);
  }

  /**
   * Draws the console view
   */
  public void run() {
    view.draw(adaptModelToViewModel(playable));
  }

  /**
   * Creates a ConsoleViewModel based on a Playable in order to restrict
   * the view's access to implementation details of Playable.
   *
   * @param adaptee the playable to turn into a ConsoleViewModel
   */
  private static ConsoleViewModel adaptModelToViewModel(Playable adaptee) {
    int height = adaptee.getEndingBeat();
    int width = adaptee.getHighestPitch().getMidiNumber() -
            adaptee.getLowestPitch().getMidiNumber();
    return new ConsoleViewModel(width, height) {
      @Override
      public Collection<Note> getNotesStartingAt(int beat) {
        return adaptee.getNotesStartingAtBeat(beat);
      }

      @Override
      public Collection<Note> getNotesContinuingAt(int beat) {
        return adaptee.getNotesContinuingAtBeat(beat);
      }

      @Override
      public Pitch getLowestPitch() {
        return adaptee.getLowestPitch();
      }

      @Override
      public Pitch getHighestPitch() {
        return adaptee.getHighestPitch();
      }
    };
  }
}
