package cs3500.music.model;

/**
 * Represents a repeated section (i.e. basic repeats or alternate endings) in a music piece
 */
public interface Repeat {
  /**
   * Returns whether this section should be skipped after playing through it once
   */
  boolean skipSectionAfterPlaythrough ();

  /**
   * Returns the beat at which to begin the repeat
   */
  int getRepeatingBeat ();

  /**
   * Returns the beat upon which this section begins
   */
  int getStartingBeat ();

  /**
   * Returns the beat to return to upon repeating
   */
  int getBeatToRepeatTo ();
}
