package cs3500.music.model;

/**
 * Represents a musical note
 */
public interface Note extends Playable {
  /**
   * Gets the starting beat of this note
   *
   * @return starting beat number
   */
  int getStartingBeat();

  /**
   * Creates a new Note with given starting beat, otherwise identical to this Note.
   *
   * @param newStartingBeat new starting beat
   * @return new Note with given starting beat
   */
  Note withStartingBeat(int newStartingBeat);

  /**
   * Gets the duration of this note in beats
   *
   * @return duration in beats
   */
  int getDuration();

  /**
   * Gets the volume of this Note, on the range [0, 100], 0 being silent.
   *
   * @return volume of the note; [0, 100]
   */
  int getVolume();

  /**
   * Instrument number of the note
   *
   * @return MIDI Instrument number.
   */
  int getInstrument();

  /**
   * Gets the pitch of this note
   *
   * @return the pitch
   */
  Pitch getPitch();
}
