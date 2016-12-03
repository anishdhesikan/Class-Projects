package cs3500.music.test;

import java.util.function.Supplier;

import cs3500.music.model.NoteFactory;
import cs3500.music.model.PitchFactory;
import cs3500.music.model.Song;

/**
 * Created by Kevin McDonough on 11/13/2015.
 */
public class ExamplesSongs {
  private final Supplier<Song.Builder> songBuilderMaker;
  private final ExamplesNotes noteEx;

  ExamplesSongs(Supplier<Song.Builder> songBuilderMaker, NoteFactory noteFactory,
                PitchFactory pitchFactory) {
    this.songBuilderMaker = songBuilderMaker;
    this.noteEx = new ExamplesNotes(noteFactory, pitchFactory);
  }

  private Song.Builder newSongBuilder() {
    return this.songBuilderMaker.get();
  }

  Song empty() {
    return this.newSongBuilder().build();
  }

  Song empty120bpm() {
    return this.newSongBuilder().setTempoBPM(120).build();
  }

  Song midC() {
    return this.newSongBuilder().addNote(noteEx.c4_0_4()).build();
  }

  Song repeatedC() {
    return this.newSongBuilder()
            .addNote(noteEx.c4_0_4())
            .addNote(noteEx.c4_2_4())
            .addNote(noteEx.c4_3_4())
            .addNote(noteEx.c4_4_4())
            .build();
  }

  Song multiOctaveC() {
    return this.newSongBuilder()
            .addNote(noteEx.cn1_0_4())
            .addNote(noteEx.c4_0_4())
            .addNote(noteEx.c5_0_4())
            .build();
  }

  Song spreadC() {
    return this.newSongBuilder()
            .addNote(noteEx.c4_0_4())
            .addNote(noteEx.lateC4())
            .build();
  }

  Song chordCDB4() {
    return this.newSongBuilder()
            .addNote(noteEx.c4_0_4())
            .addNote(noteEx.d4_0_4())
            .addNote(noteEx.b4_0_4())
            .build();
  }
}
