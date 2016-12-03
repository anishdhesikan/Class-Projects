package cs3500.music.controller;

import cs3500.music.model.Pitch;

/**
 * Runnable that selects a beat number and Pitch
 */
public class SelectCellRunnable implements Runnable
{
  private final CompositeController controller;
  private final int beatNum;
  private final Pitch curPitch;

  public SelectCellRunnable(CompositeController controller, int beatNum, Pitch curPitch) {
    this.controller = controller;
    this.beatNum = beatNum;
    this.curPitch = curPitch;
  }

  @Override
  public void run() {
    controller.cellIsSelected = true;
    controller.selectedBeat = beatNum;
    controller.selectedPitch = curPitch;

    System.out.println("Selected " + beatNum + curPitch.toString());
  }
}
