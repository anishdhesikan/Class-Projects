package cs3500.music.test;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import cs3500.music.model.Note;
import cs3500.music.model.NoteImpl;
import cs3500.music.model.PitchImpl;
import cs3500.music.model.Playable;
import cs3500.music.model.SongImpl;
import cs3500.music.view.midi.MidiView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the MidiView interface
 */
public class MidiViewTest {
  MockMidiView mockView;
  private Collection<String> mockOut;
  ExamplesSongs songEx;

  public MidiViewTest() {
    this.songEx = new ExamplesSongs(SongImpl::builder,
            new NoteImpl.Factory(), new PitchImpl.Factory());
  }

  @Before
  public void setUp() {
    mockOut = new HashSet<>();
    mockView = new MockMidiView(mockOut);
  }

  @Test
  public void testMidiSongMidC() throws Exception {
    mockView.loadPlayable(songEx.midC());
    mockView.renderWhole();

    Collection<String> expected = new HashSet<>();
    expected.add("ON, 1, 60, 100; 0\n");
    expected.add("OFF, 1, 60, 100; 2000000\n");
    assertSameStrings(expected, mockOut);
  }

  @Test
  public void testMidiSongRepeatedC() throws Exception {
    mockView.loadPlayable(songEx.repeatedC());
    mockView.renderWhole();

    Collection<String> expected = new HashSet<>();
    expected.add("ON, 1, 60, 100; 0\n");
    expected.add("OFF, 1, 60, 100; 2000000\n");
    expected.add("ON, 1, 60, 100; 1000000\n");
    expected.add("OFF, 1, 60, 100; 3000000\n");
    expected.add("ON, 1, 60, 100; 1500000\n");
    expected.add("OFF, 1, 60, 100; 3500000\n");
    expected.add("ON, 1, 60, 100; 2000000\n");
    expected.add("OFF, 1, 60, 100; 4000000\n");
    assertSameStrings(expected, mockOut);
  }

  @Test
  public void testMidiSongSpreadC() throws Exception {
    mockView.loadPlayable(songEx.spreadC());
    mockView.renderWhole();

    Collection<String> expected = new HashSet<>();
    expected.add("ON, 1, 60, 100; 0\n");
    expected.add("OFF, 1, 60, 100; 2000000\n");
    expected.add("ON, 1, 60, 100; 10000000\n");
    expected.add("OFF, 1, 60, 100; 12000000\n");
    assertSameStrings(expected, mockOut);
  }

  @Test
  public void testMidiSongChordCDB4() throws Exception {
    mockView.loadPlayable(songEx.chordCDB4());
    mockView.renderWhole();

    Collection<String> expected = new HashSet<>();
    expected.add("ON, 1, 60, 100; 0\n");
    expected.add("OFF, 1, 60, 100; 2000000\n");
    expected.add("ON, 1, 62, 100; 0\n");
    expected.add("OFF, 1, 62, 100; 2000000\n");
    expected.add("ON, 1, 71, 100; 0\n");
    expected.add("OFF, 1, 71, 100; 2000000\n");
    assertSameStrings(expected, mockOut);
  }

  /**
   * Asserts that all strings in expected are in actual and vice versa. If not, fails the assertion
   * with a message detailing what was missing.
   *
   * @param expected the expected strings
   * @param actual the actual strings
   */
  private static void assertSameStrings(Collection<String> expected, Collection<String> actual) {
    List<String> suprise = new LinkedList<>();
    List<String> missing = new LinkedList<>();
    boolean sameStrings = true;
    for(String e : expected) {
      if(!actual.contains(e)) {
        sameStrings = false;
        missing.add(e);
      }
    }
    for(String a : actual) {
      if(!expected.contains(a)) {
        sameStrings = false;
        suprise.add(a);
      }
    }
    String message = "Extra Actual Commands: \n";
    for(String s : suprise) {
      message += s;
    }
    message += "\nExtra Actual Commands: Expected Commands: \n";
    for(String s : missing) {
      message += s;
    }

    assertTrue(message, sameStrings);
  }

  /**
   * A Mock Midi view class to test the midi view.
   */
  private static class MockMidiView implements MidiView {
    private Playable playable;
    private final Collection<String> mockOut;

    private MockMidiView(Collection<String> mockOut) {
      this.mockOut = mockOut;
    }

    @Override
    public void loadPlayable(Playable playable) {
      this.playable = playable;
    }

    @Override
    public void renderWhole() {
      // TODO
      Collection<Note> notesToPlay = playable.getNotes();

      int beatLength = 60000000 / playable.getTempoBPM(); // in microseconds

      for (Note noteToPlay: notesToPlay) {
        String startMessage = "ON, " +
                noteToPlay.getInstrument() + ", " +
                noteToPlay.getPitch().getMidiNumber() + ", " +
                noteToPlay.getVolume();
        String endMessage = "OFF, " +
                noteToPlay.getInstrument() + ", " +
                noteToPlay.getPitch().getMidiNumber() + ", " +
                noteToPlay.getVolume();
        int startTime = noteToPlay.getStartingBeat() * beatLength;
        int endTime = noteToPlay.getEndingBeat() * beatLength;
        this.mockOut.add(startMessage + "; " + startTime + "\n");
        this.mockOut.add(endMessage + "; " + endTime + "\n");
      }
    }

    @Override
    public void renderBeat(int beat) {
      // TODO
    }

    @Override
    public void closeReceiver() {
      // Do Nothing
    }
  }
}
