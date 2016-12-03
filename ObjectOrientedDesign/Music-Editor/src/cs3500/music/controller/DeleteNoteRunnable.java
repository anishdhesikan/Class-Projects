package cs3500.music.controller;

import cs3500.music.model.Pitch;

/**
 * Created by Anish on 11/25/15.
 */
public class DeleteNoteRunnable implements Runnable {
  private final CompositeController controller;

  public DeleteNoteRunnable(CompositeController controller) {
    this.controller = controller;
  }

  @Override
  public void run() {
    if (controller.cellIsSelected) {
      controller.cellIsSelected = false;
      if (controller.noteExistsAt(controller.selectedBeat,
              controller.selectedPitch)) {
        controller.removeNote(controller.getNoteAt(controller.selectedBeat,
                controller.selectedPitch));
        System.out.println("Deleted");
      } else {
        System.out.println("Cannot delete non-existent note");
      }
    }
  }
}
