package cs3500.music.model;

/**
 * Created by Kevin McDonough on 11/12/2015.
 */
public interface PitchFactory {
  /**
   * Make a pitch with the given pitch class and octave number.
   *
   * @param pitchClass Pitch class of the note
   * @param octave Octave number of the note. Typically from 0 to 8.
   * @return new Pitch with given attributes
   */
  Pitch makePitch(PitchClass pitchClass, int octave);

  /**
   * Make a pitch with the given MIDI number
   *
   * @param midiNumber MIDI number of pitch; range [0, 128)
   * @return new Pitch with giben attributes
   */
  Pitch makePitch(int midiNumber);
}
