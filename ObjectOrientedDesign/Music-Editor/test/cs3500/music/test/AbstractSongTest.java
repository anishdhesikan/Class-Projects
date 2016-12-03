package cs3500.music.test;


import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import cs3500.music.model.Note;
import cs3500.music.model.NoteFactory;
import cs3500.music.model.PitchFactory;
import cs3500.music.model.Song;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing any implementation of song
 */
@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public abstract class AbstractSongTest {
  ExamplesNotes noteEx;

  AbstractSongTest(NoteFactory noteFactory, PitchFactory pitchFactory) {
    this.noteEx = new ExamplesNotes(noteFactory, pitchFactory);
  }

  @Test
  public void testHasNote_AddNote() throws Exception {
    Song song;

    song = song();
    assertEquals(false, song.hasNote(noteEx.c4_0_4()));
    assertEquals(false, song.hasNote(noteEx.c5_0_4()));
    assertEquals(false, song.hasNote(noteEx.d4_0_4()));
    song.addNote(noteEx.c4_0_4());
    assertEquals(true, song.hasNote(noteEx.c4_0_4()));
    assertEquals(false, song.hasNote(noteEx.c5_0_4()));
    assertEquals(false, song.hasNote(noteEx.d4_0_4()));

    song = song();
    assertEquals(false, song.hasNote(noteEx.c4_0_4()));
    assertEquals(false, song.hasNote(noteEx.c5_0_4()));
    assertEquals(false, song.hasNote(noteEx.d4_0_4()));
    song.addNote(noteEx.d4_0_4());
    assertEquals(false, song.hasNote(noteEx.c4_0_4()));
    assertEquals(false, song.hasNote(noteEx.c5_0_4()));
    assertEquals(true, song.hasNote(noteEx.d4_0_4()));

    song = song();
    assertEquals(false, song.hasNote(noteEx.c4_0_4()));
    assertEquals(false, song.hasNote(noteEx.c5_0_4()));
    assertEquals(false, song.hasNote(noteEx.d4_0_4()));
    song.addNote(noteEx.b3_0_4());
    song.addNote(noteEx.c5_0_4());
    song.addNote(noteEx.d4_0_4());
    assertEquals(false, song.hasNote(noteEx.c4_0_4()));
    assertEquals(true, song.hasNote(noteEx.c5_0_4()));
    assertEquals(true, song.hasNote(noteEx.d4_0_4()));
  }

  // Start change progress

  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteAlreadyExists() throws Exception {
    Song song;

    song = song();
    song.addNote(noteEx.c4_0_4());
    song.addNote(noteEx.c4_0_4());
  }

  @Test
  public void testAddNote_GetNotes() throws Exception {
    Song song = song();

    assertTrue(sameNotes(Arrays.asList(), song.getNotes()));
    song.addNote(noteEx.c4_0_4());
    assertTrue(sameNotes(Arrays.asList(noteEx.c4_0_4()), song.getNotes()));
    song.addNote(noteEx.c5_0_4());
    song.addNote(noteEx.b3_0_4());
    assertTrue(sameNotes(Arrays.asList(noteEx.c4_0_4(), noteEx.c5_0_4(), noteEx.b3_0_4()), song
            .getNotes()));
  }

  @Test
  public void testDeleteNote() throws Exception {
    Song song;

    song = song();
    song.addNote(noteEx.b3_0_4());
    song.addNote(noteEx.c4_0_4());
    assertTrue(sameNotes(Arrays.asList(noteEx.b3_0_4(), noteEx.c4_0_4()), song.getNotes()));
    song.deleteNote(noteEx.b3_0_4());
    assertTrue(sameNotes(Arrays.asList(noteEx.c4_0_4()), song.getNotes()));
    song.deleteNote(noteEx.c4_0_4());
    assertTrue(sameNotes(Arrays.asList(), song.getNotes()));

    song = song();
    song.addNote(noteEx.b3_0_4());
    song.addNote(noteEx.c4_0_4());
    song.addNote(noteEx.c5_0_4());
    song.addNote(noteEx.g9_0_4());
    assertTrue(sameNotes(Arrays.asList(noteEx.b3_0_4(), noteEx.c4_0_4(), noteEx.c5_0_4(), noteEx
            .g9_0_4()), song.getNotes()));
    song.deleteNote(noteEx.b3_0_4());
    song.deleteNote(noteEx.c4_0_4());
    song.deleteNote(noteEx.c5_0_4());
    song.deleteNote(noteEx.g9_0_4());
    assertTrue(sameNotes(Arrays.asList(), song.getNotes()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteNoteNotExist() throws Exception {
    Song song;

    song = song();
    song.deleteNote(noteEx.c4_0_4());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteNoteNotExist1() throws Exception {
    Song song;

    song = song();
    song.addNote(noteEx.c5_0_4());
    song.deleteNote(noteEx.c4_0_4());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteNoteNotExist2() throws Exception {
    Song song;

    song = song();
    song.addNote(noteEx.c4_0_4());
    song.deleteNote(noteEx.c4_0_4());
    song.deleteNote(noteEx.c4_0_4());
  }

  @Test
  public void testEditNote() throws Exception {
    Song song;
    Song songExpected;

    song = song();
    songExpected = song();

    song.addNote(noteEx.c4_0_4());
    song.editNote(noteEx.c4_0_4(), noteEx.cS4_0_4());

    songExpected.addNote(noteEx.cS4_0_4());
    assertTrue(sameNotes(songExpected.getNotes(), song.getNotes()));

    song = song();
    songExpected = song();

    song.addNote(noteEx.c5_0_4());
    song.addNote(noteEx.b3_0_4());
    song.editNote(noteEx.c5_0_4(), noteEx.b4_0_4());

    songExpected.addNote(noteEx.b3_0_4());
    songExpected.addNote(noteEx.b4_0_4());
    assertTrue(sameNotes(songExpected.getNotes(), song.getNotes()));
  }

  @Test
  public void testSetEndingBeatAndDefault() throws Exception {
    Song song;

    song = song();
    assertEquals(0, song.getEndingBeat());
    song.setEndingBeat(4);
    assertEquals(4, song.getEndingBeat());
    song.setEndingBeat(3);
    assertEquals(3, song.getEndingBeat());
    song.setDefaultEndingBeat();
    assertEquals(0, song.getEndingBeat());

    song = song();
    song.addNote(noteEx.c4_0_4());
    assertEquals(4, song.getEndingBeat());
    song.setEndingBeat(6);
    assertEquals(6, song.getEndingBeat());
    song.setDefaultEndingBeat();
    assertEquals(4, song.getEndingBeat());
    song.deleteNote(noteEx.c4_0_4());
    assertEquals(0, song.getEndingBeat());

    song = song();
    song.addNote(noteEx.c4_0_4());
    assertEquals(4, song.getEndingBeat());
    song.addNote(noteEx.c4_2_4());
    assertEquals(6, song.getEndingBeat());
    song.addNote(noteEx.c4_4_4());
    assertEquals(8, song.getEndingBeat());
    song.addNote(noteEx.c4_3_4());
    assertEquals(8, song.getEndingBeat());
    song.deleteNote(noteEx.c4_4_4());
    assertEquals(7, song.getEndingBeat());
    song.deleteNote(noteEx.c4_2_4());
    assertEquals(7, song.getEndingBeat());
    song.deleteNote(noteEx.c4_3_4());
    assertEquals(4, song.getEndingBeat());
  }

  @Test
  public void testAppendSong() throws Exception {
    Song song = song();
    Song songToAppend = song();
    songToAppend.addNote(noteEx.c4_0_4());

    Song newSong1 = song.appendSong(songToAppend, 0);
    Song newSong2 = songToAppend.appendSong(song, 0);
    assertTrue(sameNotes(newSong1.getNotes(), noteEx.c4_0_4().getNotes()));
    assertTrue(sameNotes(newSong2.getNotes(), noteEx.c4_0_4().getNotes()));
  }

  @Test
  public void testAddSongAt() throws Exception {
    Song song = song();
    Song songToAppend = song();
    songToAppend.addNote(noteEx.c4_0_4());
    Song songToAppend2 = song();
    songToAppend2.addNote(noteEx.b3_0_4());

    Song song2 = song();
    song2.addNote(noteEx.b3_0_4());
    song2.addNote(noteEx.c4_0_4());

    Song newSong1 = song.addSongAt(songToAppend, 0);
    Song newSong2 = songToAppend.addSongAt(song, 0);
    Song newSong3 = songToAppend.addSongAt(songToAppend2, 0);
    assertTrue(sameNotes(newSong1.getNotes(), Arrays.asList(noteEx.c4_0_4())));
    assertTrue(sameNotes(newSong2.getNotes(), noteEx.c4_0_4().getNotes()));
    assertTrue(sameNotes(newSong3.getNotes(), song2.getNotes()));
  }

  /**
   * Checks if sets contain all of the same notes (disregarding order and duplication).
   */
  private boolean sameNotes(Collection<Note> set1, Collection<Note> set2) {
    // check every note in set1 is in set2
    for (Note n1 : set1) {
      if (!set2.contains(n1)) {
        return false;
      }
    }

    // same check that every note in set2 is in set1
    for (Note n2 : set2) {
      if (!set1.contains(n2)) {
        return false;
      }
    }
    // if no elements were missing matches in the other list, lists must have the same items
    return true;
  }

  /**
   * Constructs an instance of a Song
   */
  protected abstract Song song();
}