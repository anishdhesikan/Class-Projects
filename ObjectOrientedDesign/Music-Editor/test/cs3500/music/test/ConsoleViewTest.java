package cs3500.music.test;

import org.junit.Before;
import org.junit.Test;

import cs3500.music.model.NoteImpl;
import cs3500.music.model.PitchImpl;
import cs3500.music.model.SongImpl;
import cs3500.music.view.console.ConsoleController;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kevin McDonough on 11/13/2015.
 */
public class ConsoleViewTest {
  StringBuilder mockOut;
  ExamplesSongs songEx;

  public ConsoleViewTest() {
    this.songEx = new ExamplesSongs(() -> SongImpl.builder(),
            new NoteImpl.Factory(), new PitchImpl.Factory());
  }

  @Before
  public void setUp() {
    this.mockOut = new StringBuilder();
  }

  @Test
  public void testConsoleSongMidC() {
    ConsoleController controller = new ConsoleController(songEx.midC(), mockOut);
    controller.run();

    StringBuilder expected = new StringBuilder();
    expected.append("   C4\n");
    expected.append("0   X\n");
    expected.append("1   |\n");
    expected.append("2   |\n");
    expected.append("3   |\n");
    assertEquals(expected.toString(), mockOut.toString());
  }

  @Test
  public void testConsoleSongRepeatedC() {
    ConsoleController controller = new ConsoleController(songEx.repeatedC(), mockOut);
    controller.run();

    StringBuilder expected = new StringBuilder();
    expected.append("   C4\n");
    expected.append("0   X\n");
    expected.append("1   |\n");
    expected.append("2   X\n");
    expected.append("3   X\n");
    expected.append("4   X\n");
    expected.append("5   |\n");
    expected.append("6   |\n");
    expected.append("7   |\n");
    assertEquals(expected.toString(), mockOut.toString());
  }

  @Test
  public void testConsoleSongSpreadC() {
    ConsoleController controller = new ConsoleController(songEx.spreadC(), mockOut);
    controller.run();

    StringBuilder expected = new StringBuilder();
    expected.append("    C4\n");
    // write first note
    expected.append(" 0   X\n");
    expected.append(" 1   |\n");
    expected.append(" 2   |\n");
    expected.append(" 3   |\n");
    // blank lines
    for(int i = 4; i < 20; i++) {
      expected.append(String.format("%2s", i) + "    \n");
    }
    // second note starting at beat 20
    expected.append("20   X\n");
    expected.append("21   |\n");
    expected.append("22   |\n");
    expected.append("23   |\n");
    assertEquals(expected.toString(), mockOut.toString());
  }

  @Test
  public void testConsoleSongMultiChordCDB4() {
    ConsoleController controller = new ConsoleController(songEx.chordCDB4(), mockOut);
    controller.run();

    StringBuilder expected = new StringBuilder();
    expected.append("   C4 C#4  D4 D#4  E4  F4 F#4  G4 G#4  A4 A#4  B4\n");
    expected.append("0   X       X                                   X\n");
    expected.append("1   |       |                                   |\n");
    expected.append("2   |       |                                   |\n");
    expected.append("3   |       |                                   |\n");
    assertEquals(expected.toString(), mockOut.toString());
  }
}
