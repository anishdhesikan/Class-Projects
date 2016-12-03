package cs3500.music.model;

import java.util.Collection;

/**
 * Represents pieces of music that are playable such as songs and notes
 */
public interface Playable {

  /**
   * Returns a collection of notes that are starting at the given beat position.
   * Does not include notes that started on previous beats
   * and are continuing during the given beat.
   *
   * @param beatPosition the beat number to check
   * @return a collection of notes starting at the given beatPosition
   */
  Collection<Note> getNotesStartingAtBeat(int beatPosition);

  /**
   * Returns a collection of notes that are continuing at the given beat position.
   * Does not include notes that start on the given beat position.
   *
   * @param beatPosition the beat number to check
   * @return a collection of notes continuing at the given beatPosition
   */
  Collection<Note> getNotesContinuingAtBeat(int beatPosition);

  /**
   * Gets the ending beat of this Playable item.
   *
   * @return ending beat
   */
  int getEndingBeat();

  /**
   * Gets the highest Pitch in this Playable item
   *
   * @return highest Note
   */
  Pitch getHighestPitch();

  /**
   * Gets the lowest Pitch in this Playable item
   *
   * @return lowest Note
   */
  Pitch getLowestPitch();

  /**
   * Gets the tempo in Beats per minute (BPM) of this Playable
   * @return tempo in BPM
   */
  int getTempoBPM();


  /**
   * Returns a copy of the collection of notes in this song
   *
   * @return a copy of the collection of notes in this song
   */
  Collection<Note> getNotes();
}
