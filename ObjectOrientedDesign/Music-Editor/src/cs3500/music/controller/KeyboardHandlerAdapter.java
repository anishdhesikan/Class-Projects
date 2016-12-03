package cs3500.music.controller;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.controller.FXKeyListener;
import cs3500.music.controller.KeyboardHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Handles keyboard events
 */
public class KeyboardHandlerAdapter implements FXKeyListener {
  KeyboardHandler adaptee;

  public KeyboardHandlerAdapter(KeyboardHandler keyboardHandler) {
    this.adaptee = keyboardHandler;
  }


  @Override
  public void keyTyped(KeyEvent e) {
    adaptee.keyTyped(javaFXKeyToSwing(e));
  }

  @Override
  public void keyPressed(KeyEvent e) {
    adaptee.keyPressed(javaFXKeyToSwing(e));
  }

  @Override
  public void keyReleased(KeyEvent e) {
    adaptee.keyReleased(javaFXKeyToSwing(e));
  }

  /**
   * Convert a javaFX KeyEvent to an awt KeyEvent.
   * Note: this conversion only currently works for letters
   *
   * @param keyEvent the given javaFX KeyEvent
   * @return the converted awt KeyEvent
   */
  private java.awt.event.KeyEvent javaFXKeyToSwing (KeyEvent keyEvent) {
//    System.out.println(Integer.toString(keyEvent.getCode().ordinal()) + keyEvent.getCharacter());
//    System.out.println(java.awt.event.KeyEvent.VK_A + "A");
//    System.out.println(java.awt.event.KeyEvent.VK_B + "B");
//    System.out.println(java.awt.event.KeyEvent.VK_C + "B");
    return new java.awt.event.KeyEvent(new Button(), 0, 0, 0,
            keyEvent.getCode().ordinal() + 29);
  }
}