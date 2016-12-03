package cs3500.music.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * An Abstract note, implementing all shared features of Notes
 */
abstract class AbstractNote implements Note {
  @Override
  public boolean equals(Object that) {
    if(!(that instanceof AbstractNote)) {
      return false;
    }
    AbstractNote thatN = (AbstractNote) that;
    return thatN.getStartingBeat() == this.getStartingBeat() &&
            thatN.getDuration() == this.getDuration() &&
            thatN.getVolume() == this.getVolume() &&
            thatN.getInstrument() == this.getInstrument() &&
            thatN.getPitch().equals(this.getPitch());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getStartingBeat(),
            this.getDuration(),
            this.getVolume(),
            this.getInstrument(),
            this.getPitch());
  }

  @Override
  public Collection<Note> getNotesStartingAtBeat(int beatPosition) {
    HashSet<Note> atBeat = new HashSet<>();
    if(beatPosition == this.getStartingBeat()) {
      atBeat.add(this);
    }
    return atBeat;
  }

  @Override
  public Collection<Note> getNotesContinuingAtBeat(int beatPosition) {
    HashSet<Note> atBeat = new HashSet<>();
    if(beatPosition > this.getStartingBeat() && beatPosition < this.getEndingBeat()) {
      atBeat.add(this);
    }
    return atBeat;
  }
}
