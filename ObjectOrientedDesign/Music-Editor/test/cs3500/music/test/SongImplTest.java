package cs3500.music.test;

import cs3500.music.model.NoteImpl;
import cs3500.music.model.PitchImpl;
import cs3500.music.model.Song;
import cs3500.music.model.SongImpl;
import cs3500.music.test.AbstractSongTest;
import cs3500.music.util.CompositionBuilder;

import static org.junit.Assert.*;

/**
 * Created by Kevin McDonough on 11/12/2015.
 */
public class SongImplTest extends AbstractSongTest {

  public SongImplTest() {
    super(new NoteImpl.Factory(), new PitchImpl.Factory());
  }

  @Override
  protected Song song() {
    return new SongImpl();
  }
}