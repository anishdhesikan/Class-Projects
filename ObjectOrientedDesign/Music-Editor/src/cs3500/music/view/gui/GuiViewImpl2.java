package cs3500.music.view.gui;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.Playable;
import cs3500.music.controller.FXKeyListener;
import cs3500.music.controller.FXMouseListener;
import cs3500.music.model.Repeat;
import cs3500.music.model.Song;
import javafx.geometry.Insets;
import cs3500.music.controller.KeyboardHandlerAdapter;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * An GuiView Implementation making use of JavaFX's GridPane
 */
public class GuiViewImpl2 implements GuiView {
  Playable playable;
  private final Pane root;
  private GridPane noteGrid;
  private GridPane markerGrid;
  private CellView[][] cells;
  private StackPane[][] markers;
  private int currBeatMarker;
  private Pitch lowestPitch;
  private Pitch highestPitch;

  public GuiViewImpl2() {
    root = new Pane();
  }

  @Override
  public void renderWhole() {
    renderBeat(0);
  }

  @Override
  public void renderBeat(int beat) {
    this.setBeatMarker(beat);
  }

  @Override
  public void loadPlayable(Playable playable) {
    this.playable = playable;
    this.currBeatMarker = 0;
    this.lowestPitch = playable.getLowestPitch();
    this.highestPitch = playable.getHighestPitch();

    while (root.getChildren().size() > 0) {
      root.getChildren().remove(0);
    }
    HBox hb = new HBox();
    root.getChildren().add(hb);

    hb.setBackground(Constants.GRID_BACKGROUND);
    hb.setBorder(Constants.GRID_BORDER);

    Pitch lowestPitch = this.playable.getLowestPitch();
    Pitch highestPitch = this.playable.getHighestPitch();
    if (lowestPitch == null || highestPitch == null) {
      throw new NullPointerException("Playable must have extreme pitches defined");
    }
    // max number of beats shown
    int numBeats = this.playable.getEndingBeat();
    // max number of pitches shown
    int numPitches = highestPitch.getMidiNumber() - lowestPitch.getMidiNumber() + 1;
    setupNewGrid(numBeats, numPitches);
    updateGridStates();

    StackPane combinedGrid = new StackPane(noteGrid, markerGrid);

    GridPane pitchPane = new GridPane();
    pitchPane.add(new Label(), 0, 0);
    pitchPane.addColumn(0, getPitchLabels(lowestPitch, highestPitch));
    pitchPane.getRowConstraints().addAll(noteGrid.getRowConstraints());

    ScrollPane scrollGrid = new ScrollPane();
    scrollGrid.setBackground(Constants.GRID_BACKGROUND);
    scrollGrid.setContent(combinedGrid);
    scrollGrid.setMaxWidth(800);
    scrollGrid.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

    hb.getChildren().add(pitchPane);
    hb.getChildren().add(scrollGrid);

    int beatInterval = 4;
    Node[] beatLabels = getBeatLabels(0, numBeats, beatInterval);
    for (int i = 0; i < beatLabels.length; i++) {
      noteGrid.add(beatLabels[i], i, 0, beatInterval, 1);
    }
  }

  /**
   * Gets a list of all of the Pitch labels on the given range
   */
  private static Label[] getPitchLabels(Pitch lowestPitch, Pitch highestPitch) {
    List<Label> labelList = new ArrayList<>();
    // Add all the pitch labels onto the left column of grid
    Pitch curPitch = lowestPitch;
    while (curPitch.compareTo(highestPitch) <= 0) {
      Label label = new Label(curPitch.toString());
      label.setFont(Font.font(Constants.CELL_SIZE * 0.7));

      labelList.add(label);

      curPitch = curPitch.nextPitch();
    }

    Label[] labelArray = new Label[labelList.size()];
    for (int i = 0; i < labelList.size(); i++) {
      labelArray[i] = labelList.get(labelList.size() - i - 1);
    }

    return labelArray;
  }

  /**
   * Get beat labels
   */
  private static Node[] getBeatLabels(int startBeat, int endBeat, int interval) {
    Node[] labelList = new Node[endBeat - startBeat];
    for (int i = 0; i < labelList.length; i++) {
      int beat = i + startBeat;
      Node root;
      if (beat % interval == 0) {
        HBox base = new HBox();

        Label text;
        text = new Label(Integer.toString(beat));
        text.setFont(Font.font(Constants.CELL_SIZE * 0.7));
        text.setPadding(new Insets(0, 0, 0, 5));

        LineMarker marker = new LineMarker();
        marker.setColor(Color.BLACK);
        marker.setStrokeWidth(.5);

        base.getChildren().addAll(marker.root(), text);
        root = base;
      } else {
        root = new Label();
      }
      labelList[i] = root;
    }
    return labelList;
  }

  /**
   * Creates and sets up a new {@code GridPane} for the GUI
   *
   * @return the new GridPane, already set up
   */
  private void setupNewGrid(int width, int height) {
    width += 1;
    noteGrid = new GridPane();
    noteGrid.setHgap(0);
    noteGrid.setVgap(0);
    noteGrid.setBackground(Constants.GRID_BACKGROUND);

    List<ColumnConstraints> colConstraints = noteGrid.getColumnConstraints();
    for (int i = 0; i < width; i++) {
      colConstraints.add(new ColumnConstraints(Constants.CELL_SIZE));
    }

    List<RowConstraints> rowConstraints = noteGrid.getRowConstraints();
    // making space for label row
    rowConstraints.add(new RowConstraints(Constants.CELL_SIZE));
    for (int i = 0; i < height; i++) {
      rowConstraints.add(new RowConstraints(Constants.CELL_SIZE));
    }

    markerGrid = new GridPane();
    markerGrid.setHgap(0);
    markerGrid.setVgap(0);
    markerGrid.setMouseTransparent(true);

    markerGrid.getRowConstraints().addAll(rowConstraints);
    markerGrid.getColumnConstraints().addAll(colConstraints);

    this.cells = emptyCellGrid(width, height);
    this.markers = emptyStackPaneGrid(width, height);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        noteGrid.add(cells[i][j].root(), i, j + 1);
        markerGrid.add(markers[i][j], i, j + 1);
      }
    }
    updateAllMarkers();
  }

  @Override
  public void updateViewAt(int startBeat, int endBeat) {
    //updateGridStates(note.getStartingBeat(), note.getEndingBeat());
    updateGridStates();
  }

  private void updateGridStates() {
    updateGridStates(0, cells.length);
  }

  /**
   * Updates all the visual states on the grid to match the notes of the playable field
   */
  private void updateGridStates(int startBeat, int endBeat) {
    int maxPitchNum = playable.getHighestPitch().getMidiNumber();
    endBeat = endBeat < cells.length ? endBeat : cells.length;

    for (int beat = startBeat; beat < endBeat; beat++) {
      for (int i = 0; i < cells[beat].length; i++) {
        cells[beat][i].setState(NoteState.Empty);
      }

      Collection<Note> contNotes = playable.getNotesContinuingAtBeat(beat);
      for (Note n : contNotes) {
        int pitchIndex = maxPitchNum - n.getPitch().getMidiNumber();
        cells[beat][pitchIndex].setState(NoteState.Continuing);
      }

      Collection<Note> startNotes = playable.getNotesStartingAtBeat(beat);
      for (Note n : startNotes) {
        int pitchIndex = maxPitchNum - n.getPitch().getMidiNumber();
        cells[beat][pitchIndex].setState(NoteState.Starting);
      }
    }
  }

  private void updateAllMarkers() {
    clearAllBeatMarkers();
    showRepeats();
    addBeatMarker(currBeatMarker, Constants.BEAT_MARKER_COLOR, Constants.BEAT_MARKER_WIDTH);
  }

  /**
   * Updates the repeats of the song to match the playable (if it's a song)
   */
  private void showRepeats() {
    if (!(playable instanceof Song)) {
      return;
    }
    Song song = (Song) playable;

    int startBeat = 0;
    int endBeat = markers.length;

    for (int beat = startBeat; beat < endBeat + 1; beat++) {
      for(Repeat repeat : song.getRepeatsStartingAtBeat(beat)) {
        addBeatMarker(repeat.getStartingBeat(), Color.VIOLET, 2);
        addBeatMarker(repeat.getBeatToRepeatTo(), Color.GREEN, 3);
        addBeatMarker(repeat.getRepeatingBeat(), Color.BLUE, 4);
      }
    }
  }

  @Override
  public void setBeatMarker(int beat) {
    currBeatMarker = beat;
    updateAllMarkers();
  }

  private void clearAllBeatMarkers() {
    for(int i = 0; i < markers.length; i++) {
      clearBeatMarkers(i);
    }
  }

  /**
   * Removes all markers at given beat
   */
  private void clearBeatMarkers(int beat) {
    if (beat >= 0 && beat < markers.length) {
      for (int i = 0; i < markers[beat].length; i++) {
        markers[beat][i].getChildren().clear();
      }
    }
  }

  /**
   * Adds a beat marker to all grid markers at given beat
   */
  private void addBeatMarker(int beat, Color color, int width) {
    if (beat >= 0 && beat < markers.length) {
      for (int i = 0; i < markers[beat].length; i++) {
        LineMarker beatMarker = new LineMarker(color, width);
        markers[beat][i].getChildren().add(beatMarker.root());
      }
    }
  }

  /**
   * Creates a width by height grid of CellViews, all initialized to empty
   *
   * @param width  width (first dimension) of grid
   * @param height height (second dimension) of grid
   * @return a grid of NoteState.Empty objects.
   */
  private static CellView[][] emptyCellGrid(int width, int height) {
    CellView[][] blankGrid = new CellView[width][height];
    for (int x = 0; x < blankGrid.length; x++) {
      for (int y = 0; y < blankGrid[x].length; y++) {
        blankGrid[x][y] = new CellView();
      }
    }
    return blankGrid;
  }

  /**
   * Creates a width by height grid of CellViews, all initialized to empty
   *
   * @param width  width (first dimension) of grid
   * @param height height (second dimension) of grid
   * @return a grid of NoteState.Empty objects.
   */
  private static StackPane[][] emptyStackPaneGrid(int width, int height) {
    StackPane[][] blankGrid = new StackPane[width][height];
    for (int x = 0; x < blankGrid.length; x++) {
      for (int y = 0; y < blankGrid[x].length; y++) {
        blankGrid[x][y] = new StackPane();
      }
    }
    return blankGrid;
  }

  /**
   * Gets the root {@code Node} of this view
   *
   * @return the root Node of htis view
   */
  public Node root() {
    return root;
  }

  /**
   * Get the width of the CellView grid (in cells)
   */
  public int getWidth() {
    return cells.length;
  }

  /**
   * Get the height of the CellView grid (in cells)
   */
  public int getHeight() {
    if (cells.length > 0) {
      return cells[0].length;
    } else {
      return 0;
    }
  }

  /**
   * Gets the cell at the given beatNumber and pitch
   */
  public CellView getCellAt(int beatNumber, Pitch pitch) {
    int pitchOffset = highestPitch.getMidiNumber() - pitch.getMidiNumber();
    if (pitchOffset >= 0) {
      return cells[beatNumber][pitchOffset];
    } else {
      throw new IllegalArgumentException("Given pitch not valid in this song");
    }
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    FXKeyListener keyboardHandlerAdapter =
            new KeyboardHandlerAdapter((KeyboardHandler) keyListener);
    root.setOnKeyPressed(keyEvent -> {
      keyboardHandlerAdapter.keyPressed(keyEvent);
    });
    root.setOnKeyTyped(keyEvent -> {
      keyboardHandlerAdapter.keyTyped(keyEvent);
    });
    root.setOnKeyReleased(keyEvent -> {
      keyboardHandlerAdapter.keyReleased(keyEvent);
    });
  }

  @Override
  public void addMouseListener(FXMouseListener mouseListener, int beatNumber, Pitch pitch) {
    Node cell = getCellAt(beatNumber, pitch).root();

    cell.setOnMouseClicked(mouseEvent -> {
      mouseListener.mouseClicked(mouseEvent);
    });

    cell.setOnMousePressed(mouseEvent -> {
      mouseListener.mousePressed(mouseEvent);
    });

    cell.setOnMouseReleased(mouseEvent -> {
      mouseListener.mouseReleased(mouseEvent);
    });

    cell.setOnMouseEntered(mouseEvent -> {
      mouseListener.mouseEntered(mouseEvent);
    });

    cell.setOnMouseExited(mouseEvent -> {
      mouseListener.mouseExited(mouseEvent);
    });
  }
}
