package cs3500.music.model;

/**
 * Represents a musical pitch
 */
public interface Pitch {
  /**
   * Gets the PitchClass of this pitch
   *
   * @return the PitchClass of this pitch
   */
  PitchClass getPitchClass();

  /**
   * Gets the octave number of this pitch
   *
   * @return the octave number of this pitch
   */
  int getOctave();

  /**
   * Gets the MIDI number representation of this pitch
   *
   * @return the MIDI number representation of this pitch
   */
  int getMidiNumber();

  /**
   * Compare this pitch with the given pitch to find out which is higher or
   * whether they are the same
   *
   * @param other the pitch this pitch is being compared to
   * @return an int representing the comparison between the two pitches.
   *      negative number means this pitch is lower than the given pitch
   *      positive number means this pitch is higher that the given pitch
   *      zero means this pitch is the same as the given pitch
   */
  int compareTo(Pitch other);

  /**
   * Get a pitch that is the next pitch
   * (e.g. the pitch after C is C#, the pitch after C# is D)
   *
   * @return a Pitch that is equivalent to the pitch after this pitch
   */
  Pitch nextPitch();
}
