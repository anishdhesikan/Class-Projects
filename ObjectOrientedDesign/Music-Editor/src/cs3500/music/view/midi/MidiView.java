package cs3500.music.view.midi;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.Playable;
import cs3500.music.view.View;

/**
 * Interface for producing a Midi rendering of a music piece
 */
public interface MidiView extends View {
  /**
   * Performs operations necessary to close the view
   */
  void closeReceiver();
}
