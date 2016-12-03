package cs3500.music;

import cs3500.music.model.NoteImpl;
import cs3500.music.model.PitchClass;
import cs3500.music.model.PitchImpl;
import cs3500.music.model.Playable;
import cs3500.music.model.Song;
import cs3500.music.model.SongImpl;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ViewFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  private static Playable playable;
  private Song testSong;

  // runs based on command line arguments (set in run configurations)
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    String fileName = "mary-little-lamb.txt";
    String viewType = "console";
    if (args.length >= 2) {
      fileName = args[0];
      viewType = args[1];
    }

    Readable textReader = new StringReader("");
    try {
      textReader = new FileReader(new File(fileName));
    } catch (IOException e) {
      e.printStackTrace();
    }

    CompositionBuilder<Song> readSongBuilder = SongImpl.compositionBuilder();
    Song readSong = MusicReader.parseFile(textReader, readSongBuilder);

    playable = readSong;
    ViewFactory viewFactory = new ViewFactory();
    viewFactory.startView(viewType);
  }

  /**
   * returns the Playable of the current music editor to be used in a view
   */
  public static Playable getPlayable() {
    return playable;
  }

  // Boston by Augustana in a testSong. For testing purposes only.
  private void initializeTestSong() {
    testSong = new SongImpl();
    testSong.setTempoBPM(120);
    NoteImpl.Factory noteFactory = new NoteImpl.Factory();
    PitchImpl.Factory pitchFactory = new PitchImpl.Factory();

    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.C, 4), 0, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 4), 2, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.C, 4), 3, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 4), 5, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.G, 4), 6, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 4), 8, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.C, 4), 9, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 4), 11, 8, 100, 1));


    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.C, 3), 0, 20, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.G, 3), 0, 20, 100, 1));


    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.B, 3), 12, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 4), 14, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.B, 3), 15, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 4), 17, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.G, 4), 18, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 4), 20, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.B, 3), 21, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 4), 23, 8, 100, 1));


    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.B, 2), 12, 20, 100,
            1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.G, 3), 12, 20, 100,
            1));

    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.C, 4), 24, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 4), 26, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.C, 4), 27, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 4), 29, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.G, 4), 30, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 4), 32, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.C, 4), 33, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.B, 4), 36, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 4), 39, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.C, 4), 41, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.B, 4), 42, 8, 100, 1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.C, 5), 44, 8, 100, 1));


    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.A, 2), 24, 20, 127,
            1));
    testSong.addNote(noteFactory.makeNote(pitchFactory.makePitch(PitchClass.E, 3), 24, 20, 127,
            1));
  }
}
