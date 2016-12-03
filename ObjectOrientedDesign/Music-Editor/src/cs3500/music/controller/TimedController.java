package cs3500.music.controller;


import cs3500.music.model.Playable;
import cs3500.music.view.View;
import cs3500.music.view.composite.CompositeView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Created by Kevin McDonough on 11/24/2015.
 */
public class TimedController {
  private final View view;
  private final Playable playable;

  public TimedController(View view, Playable playable) {
    this.view = view;
    this.playable = playable;
    view.loadPlayable(playable);
  }

  public void run() {
    Timeline timeline = new Timeline();
    for(int i = 0; i < playable.getEndingBeat(); i++) {
      final int beat = i;
      final int beatTime = beat * 60000 / playable.getTempoBPM();
      timeline.getKeyFrames().add(new KeyFrame(
              Duration.millis(beatTime),
              ae -> this.view.renderBeat(beat)));
    }
    timeline.play();

//    int length = playable.getEndingBeat();
//    for(int i = 0; i < length; i++) {
//      this.view.renderBeat(playable, i);
//    }
  }
}
