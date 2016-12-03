package cs3500.music.controller;

import cs3500.music.model.Pitch;

/**
 * Created by Anish on 11/25/15.
 */
public class MoveNoteRunnable implements Runnable
{
  private final CompositeController controller;
  private final int beatNum;
  private final Pitch curPitch;

  public MoveNoteRunnable(CompositeController controller, int beatNum, Pitch curPitch) {
    this.controller = controller;
    this.beatNum = beatNum;
    this.curPitch = curPitch;
  }

  @Override
  public void run() {
    if (controller.cellIsSelected) {
      controller.cellIsSelected = false;
      if (controller.noteExistsAt(controller.selectedBeat,
              controller.selectedPitch)) {
        controller.moveNote(controller.getNoteAt(controller.selectedBeat,
                                                 controller.selectedPitch),
                beatNum,
                curPitch);
        System.out.println("Moved Note to " + beatNum + curPitch.toString());
      }
    } else {
      System.out.println("No note selected");
    }
  }
}