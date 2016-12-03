package cs3500.music.view.console;

import java.io.IOException;
import java.util.Collection;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * Concrete class for rendering the consolve view
 */
public class ConsoleView {
  private final Appendable output;

  public ConsoleView(Appendable output) {
    this.output = output;
  }

  /**
   * Draw the given ConsoleViewModel as text to standard output
   *
   * @param vm the ConsoleViewModel to draw
   */
  public void draw(ConsoleViewModel vm) {
    String result = "";

    int precedingSpaces = (int) Math.ceil(Math.log10(vm.getHeight()));
    String firstRowSpaces = String.format("%" + precedingSpaces + "s", "");
    result += firstRowSpaces;

    // Set column pitch names
    Pitch curPitch = vm.getLowestPitch();
    Pitch highestPitch = vm.getHighestPitch();
    while (curPitch.compareTo(highestPitch) <= 0) {
      String pitchString = curPitch.toString();
      result += String.format("%4s", pitchString);
      curPitch = curPitch.nextPitch();
    }
    result += "\n";

    // Set each row, notes starting and continuing on each beat
    for (int i = 0; i < vm.getHeight(); i++) {
      String row = "";
      String curBeat = String.format("%" + precedingSpaces + "s", Integer.toString(i));
      row += curBeat;

      Collection<Note> notesStartingAtCurBeat = vm.getNotesStartingAt(i);
      Collection<Note> notesContinuingAtCurBeat = vm.getNotesContinuingAt(i);
      curPitch = vm.getLowestPitch();
      while (curPitch.compareTo(highestPitch) <= 0) {
        boolean curPitchStarting = false;
        for (Note curNote: notesStartingAtCurBeat) {
          if (curNote.getPitch().compareTo(curPitch) == 0) {
            curPitchStarting = true;
          }
        }
        boolean curPitchContinuing = false;
        for (Note curNote: notesContinuingAtCurBeat) {
          if (curNote.getPitch().compareTo(curPitch) == 0) {
            curPitchContinuing = true;
          }
        }

        if (curPitchStarting) {
          row += String.format("%4s", "X");
        } else if (curPitchContinuing) {
          row += String.format("%4s", "|");
        } else {
          row += String.format("%4s", "");
        }

        curPitch = curPitch.nextPitch();
      }

      result += row + "\n";
    }

    try{
      output.append(result);
    } catch (IOException e) {
      System.err.println("Appending to bad Appendable.");
      e.printStackTrace();
    }
  }
}
