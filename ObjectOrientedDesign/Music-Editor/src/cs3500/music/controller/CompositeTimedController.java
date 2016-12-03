package cs3500.music.controller;

import cs3500.music.model.Playable;
import cs3500.music.view.View;
import cs3500.music.view.gui.GuiView;
import cs3500.music.view.midi.MidiView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Created by Kevin on 11/25/2015.
 */
public class CompositeTimedController {
  private final GuiView guiView;
  private final MidiView midiView;
  private final Playable playable;

  public CompositeTimedController(GuiView guiView, MidiView midiView, Playable playable) {
    this.guiView = guiView;
    this.midiView = midiView;
    this.playable = playable;
    this.guiView.loadPlayable(playable);
    this.midiView.loadPlayable(playable);
  }

  public void run() {
    Timeline midiTimeline = new Timeline();
    Timeline guiTimeline = new Timeline();

    int endBeat = playable.getEndingBeat();
    for(int i = 0; i < endBeat; i++) {
      final int beat = i;
      final int beatTime = beat * 60000 / playable.getTempoBPM();

      midiTimeline.getKeyFrames().add(new KeyFrame(
              Duration.millis(beatTime),
              ae -> this.midiView.renderBeat(beat)));
      guiTimeline.getKeyFrames().add(new KeyFrame(
              Duration.millis(beatTime),
              ae -> this.guiView.setBeatMarker(beat)));
    }

    midiTimeline.play();
    guiTimeline.play();

//    int length = playable.getEndingBeat();
//    for(int i = 0; i < length; i++) {
//      this.view.renderBeat(playable, i);
//    }
  }
}
