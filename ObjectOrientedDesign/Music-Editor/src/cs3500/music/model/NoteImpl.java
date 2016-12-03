package cs3500.music.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * An implementation of a Note, storing a note with Pitch and ints for starting beat, duration,
 * volume and instrument
 */
public class NoteImpl extends AbstractNote {
  Pitch pitch; // pitch of this note
  int startBeat; // starting beat
  int duration; // duration in beats
  int volume; // volume on the scale from 0 to 100
  int instrument; // Midi number of instrument playing this note

  /*
  INVARIANTS:
   - duration >= 0;
   - 0 <= volume <= 100;
   - 0 <= instrument < 128;
   */

  public NoteImpl(Pitch pitch, int startBeat, int duration, int volume, int instrument) {
    this.pitch = pitch;
    this.startBeat = startBeat;
    this.duration = duration;
    this.volume = volume;
    this.instrument = instrument;
  }

  @Override
  public int getStartingBeat() {
    return this.startBeat;
  }

  @Override
  public int getDuration() {
    return this.duration;
  }

  @Override
  public int getVolume() {
    return this.volume;
  }

  @Override
  public int getInstrument() {
    return this.instrument;
  }

  @Override
  public Pitch getPitch() {
    return this.pitch;
  }

  @Override
  public int getEndingBeat() {
    return this.startBeat + this.duration;
  }

  @Override
  public int getTempoBPM() {
    return 120;
  }

  @Override
  public Collection<Note> getNotes() {
    Collection<Note> noteBag = new HashSet<>();
    noteBag.add(this);
    return noteBag;
  }

  @Override
  public Pitch getHighestPitch() {
    return this.pitch;
  }

  @Override
  public Pitch getLowestPitch() {
    return this.pitch;
  }

  @Override
  public Note withStartingBeat(int newStartingBeat) {
    return new NoteImpl(this.pitch, newStartingBeat, this.duration, this.volume, this.instrument);
  }

  public static class Factory implements NoteFactory {
    @Override
    public Note makeNote(Pitch pitch, int startBeat, int duration, int volume, int instrument) {
      return new NoteImpl(pitch, startBeat, duration, volume, instrument);
    }
  }
}