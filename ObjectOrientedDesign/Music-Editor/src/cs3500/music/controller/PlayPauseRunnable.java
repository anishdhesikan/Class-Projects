package cs3500.music.controller;

/**
 * Created by Kevin on 11/25/2015.
 */
public class PlayPauseRunnable implements Runnable {
  private final CompositeController controller;

  PlayPauseRunnable(CompositeController controller) {
    this.controller = controller;
  }

  @Override
  public void run() {
    controller.togglePlayPause();
  }
}
