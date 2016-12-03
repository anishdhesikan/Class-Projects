package cs3500.music.model;

/**
 * Represents a basic repeated section in a musical piece. Plays through to its repeatingBeat,
 * repeats back to its starting beat, and plays through again without repeating twice
 */
public class BasicRepeat implements Repeat {
  private final int startingBeat;
  private final int repeatingBeat;

  public BasicRepeat(int startingBeat, int repeatingBeat) {
    this.startingBeat = startingBeat;
    this.repeatingBeat = repeatingBeat;
  }

  @Override
  public boolean skipSectionAfterPlaythrough() {
    return false;
  }

  @Override
  public int getRepeatingBeat() {
    return repeatingBeat;
  }

  @Override
  public int getStartingBeat() {
    return startingBeat;
  }

  @Override
  public int getBeatToRepeatTo() {
    return startingBeat;
  }
}
