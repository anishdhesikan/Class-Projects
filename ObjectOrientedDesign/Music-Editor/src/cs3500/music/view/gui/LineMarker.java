package cs3500.music.view.gui;

import javafx.animation.Animation;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import static cs3500.music.view.gui.Constants.CELL_CONTINUING_COLOR;
import static cs3500.music.view.gui.Constants.CELL_EMPTY_COLOR;
import static cs3500.music.view.gui.Constants.CELL_SIZE;
import static cs3500.music.view.gui.Constants.CELL_STARTING_COLOR;

final class LineMarker {
  private final Pane root;
  private final Shape token;

  private NoteState state;
  private Animation errorAnimation = null;


  LineMarker() {
    this(Color.BLACK, 1);
  }

  LineMarker(Color color, double width) {
    int lineHeight = CELL_SIZE;
    token = new javafx.scene.shape.Line
            (0, 0, 0, lineHeight);

    token.setStroke(color);
    token.setStrokeWidth(width);

    root = new Pane(token);

  }

  Node root() {
    return root;
  }

  Node target() {
    return token;
  }

  void setColor(Color color) {
    token.setStroke(color);
  }

  void setStrokeWidth(double width) {
    this.token.setStrokeWidth(width);
  }

  private static Color stateToColor(NoteState state) {
    if (state == null) {
      return CELL_EMPTY_COLOR;
    }

    switch (state) {
      case Starting:
        return CELL_STARTING_COLOR;

      case Continuing:
        return CELL_CONTINUING_COLOR;

      case Empty:
        return CELL_EMPTY_COLOR;

      default:
        throw new RuntimeException("should be impossible");
    }
  }
}
