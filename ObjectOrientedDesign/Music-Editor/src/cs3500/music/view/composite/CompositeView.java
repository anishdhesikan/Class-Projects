package cs3500.music.view.composite;

import java.awt.event.KeyListener;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.Playable;
import cs3500.music.controller.FXKeyListener;
import cs3500.music.controller.FXMouseListener;
import cs3500.music.view.midi.MidiView;
import cs3500.music.view.gui.GuiView;
import javafx.scene.Node;

/**
 * A view that combines both gui and midi views
 */
public class CompositeView implements GuiView, MidiView {

  private final GuiView guiView;
  private final MidiView midiView;

  public CompositeView(GuiView guiView, MidiView midiView) {
    this.guiView = guiView;
    this.midiView = midiView;
  }

  @Override
  public void loadPlayable(Playable playable) {
    guiView.loadPlayable(playable);
    midiView.loadPlayable(playable);
  }

  @Override
  public void updateViewAt(int startBeat, int endBeat) {
    guiView.updateViewAt(startBeat, endBeat);
  }

  @Override
  public void setBeatMarker(int beat) {
    guiView.setBeatMarker(beat);
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    guiView.addKeyListener(keyListener);
  }

  @Override
  public void addMouseListener(FXMouseListener mouseListener, int beatNumber, Pitch pitch) {
    guiView.addMouseListener(mouseListener, beatNumber, pitch);
  }

  @Override
  public Node root() {
    return guiView.root();
  }

  @Override
  public void closeReceiver() {
    midiView.closeReceiver();
  }

  @Override
  public void renderWhole() {
    guiView.renderWhole();
    midiView.renderWhole();
  }

  @Override
  public void renderBeat(int beat) {
    guiView.setBeatMarker(beat);
    midiView.renderBeat(beat);
  }
}
