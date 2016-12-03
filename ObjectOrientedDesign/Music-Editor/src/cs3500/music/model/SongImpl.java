package cs3500.music.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import cs3500.music.util.CompositionBuilder;

/**
 * An implementation of a Song, representing songs as TreeMaps from beat to collections of notes at
 * that beat.
 */
public class SongImpl implements Song {
  // Maps beat numbers to collections of notes starting at that beat. A key doesn't exist iff
  // no notes start at that beat.
  TreeMap<Integer, Collection<Note>> noteStarts;
  // Maps beat numbers to collections of notes continuing (not starting) at that beat. A key
  // doesn't exist iff no notes start at that beat.
  TreeMap<Integer, Collection<Note>> noteContinues;
  // Collection of all the repeats in this song. Order doesn't matter.
  Collection<Repeat> repeats;

  // Manual ending beat. If manualEndingBeat == -1, then the ending beat is automatically
  // the first beat where nothing is played.
  int manualEndingBeat;
  private int tempo;

  /*
  Invariants:
   - For every Collection c in noteStarts and noteContinues, c.isEmpty() == false
   - If noteStarts.get(beat).contains(n) then !noteStarts.get(beat).contains(n)
   - Any Note exists in a collection in noteStarts, and has getDuration() > 1, then it
      exists in all the appropriate collections in noteContinues
   - manualEndingBeat >= -1
   - If manualEndingBeat != -1 then noteStarts and noteContinues have no elements with
      keys >= manualEndingBeat
   */

  public SongImpl() {
    super();
    this.noteStarts = new TreeMap<>();
    this.noteContinues = new TreeMap<>();
    this.repeats = new ArrayList<Repeat>();
    this.manualEndingBeat = -1;
    this.tempo = 120;
  }

  /**
   * A private constructor making a new Song based on the given noteStarts and noteContinues,
   * (creating new TreeMaps and Collections to avoid alias problems).
   */
  private SongImpl(TreeMap<Integer, Collection<Note>> noteStarts, TreeMap<Integer,
          Collection<Note>> noteContinues, int manualEndingBeat) {
    this();
    for (int key : noteStarts.keySet()) {
      Collection<Note> newNotes = new HashSet<>();
      newNotes.addAll(noteStarts.get(key));

      this.noteStarts.put(key, newNotes);
    }
    for (int key : noteContinues.keySet()) {
      Collection<Note> newNotes = new HashSet<>();
      newNotes.addAll(noteContinues.get(key));

      this.noteContinues.put(key, newNotes);
    }
    this.manualEndingBeat = manualEndingBeat;
  }

  @Override
  public void setEndingBeat(int beat) {
    if (beat < this.getDefaultEndingBeat()) {
      throw new IllegalArgumentException("Manual ending beat must be" +
              "after last note has completed");
    }
    this.manualEndingBeat = beat;
  }

  @Override
  public void setDefaultEndingBeat() {
    this.manualEndingBeat = -1;
  }

  @Override
  public void setTempoBPM(int newTempo) {
    this.tempo = newTempo;
  }

  @Override
  public boolean hasNote(Note note) {
    int startBeat = note.getStartingBeat();
    return this.noteStarts.containsKey(startBeat) &&
            this.noteStarts.get(startBeat).contains(note);
  }

  @Override
  public void addNote(Note note) {
    // Ensure this note doesn't already exist
    if (this.hasNote(note)) {
      throw new IllegalArgumentException("Note already exists in song");
    }
    // If this song has a manual ending, ensure this note doesn't end to late
    if (manualEndingBeat > -1 && note.getEndingBeat() > this.manualEndingBeat) {
      throw new IllegalArgumentException("Note ends after manual song ending.");
    }

    addToBeatMap(note, note.getStartingBeat(), this.noteStarts);
    for (int beat = note.getStartingBeat() + 1; beat < note.getEndingBeat(); beat++) {
      addToBeatMap(note, beat, this.noteContinues);
    }
  }

  @Override
  public void deleteNote(Note note) {
    if (!this.hasNote(note)) {
      throw new IllegalArgumentException("Note does not exist in this song.");
    }

    removeFromBeatMap(note, note.getStartingBeat(), this.noteStarts);
    for (int beat = note.getStartingBeat() + 1; beat < note.getEndingBeat(); beat++) {
      removeFromBeatMap(note, beat, this.noteContinues);
    }
  }

  /**
   * Adds the given note to collection at given beat in given beat map, creating a new
   * beat-collection pair if necessary
   *
   * @param note    note to be removed
   * @param beat    key of Collection to be removed from.
   * @param beatMap beat map to be removed from.
   */
  private void addToBeatMap(Note note, int beat, TreeMap<Integer, Collection<Note>> beatMap) {
    if (!beatMap.containsKey(beat)) {
      beatMap.put(beat, new HashSet<>());
    }
    beatMap.get(beat).add(note);
  }

  /**
   * Removes the given note from collection at given beat in given beat map, removing the
   * collection at that beat if its empty.
   *
   * @param note    note to be removed
   * @param beat    key of Collection to be removed from.
   * @param beatMap beat map to be removed from.
   * @throws IllegalArgumentException if the note doesn't exist in the collection with key {@code
   *                                  beat} in {@code beatMap}
   */
  private void removeFromBeatMap(Note note, int beat, TreeMap<Integer, Collection<Note>> beatMap) {
    // ensure collection exists for the beat.
    if (!beatMap.containsKey(beat)) {
      throw new IllegalArgumentException("Cannot remove a note from a beat where it " +
              "doesn't exist.");
    }
    // get the collection of notes at the given beat.
    Collection<Note> beatCol = beatMap.get(beat);
    // ensure the note is in the collection.
    if (!beatCol.contains(note)) {
      throw new IllegalArgumentException("Cannot remove a note from a beat where it " +
              "doesn't exist.");
    }
    // finally remove the note from the collection
    beatCol.remove(note);
    // if the collection is empty, remove it from the map
    if (beatCol.isEmpty()) {
      beatMap.remove(beat);
    }
  }

  @Override
  public void editNote(Note noteToEdit, Note resultantNote) {
    this.deleteNote(noteToEdit);
    this.addNote(resultantNote);
  }

  @Override
  public Song appendSong(Song song, int beatOffset) {
    return addSongAt(song, getEndingBeat() + beatOffset);
  }

  @Override
  public Song addSongAt(Song song, int beatPosition) {
    Song newSong = new SongImpl(this.noteStarts, this.noteContinues, this.manualEndingBeat);
    for (int beat = 0; beat < song.getEndingBeat(); beat++) {
      int newBeat = beat + beatPosition;
      Collection<Note> notesToAdd = song.getNotesStartingAtBeat(beat);
      for (Note note : notesToAdd) {
        Note shiftedNote = note.withStartingBeat(newBeat);
        if (!this.hasNote(shiftedNote)) {
          newSong.addNote(note);
        }
      }
    }
    return newSong;
  }

  @Override
  public int getNextBeat(int curBeat, Collection<Repeat> repeatsAlreadyUsed) {
    int nextBeat = curBeat + 1;

    Collection<Repeat> validRepeatsNotUsed = new ArrayList<Repeat>();
    validRepeatsNotUsed.addAll(this.repeats);
    Collection<Repeat> validRepeatsUsed = new ArrayList<Repeat>();
    for (Repeat curRepeat : repeatsAlreadyUsed) {
      if (validRepeatsNotUsed.contains(curRepeat)) {
        validRepeatsNotUsed.remove(curRepeat);
        validRepeatsUsed.add(curRepeat);
      }
    }

    // if any repeat that isn't already used repeats on curBeat, repeat the section
    for (Repeat curRepeat : validRepeatsNotUsed) {
      if (curRepeat.getRepeatingBeat() - 1 == curBeat) {
        nextBeat = curRepeat.getBeatToRepeatTo();
      }
    }

    boolean tempNextSet = false;
    int tempNextBeat = curBeat;

    // skip a section if it is already used and is marked to skip after a playthrough
    for (Repeat curRepeat : validRepeatsUsed) {
      if (curRepeat.getStartingBeat() - 1 == tempNextBeat && curRepeat.skipSectionAfterPlaythrough
              ()) {
        tempNextBeat = curRepeat.getRepeatingBeat() - 1;
        tempNextSet = true;
      }
    }
    if (tempNextSet) {
      nextBeat = tempNextBeat + 1;
    }

    return nextBeat;
  }

  @Override
  public void addRepeat(Repeat repeatToAdd) {
    this.repeats.add(repeatToAdd);
  }

  @Override
  public Collection<Repeat> getRepeatsStartingAtBeat(int startingBeat) {
    Collection<Repeat> resultingRepeats = new ArrayList<Repeat>();

    for (Repeat curRepeat : repeats) {
      if (curRepeat.getStartingBeat() == startingBeat) {
        resultingRepeats.add(curRepeat);
      }
    }

    return resultingRepeats;
  }

  @Override
  public Collection<Repeat> getRepeatsRepeatingAtBeat(int repeatingBeat) {
    Collection<Repeat> resultingRepeats = new ArrayList<Repeat>();

    for (Repeat curRepeat : repeats) {
      if (curRepeat.getRepeatingBeat() - 1 == repeatingBeat) {
        resultingRepeats.add(curRepeat);
      }
    }

    return resultingRepeats;
  }

  @Override
  public Collection<Note> getNotes() {
    Collection<Note> noteBag = new HashSet<>();
    for (int beat : this.noteStarts.keySet()) {
      noteBag.addAll(this.noteStarts.get(beat));
    }
    return noteBag;
  }

  @Override
  public Collection<Note> getNotesStartingAtBeat(int beatPosition) {
    Collection<Note> noteBag = new HashSet<>();
    if (this.noteStarts.containsKey(beatPosition)) {
      noteBag.addAll(this.noteStarts.get(beatPosition));
    }
    return noteBag;
  }

  @Override
  public Collection<Note> getNotesContinuingAtBeat(int beatPosition) {
    Collection<Note> noteBag = new HashSet<>();
    if (this.noteContinues.containsKey(beatPosition)) {
      noteBag.addAll(this.noteContinues.get(beatPosition));
    }
    return noteBag;
  }

  @Override
  public int getEndingBeat() {
    if (this.manualEndingBeat == -1) {
      return this.getDefaultEndingBeat();
    } else {
      return this.manualEndingBeat;
    }
  }

  @Override
  public Pitch getHighestPitch() {
    Pitch highestPitch = null;
    for (int beat : this.noteStarts.keySet()) {
      Collection<Note> notesAtBeat = this.noteStarts.get(beat);
      for (Note note : notesAtBeat) {
        if (highestPitch == null ||
                note.getPitch().compareTo(highestPitch) > 0) {
          highestPitch = note.getPitch();
        }
      }
    }
    return highestPitch;
  }

  @Override
  public Pitch getLowestPitch() {
    Pitch lowestPitch = null;
    for (int beat : this.noteStarts.keySet()) {
      Collection<Note> notesAtBeat = this.noteStarts.get(beat);
      for (Note note : notesAtBeat) {
        if (lowestPitch == null ||
                note.getPitch().compareTo(lowestPitch) < 0) {
          lowestPitch = note.getPitch();
        }
      }
    }
    return lowestPitch;
  }

  /**
   * Gets the beat on which the last note ends.
   *
   * @return ending beat of last note
   */
  private int getDefaultEndingBeat() {
    if (noteStarts.size() == 0) {
      return 0;
    }
    return this.noteContinues.lastKey() + 1;
  }

  @Override
  public int getTempoBPM() {
    return this.tempo;
  }

  public static CompositionBuilder<Song> compositionBuilder() {
    return new CompositionBuilderImpl(new NoteImpl.Factory(), new PitchImpl.Factory());
  }

  /**
   * A class adapter from {@code Song.Builder SongImpl.builder()} to {@code
   * CompositionBuilder<Song>}
   */
  protected static final class CompositionBuilderImpl implements CompositionBuilder<Song> {
    Builder builder;
    NoteFactory noteFact;
    PitchFactory pitchFact;

    protected CompositionBuilderImpl(NoteFactory noteFactory, PitchFactory pitchFactory) {
      this.builder = SongImpl.builder();
      this.noteFact = noteFactory;
      this.pitchFact = pitchFactory;
    }

    @Override
    public Song build() {
      return this.builder.build();
    }

    @Override
    public CompositionBuilder<Song> setTempo(int tempo) {
      int bpmTempo = 60000000 / tempo;
      this.builder.setTempoBPM(bpmTempo);
      return this;
    }

    @Override
    public CompositionBuilder<Song> addNote(int start, int end,
                                            int instrument, int pitch, int volume) {
      Pitch notePitch = pitchFact.makePitch(pitch);
      Note newNote = noteFact.makeNote(notePitch, start, end - start, volume, instrument - 1);
      this.builder.addNote(newNote);
      return this;
    }

    @Override
    public CompositionBuilder<Song> addBasicRepeat(int start, int end) {
      this.builder.addBasicRepeat(start, end);
      return this;
    }

    @Override
    public CompositionBuilder<Song> addAltEnd(int beatToRepeatTo, int start, int end) {
      this.builder.addAltEnd(beatToRepeatTo, start, end);
      return null;
    }
  }

  /**
   * Creates and returns a new SongImpl builder
   *
   * @return new {@code SongImpl.BuilderImpl}
   */
  public static Builder builder() {
    return new BuilderImpl();
  }

  /**
   * A Song.Builder implementation for SongImpl
   */
  protected static final class BuilderImpl implements Builder {
    private final Song song;

    private BuilderImpl() {
      this.song = new SongImpl();
    }

    @Override
    public Song build() {
      Song newSong = new SongImpl();
      for (Note note : this.song.getNotes()) {
        newSong.addNote(note);
      }
      newSong.setTempoBPM(song.getTempoBPM());
      // add all repeats
      for (int i = 0; i < song.getEndingBeat(); i++) {
        for (Repeat curRepeat : song.getRepeatsStartingAtBeat(i)) {
          newSong.addRepeat(curRepeat);
        }
      }
      return newSong;
    }

    @Override
    public Song.Builder addNote(Note n) {
      this.song.addNote(n);
      return this;
    }

    @Override
    public Builder addBasicRepeat(int start, int end) {
      this.song.addRepeat(new BasicRepeat(start, end));
      return this;
    }

    @Override
    public Builder addAltEnd(int beatToRepeatTo, int start, int end) {
      this.song.addRepeat(new AlternateEnding(beatToRepeatTo, start, end));
      return this;
    }

    @Override
    public Song.Builder setTempoBPM(int tempo) {
      this.song.setTempoBPM(tempo);
      return this;
    }
  }
}
