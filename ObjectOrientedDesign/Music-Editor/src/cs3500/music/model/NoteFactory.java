package cs3500.music.model;

/**
 * Created by Kevin McDonough on 11/12/2015.
 */
public interface NoteFactory {
  /**
   * Creates a note with the given pitch, starting beat, duration in pitches,
   * volume and instrument number
   *
   * @param pitch pitch of the note
   * @param startBeat starting beat of the note
   * @param duration duration of the note in beats
   * @param volume volume of note on scale [0, 100]
   * @param instrument MIDI instrument number; range [0, 128)
   * @return new Note with given properties
   */
  Note makeNote(Pitch pitch, int startBeat, int duration, int volume, int instrument);
}
