package cs3500.music.view.gui;

import javafx.animation.Animation;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import static cs3500.music.view.gui.Constants.*;

final class CellView {
  private final Pane root;
  private final Shape token;

  private NoteState state;
  private Animation errorAnimation = null;

  CellView() {
//    int rectSize = CELL_SIZE / 2 - CELL_PADDING;
    int rectSize = CELL_SIZE;
    token = new javafx.scene.shape.Rectangle(rectSize, rectSize);
    token.setFill(Constants.CELL_EMPTY_COLOR);
    token.setStroke(Color.BLACK);
    token.setStrokeWidth(0.2);
//    token.setX(CELL_SIZE / 2);
//    token.setY(CELL_SIZE / 2);

    root = new Pane(token);
//    root.setPadding(new Insets(CELL_PADDING));

    setState(null);
  }

  Node root() {
    return root;
  }

  Node target() {
    return token;
  }

  void setState(NoteState newState) {
    state = newState;
    refresh();
  }

  private void refresh() {
    token.setFill(stateToColor(state));
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
