package cs3500.music.controller;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Handles mouse events
 */
public class MouseHandler implements FXMouseListener {

  private final Map<MouseButton, Runnable> mouseClickedMap;
  private final Map<MouseButton, Runnable> mousePressedMap;
  private final Map<MouseButton, Runnable> mouseReleasedMap;
  private final Map<MouseButton, Runnable> mouseEnteredMap;
  private final Map<MouseButton, Runnable> mouseExitedMap;

  public MouseHandler() {
    this.mouseClickedMap = new HashMap<MouseButton, Runnable>();
    this.mousePressedMap = new HashMap<MouseButton, Runnable>();
    this.mouseReleasedMap = new HashMap<MouseButton, Runnable>();
    this.mouseEnteredMap = new HashMap<MouseButton, Runnable>();
    this.mouseExitedMap = new HashMap<MouseButton, Runnable>();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // get curRunnable if there is a mouseButton and a mapping from that mouseButton to a non-null
    // Runnable
    Runnable curRunnable = null;
    if (mouseClickedMap.containsKey(e.getButton())) {
      curRunnable = mouseClickedMap.get(e.getButton());
    }

    // if there is a mapping, run the mapped Runnable
    if (curRunnable != null) {
      curRunnable.run();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // get curRunnable if there is a mouseButton and a mapping from that mouseButton to a non-null
    // Runnable
    Runnable curRunnable = null;
    if (mousePressedMap.containsKey(e.getButton())) {
      curRunnable = mousePressedMap.get(e.getButton());
    }

    // if there is a mapping, run the mapped Runnable
    if (curRunnable != null) {
      curRunnable.run();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // get curRunnable if there is a mouseButton and a mapping from that mouseButton to a non-null
    // Runnable
    Runnable curRunnable = null;
    if (mouseReleasedMap.containsKey(e.getButton())) {
      curRunnable = mouseReleasedMap.get(e.getButton());
    }

    // if there is a mapping, run the mapped Runnable
    if (curRunnable != null) {
      curRunnable.run();
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // get curRunnable if there is a mouseButton and a mapping from that mouseButton to a non-null
    // Runnable
    Runnable curRunnable = null;
    if (mouseEnteredMap.containsKey(e.getButton())) {
      curRunnable = mouseEnteredMap.get(e.getButton());
    }

    // if there is a mapping, run the mapped Runnable
    if (curRunnable != null) {
      curRunnable.run();
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // get curRunnable if there is a mouseButton and a mapping from that mouseButton to a non-null
    // Runnable
    Runnable curRunnable = null;
    if (mouseExitedMap.containsKey(e.getButton())) {
      curRunnable = mouseExitedMap.get(e.getButton());
    }

    // if there is a mapping, run the mapped Runnable
    if (curRunnable != null) {
      curRunnable.run();
    }
  }

  /**
   * Install a runnable for when a mouse event of given mouseButton is clicked
   *
   * @param mouseButton the MouseButton of interest
   * @param runnable the Runnable to run when the given mouseButton is clicked
   */
  public void installOnMouseClicked (MouseButton mouseButton, Runnable runnable) {
    this.mouseClickedMap.put(mouseButton, runnable);
  }

  /**
   * Install a runnable for when a mouse event of given mouseButton is pressed
   *
   * @param mouseButton the MouseButton of interest
   * @param runnable the Runnable to run when the given mouseButton is pressed
   */
  public void installOnMousePressed (MouseButton mouseButton, Runnable runnable) {
    this.mousePressedMap.put(mouseButton, runnable);
  }

  /**
   * Install a runnable for when a mouse event of given mouseButton is released
   *
   * @param mouseButton the MouseButton of interest
   * @param runnable the Runnable to run when the given mouseButton is released
   */
  public void installOnMouseReleased (MouseButton mouseButton, Runnable runnable) {
    this.mouseReleasedMap.put(mouseButton, runnable);
  }

  /**
   * Install a runnable for when a mouse event of given mouseButton enters
   *
   * @param mouseButton the MouseButton of interest
   * @param runnable the Runnable to run when the given mouseButton enters
   */
  public void installOnMouseEnter (MouseButton mouseButton, Runnable runnable) {
    this.mouseEnteredMap.put(mouseButton, runnable);
  }

  /**
   * Install a runnable for when a mouse event of given mouseButton exits
   *
   * @param mouseButton the MouseButton of interest
   * @param runnable the Runnable to run when the given mouseButton exits
   */
  public void installOnMouseExit (MouseButton mouseButton, Runnable runnable) {
    this.mouseExitedMap.put(mouseButton, runnable);
  }

}
