package cs3500.music.model;

import java.util.Collection;

import cs3500.music.util.CompositionBuilder;

/**
 * Represents a musical composition
 */
public interface Song extends Playable {
  /**
   * Determines if given note exists in this song.
   *
   * @param note note to be checked
   * @return does the given note exist?
   */
  boolean hasNote(Note note);

  /**
   * Adds a note to this music piece
   *
   * @throws IllegalArgumentException if there is already an equivalent note in this song
   * @throws IllegalArgumentException if the Song has a manual ending beat and this note ends after
   *                                  that beat.
   */
  void addNote(Note note);

  /**
   * Deletes the note that {@code .equals()} given note.
   *
   * @throws IllegalArgumentException if no such note is found in the model
   */
  void deleteNote(Note note);

  /**
   * Deletes the noteToEdit and adds resultantNote in its place, simulating the editing of the
   * note. Duration of the resultantNote is set to the same as the duration of the noteToEdit.
   *
   * @throws IllegalArgumentException if no such note noteToEdit is found
   * @throws IllegalArgumentException if there is already an equivalent note in this song
   */
  void editNote(Note noteToEdit, Note resultantNote);

  /**
   * Manually sets the ending beat to some beat after all the notes are ended.
   *
   * @param beat beat on which to end the song.
   * @throws IllegalArgumentException if the beat is not after all the notes have ended in the
   *                                  song.
   */
  void setEndingBeat(int beat);

  /**
   * Resets the ending beat to be the ending beat of the last note.
   */
  void setDefaultEndingBeat();

  /**
   * Sets the tempo of this song, in Beats per Minute
   *
   * @param newTempo new tempo in bpm
   */
  void setTempoBPM(int newTempo);

  /**
   * Returns a new song with the given song appended to this song. The given song is appended at
   * the end of this song plus the given beatOffset.
   *
   * @param songToAppend the song to append to this song
   * @param beatOffset   the offset from the end of this song to add the given song
   * @return a new song with songToAppend appended to this song
   * @throws IllegalArgumentException if appending the songs causes two notes to start at the same
   *                                  position
   * @throws IllegalArgumentException if any resulting notes start at a beat that is less than 0
   */
  Song appendSong(Song songToAppend, int beatOffset);

  /**
   * Appends songToAppend to this song starting at the given beatPosition
   *
   * @param songToAppend song to append to this song
   * @param beatPosition beat at which to append the given song
   * @return a new song with songToAppend appended to this song
   * @throws IllegalArgumentException if appending the songs causes two notes to start at the same
   *                                  position
   * @throws IllegalArgumentException if any resulting notes start at a beat that is less than 0
   */
  Song addSongAt(Song songToAppend, int beatPosition);

  /**
   * Get the beat to play after curBeat, considering the repeats in this song
   *
   * @param curBeat the current beat
   * @param repeatsAlreadyUsed collection of Repeats that have already been repeated
   * @return the beat number that comes after curBeat in this song
   */
  int getNextBeat(int curBeat, Collection<Repeat> repeatsAlreadyUsed);

  /**
   * Adds a Repeat to this song
   */
  void addRepeat(Repeat repeatToAdd);

  /**
   * Returns a collection of Repeats that begin on the given beat
   *
   * @param startingBeat the beat at which the repeats begin
   * @return a collection of Repeats that begin on the given beat
   */
  Collection<Repeat> getRepeatsStartingAtBeat(int startingBeat);

  /**
   * Returns a collection of Repeats that repeat on the given beat
   *
   * @param repeatingBeat the beat at which the Repeats repeat
   * @return a collection of Repeats that repeat on the given beat
   */
  Collection<Repeat> getRepeatsRepeatingAtBeat(int repeatingBeat);

  public interface Builder {
    /**
     * Builds and returns the specified {@code Song}
     *
     * @return new {@Song}
     */
    Song build();

    /**
     * Adds a new {@code Note} to the {@code Song} being built
     *
     * @param n the {@code Note} being added
     * @return {@code this}; for method chaining
     */
    Builder addNote(Note n);

    /**
     * Adds a new {@code BasicRepeat} to the {@code Song} being built
     *
     * @param start the starting beat for the repeat
     * @param end the beat at which to return to the starting beat
     * @return {@code this}; for method chaining
     */
    Builder addBasicRepeat(int start, int end);

    /**
     * Adds a new {@code AlternateEnding} to the {@code Song} being built
     *
     * @param beatToRepeatTo The beat to go to upon reaching end of this section
     * @param start The start beat of the repeat
     * @param end The beat at which to return to the beatToRepeatTo
     * @return {@code this}; for method chaining
     */
    Builder addAltEnd(int beatToRepeatTo, int start, int end);

    /**
     * Sets the tempo (in BPM) of the {@code Song} being built
     *
     * @param tempo the tempo (in BPM)
     * @return {@code this}; for method chaining
     */
    Builder setTempoBPM(int tempo);
  }
}
