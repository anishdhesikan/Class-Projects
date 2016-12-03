package cs3500.music.controller;

import cs3500.music.model.Note;
import cs3500.music.model.NoteFactory;
import cs3500.music.model.NoteImpl;

/**
 * Created by Anish on 11/25/15.
 */
public class AddNoteRunnable implements Runnable {
  private final CompositeController controller;
  private final int beatLength;

  public AddNoteRunnable(CompositeController controller, int beatLength) {
    this.controller = controller;
    this.beatLength = beatLength;
  }

  @Override
  public void run() {
    if (controller.cellIsSelected) {
      controller.cellIsSelected = false;
      NoteFactory factory = new NoteImpl.Factory();
      Note noteToAdd = factory.makeNote(controller.selectedPitch,
              controller.selectedBeat, beatLength, 100, 1);
      controller.addNote(noteToAdd);
      System.out.println("Added");
    }
  }
}