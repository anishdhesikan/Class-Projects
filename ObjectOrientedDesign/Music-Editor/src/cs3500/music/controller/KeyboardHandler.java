package cs3500.music.controller;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles keyboard events
 */
public class KeyboardHandler implements KeyListener {
  private final Map<Integer, Runnable> keyTypedMap;
  private final Map<Integer, Runnable> keyPressedMap;
  private final Map<Integer, Runnable> keyReleasedMap;

  public KeyboardHandler() {
    this.keyTypedMap = new HashMap<Integer, Runnable>();
    this.keyPressedMap = new HashMap<Integer, Runnable>();
    this.keyReleasedMap = new HashMap<Integer, Runnable>();
  }


  @Override
  public void keyTyped(KeyEvent e) {
    // get curRunnable if there is a key and a mapping from that key to a non-null Runnable
    Runnable curRunnable = null;
    if (keyTypedMap.containsKey(e.getKeyCode())) {
      curRunnable = keyTypedMap.get(e.getKeyCode());
    }

    // if there is a mapping, run the mapped Runnable
    if (curRunnable != null) {
      curRunnable.run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // get curRunnable if there is a key and a mapping from that key to a non-null Runnable
    Runnable curRunnable = null;
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      curRunnable = keyPressedMap.get(e.getKeyCode());
    }

    // if there is a mapping, run the mapped Runnable
    if (curRunnable != null) {
      curRunnable.run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // get curRunnable if there is a key and a mapping from that key to a non-null Runnable
    Runnable curRunnable = null;
    if (keyReleasedMap.containsKey(e.getKeyCode())) {
      curRunnable = keyReleasedMap.get(e.getKeyCode());
    }

    // if there is a mapping, run the mapped Runnable
    if (curRunnable != null) {
      curRunnable.run();
    }
  }

  /**
   * Install a runnable for when a given KeyEvent is typed
   *
   * @param key the KeyEvent of interest
   * @param runnable the Runnable to run when the given key is pressed
   */
  public void installOnKeyTyped (int key, Runnable runnable) {
    this.keyTypedMap.put(key, runnable);
  }

  /**
   * Install a runnable for when a given KeyEvent is pressed
   *
   * @param key the KeyEvent of interest
   * @param runnable the Runnable to run when the given key is pressed
   */
  public void installOnKeyPressed (int key, Runnable runnable) {
    this.keyPressedMap.put(key, runnable);
  }

  /**
   * Install a runnable for when a given KeyEvent is released
   *
   * @param key the KeyEvent of interest
   * @param runnable the Runnable to run when the given key is pressed
   */
  public void installOnKeyReleased (int key, Runnable runnable) {
    this.keyReleasedMap.put(key, runnable);
  }
}
