package cs3500.music.view.midi;

import java.util.Collection;

import javax.sound.midi.*;

import cs3500.music.model.Note;
import cs3500.music.model.Playable;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements MidiView {
  private Playable playable;
  private Synthesizer synth = null;
  private Receiver receiver = null;

  public MidiViewImpl() {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = synth.getReceiver();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }
  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

  @Override
  public void loadPlayable(Playable playable) {
    closeReceiver();
    this.playable = playable;
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = synth.getReceiver();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  /**
   * Plays a Playable by sending all info to the receiver immediately
   */
  public void renderWhole() {
    Collection<Note> notesToPlay = this.playable.getNotes();

    int beatLength = 60000000 / this.playable.getTempoBPM(); // in microseconds

    this.playNotes(notesToPlay, -1, beatLength);
  }

  @Override
  public void renderBeat(int beat) {
    Collection<Note> notesToPlay = this.playable.getNotesStartingAtBeat(beat);
    int beatLength = 60000000 / this.playable.getTempoBPM(); // in microseconds

    this.playNotes(notesToPlay, beat, beatLength);
  }

  private void playNotes(Collection<Note> notesToPlay, int currBeat, int beatLength) {
    long curTime = this.synth.getMicrosecondPosition();
    for (Note noteToPlay: notesToPlay) {
      MidiMessage start = null;
      MidiMessage stop = null;
      try {
        start = new ShortMessage(ShortMessage.NOTE_ON,
                noteToPlay.getInstrument(),
                noteToPlay.getPitch().getMidiNumber(),
                noteToPlay.getVolume());
        stop = new ShortMessage(ShortMessage.NOTE_OFF,
                noteToPlay.getInstrument(),
                noteToPlay.getPitch().getMidiNumber(),
                noteToPlay.getVolume());
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }

      long relativeStartTime = (noteToPlay.getStartingBeat() - currBeat) * beatLength;
      long relativeEndTime = (noteToPlay.getEndingBeat() - currBeat) * beatLength - 10000;
      long startTime = relativeStartTime + curTime;
      long endTime = relativeEndTime + curTime;

      this.receiver.send(start, startTime);
      this.receiver.send(stop, endTime);
    }
  }

  /**
   * Closes the receiver.
   * NOTE: Only call this after *all* notes are done playing
   */
  public void closeReceiver() {
    if(receiver != null) {
      this.receiver.close();
    }
    if(synth != null) {
      this.synth.close();
    }
  }
}
