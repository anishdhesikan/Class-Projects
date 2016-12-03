package cs3500.music.view.gui;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

final class Constants {
  private Constants() { } // prevents instantiation

  public static final Background GRID_BACKGROUND =
          new Background(new BackgroundFill(Color.GRAY, null, null));

  public static final int GRID_BORDER_WIDTH = 4;

  public static final Border GRID_BORDER =
          new Border(new BorderStroke(Color.GRAY,
                  BorderStrokeStyle.SOLID,
                  CornerRadii.EMPTY,
                  new BorderWidths(GRID_BORDER_WIDTH)));

  public static final int CELL_SIZE    = 20;

//  public static final int CELL_PADDING = 1;

  public static final Color CELL_EMPTY_COLOR = Color.LIGHTGRAY;

  public static final Color CELL_CONTINUING_COLOR = Color.DEEPSKYBLUE;

  public static final Color CELL_STARTING_COLOR = Color.DARKBLUE;

  public static final Color BEAT_MARKER_COLOR = Color.RED;

  public static final int BEAT_MARKER_WIDTH = 3;
}
