package cs3500.music.model;

/**
 * Represents an alternate ending. Plays through once, then repeats to the beginning of the song
 * and skips this entire section the next time
 */
public class AlternateEnding implements Repeat {
  private final int startingBeat;
  private final int repeatingBeat;
  private final int beatToRepeatTo;

  public AlternateEnding(int beatToRepeatTo, int startingBeat, int repeatingBeat) {
    this.startingBeat = startingBeat;
    this.repeatingBeat = repeatingBeat;
    this.beatToRepeatTo = beatToRepeatTo;
  }

  @Override
  public boolean skipSectionAfterPlaythrough() {
    return true;
  }

  @Override
  public int getRepeatingBeat() {
    return this.repeatingBeat;
  }

  @Override
  public int getStartingBeat() {
    return this.startingBeat;
  }

  @Override
  public int getBeatToRepeatTo() {
    return beatToRepeatTo;
  }
}
