package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;

import cs3500.music.model.AlternateEnding;
import cs3500.music.model.BasicRepeat;
import cs3500.music.model.Note;
import cs3500.music.model.NoteFactory;
import cs3500.music.model.NoteImpl;
import cs3500.music.model.Pitch;
import cs3500.music.model.Repeat;
import cs3500.music.model.Song;
import cs3500.music.view.composite.CompositeView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

/**
 * Controller for a composite music editor view, both gui and midi
 */
public class CompositeController {
  private int beat;
  private final CompositeView view;
  private final Song song;
  // Repeats that have already played through once
  private Collection<Repeat> usedRepeats;
  private Timeline timeline;
  private boolean playing;

  // related to selecting a cell
  public boolean cellIsSelected;
  public int selectedBeat;
  public Pitch selectedPitch;

  // Where does the repeat to create begin/end/repeat to? -1 if not yet set.
  private int repeatBeginning;
  private int repeatEnd;
  private int repeatBeatToRepeatTo;

  public CompositeController(CompositeView view,Song song) {
    this.view = view;
    this.song = song;
    this.usedRepeats = new ArrayList<Repeat>();
    this.timeline = new Timeline();
    this.repeatBeginning = -1;
    this.repeatEnd = -1;
  }

  public void run() {
    int width = song.getEndingBeat();

    view.loadPlayable(song);

    // Install a selectCell mouseListener on each cell in the song
    for (int beatNum = 0; beatNum < width; beatNum++) {
      for (Pitch curPitch = song.getLowestPitch();
           curPitch.compareTo(song.getHighestPitch()) <= 0;
           curPitch = curPitch.nextPitch()) {

        Runnable selectCellOnClick = new SelectCellRunnable(this, beatNum, curPitch);
        Runnable moveOnRightClick = new MoveNoteRunnable(this, beatNum, curPitch);

        MouseHandler mouseListener = new MouseHandler();
        mouseListener.installOnMouseClicked(MouseButton.PRIMARY, selectCellOnClick);
        mouseListener.installOnMouseClicked(MouseButton.SECONDARY, moveOnRightClick);

        view.addMouseListener(mouseListener, beatNum, curPitch);
      }
    }


    Runnable deleteOnR = new DeleteNoteRunnable(this);
    KeyboardHandler keyboardHandler = new KeyboardHandler();
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_R, deleteOnR);
    // The + 5 here is because the adapter for keyhandler is not perfect for non-letters
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_1 + 5, new AddNoteRunnable(this, 1));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_2 + 5, new AddNoteRunnable(this, 2));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_3 + 5, new AddNoteRunnable(this, 3));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_4 + 5, new AddNoteRunnable(this, 4));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_5 + 5, new AddNoteRunnable(this, 5));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_6 + 5, new AddNoteRunnable(this, 6));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_7 + 5, new AddNoteRunnable(this, 7));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_8 + 5, new AddNoteRunnable(this, 8));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_9 + 5, new AddNoteRunnable(this, 9));

    keyboardHandler.installOnKeyPressed(KeyEvent.VK_P, new PlayPauseRunnable(this));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_B, new ToBeginning(this));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_N, new MoveLeft(this));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_M, new MoveRight(this));

    keyboardHandler.installOnKeyPressed(KeyEvent.VK_G, new SetRepeatBeatToRepeatTo(this));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_H, new SetRepeatBeginning(this));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_J, new SetRepeatEnd(this));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_K, new CreateBasicRepeat(this));
    keyboardHandler.installOnKeyPressed(KeyEvent.VK_L, new CreateAlternateEnding(this));
    view.addKeyListener(keyboardHandler);

    this.beat = 0;
  }

  private void playBeat(int beat) {
    this.view.renderBeat(beat);
    this.beat = this.song.getNextBeat(this.beat, usedRepeats);
    usedRepeats.addAll(this.song.getRepeatsRepeatingAtBeat(beat));
  }

  private void drawBeat(int beat) {
    this.view.setBeatMarker(beat);
  }

  public void removeNote(Note noteToRemove) {
    song.deleteNote(noteToRemove);
    view.updateViewAt(noteToRemove.getStartingBeat(), noteToRemove.getEndingBeat());
  }

  public void addNote(Note noteToAdd) {
    song.addNote(noteToAdd);
    view.updateViewAt(noteToAdd.getStartingBeat(), noteToAdd.getEndingBeat());
  }

  public void moveNote(Note noteToMove, int beatToMoveTo, Pitch pitchToChangeTo) {
    NoteFactory factory = new NoteImpl.Factory();
    Note newNote = factory.makeNote(pitchToChangeTo, beatToMoveTo, noteToMove.getDuration(),
            noteToMove.getVolume(), noteToMove.getInstrument());
    addNote(newNote);
    removeNote(noteToMove);
  }

  public boolean noteExistsAt(int beatNumber, Pitch pitch) {
    Collection<Note> notesAtBeat = song.getNotesStartingAtBeat(beatNumber);
    for (Note note: notesAtBeat) {
      if (note.getPitch().compareTo(pitch) == 0) {
        return true;
      }
    }
    return false;
  }

  public Note getNoteAt(int beatNumber, Pitch pitch) {
    Collection<Note> notesAtBeat = song.getNotesStartingAtBeat(beatNumber);
    for (Note note: notesAtBeat) {
      if (note.getPitch().compareTo(pitch) == 0) {
        return note;
      }
    }
    throw new IllegalArgumentException("No such note found");
  }

  public void UpdateViewAt(int beatNumber, Pitch pitch) {
    Note updatedNote = getNoteAt(beatNumber, pitch);
    if(updatedNote != null) {
      view.updateViewAt(updatedNote.getStartingBeat(), updatedNote.getEndingBeat());
    }
  }

  void play() {
    playing = true;
//    timeline.getKeyFrames().clear();
//    for(int i = beat; i < song.getEndingBeat(); i++) {
//      final int curBeat = i;
//      final int beatTime = (curBeat - this.beat) * 60000 / song.getTempoBPM();
//      timeline.getKeyFrames().add(new KeyFrame(
//              Duration.millis(beatTime),
//              ae -> this.playBeat(curBeat)));
//    }
//    timeline.play();

    this.timeline = new Timeline(new KeyFrame(
            Duration.millis(60000 / song.getTempoBPM()),
            ae -> playBeat(this.beat)));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  void pause() {
    if(playing) {
      playing = false;
      timeline.stop();
    }
  }

  public void togglePlayPause() {
    if(playing) {
      this.pause();
    } else {
      this.play();
    }
  }

  private void moveMarkerTo(int beat) {
    if(!playing) {
      this.beat = beat;
      this.drawBeat(this.beat);
    }
  }

  public void toBeginning() {
    moveMarkerTo(0);
    this.usedRepeats.clear();
  }

  private class ToBeginning implements Runnable {
    CompositeController controller;
    ToBeginning(CompositeController controller) {
      this.controller = controller;
    }

    @Override
    public void run() {
      controller.toBeginning();
    }
  }

  public void moveLeft() {
    moveMarkerTo(this.beat - 1);
  }

  private class MoveLeft implements Runnable {
    CompositeController controller;
    MoveLeft(CompositeController controller) {
      this.controller = controller;
    }

    @Override
    public void run() {
      controller.moveLeft();
    }
  }

  public void moveRight() {
    moveMarkerTo(this.beat + 1);
  }

  private class MoveRight implements Runnable {
    CompositeController controller;
    MoveRight(CompositeController controller) {
      this.controller = controller;
    }

    @Override
    public void run() {
      controller.moveRight();
    }
  }

  public void setRepeatBeginning() {
    if (this.cellIsSelected) {
      this.cellIsSelected = false;
      this.repeatBeginning = this.selectedBeat;
//      Repeat newRepeat = new BasicRepeat(SelectCellRunnable.selectedBeat,
//              SelectCellRunnable.selectedBeat + 5);
//      this.song.addRepeat(newRepeat);
      System.out.println("Set beginning of repeat to beat " + repeatBeginning);
    } else {
      System.out.println("No cell selected");
    }
  }

  private class SetRepeatBeginning implements Runnable {
    CompositeController controller;
    SetRepeatBeginning(CompositeController controller) {
      this.controller = controller;
    }

    @Override
    public void run() {
      controller.setRepeatBeginning();
    }
  }


  public void setRepeatEnd() {
    if (this.cellIsSelected) {
      this.cellIsSelected = false;
      this.repeatEnd = this.selectedBeat;
      System.out.println("Set end of repeat to beat " + repeatEnd);
    } else {
      System.out.println("No cell selected");
    }
  }

  private class SetRepeatEnd implements Runnable {
    CompositeController controller;
    SetRepeatEnd(CompositeController controller) {
      this.controller = controller;
    }

    @Override
    public void run() {
      controller.setRepeatEnd();
    }
  }

  public void setRepeatBeatToRepeatTo() {
    if (this.cellIsSelected) {
      this.cellIsSelected = false;
      this.repeatBeatToRepeatTo = this.selectedBeat;
      System.out.println("Set beat to repeat to of repeat to beat " + repeatBeatToRepeatTo);
    } else {
      System.out.println("No cell selected");
    }
  }

  private class SetRepeatBeatToRepeatTo implements Runnable {
    CompositeController controller;
    SetRepeatBeatToRepeatTo(CompositeController controller) {
      this.controller = controller;
    }

    @Override
    public void run() {
      controller.setRepeatBeatToRepeatTo();
    }
  }

  public void createBasicRepeat() {
    if (this.repeatBeginning >= 0 && this.repeatEnd >= 0) {
      Repeat newRepeat = new BasicRepeat(this.repeatBeginning, this.repeatEnd);
      this.song.addRepeat(newRepeat);
      System.out.println("Created basic repeat: " +
              this.repeatBeginning + "-" + this.repeatEnd);

      this.repeatBeginning = -1;
      this.repeatEnd = -1;
    } else {
      System.out.println("Cannot create repeat. Beginning or end beat not set.");
    }
    this.view.updateViewAt(0, 0);
  }

  private class CreateBasicRepeat implements Runnable {
    CompositeController controller;
    CreateBasicRepeat(CompositeController controller) {
      this.controller = controller;
    }

    @Override
    public void run() {
      controller.createBasicRepeat();
    }
  }

  public void createAlternateEnding() {
    if (this.repeatBeatToRepeatTo >= 0 && this.repeatBeginning >= 0 && this.repeatEnd >= 0) {
      Repeat newRepeat = new AlternateEnding(this.repeatBeatToRepeatTo, this.repeatBeginning,
              this.repeatEnd);
      this.song.addRepeat(newRepeat);
      System.out.println("Created alternate ending: " +
              this.repeatBeatToRepeatTo + "-" + this.repeatBeginning + "-" + this.repeatEnd);

      this.repeatBeginning = -1;
      this.repeatEnd = -1;
      this.view.updateViewAt(0, 0);
    } else {
      System.out.println("Cannot create repeat. Beat to repeat or beginning or end beat not set.");
    }
  }

  private class CreateAlternateEnding implements Runnable {
    CompositeController controller;
    CreateAlternateEnding(CompositeController controller) {
      this.controller = controller;
    }

    @Override
    public void run() {
      controller.createAlternateEnding();
    }
  }
}
