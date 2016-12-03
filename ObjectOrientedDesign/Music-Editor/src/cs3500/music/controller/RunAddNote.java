package cs3500.music.controller;

import cs3500.music.model.Note;
import cs3500.music.model.Song;

/**
 * Created by Anish on 11/24/15.
 */
public class RunAddNote implements Runnable {

  private final Song song;
  private Note noteToAdd;

  public RunAddNote(Song song, Note noteToAdd) {
    this.song = song;
    this.noteToAdd = noteToAdd;
  }

  @Override
  public void run() {
      this.song.addNote(noteToAdd);
  }
}
