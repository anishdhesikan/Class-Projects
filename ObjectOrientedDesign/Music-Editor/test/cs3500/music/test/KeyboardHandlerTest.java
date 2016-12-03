package cs3500.music.test;


import org.junit.Before;
import org.junit.Test;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.KeyboardHandlerAdapter;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.Assert.assertEquals;

/**
 * Created by Anish on 11/23/15.
 */
public class KeyboardHandlerTest {
  MockRunnable mockRunnable;
  KeyboardHandler handler = new KeyboardHandler();

  @Before
  public void setUp() throws Exception {
    mockRunnable = new MockRunnable(0);
    handler = new KeyboardHandler();
  }

  @Test
  public void generalTest() {
    // make sure the runnable has a value of 0 at start
    assertEquals(mockRunnable.getValue(), 0);

    // install a runnable when "a" is typed
    handler.installOnKeyTyped(KeyEvent.VK_A, mockRunnable);
    // install a runnable when "k" is pressed
    handler.installOnKeyPressed(KeyEvent.VK_K, mockRunnable);
    // install a runnable when "k" is released
    handler.installOnKeyReleased(KeyEvent.VK_K, mockRunnable);

    // nothing should change
    assertEquals(mockRunnable.getValue(), 0);

    // type a key that is not the key in the keylistener
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_K));

    // nothing should change
    assertEquals(mockRunnable.getValue(), 0);

    // type a key that is in the keylistener
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));

    // runnable should be incremented by 1
    assertEquals(mockRunnable.getValue(), 1);

    // invoke all three installables. runnable should increment 3 times.
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));
    assertEquals(mockRunnable.getValue(), 2);
    handler.keyPressed(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_K));
    assertEquals(mockRunnable.getValue(), 3);
    handler.keyReleased(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_K));
    assertEquals(mockRunnable.getValue(), 4);
  }

  @Test
  public void testKeyTyped() {
    // make sure the runnable has a value of 0 at start
    assertEquals(mockRunnable.getValue(), 0);

    // install a runnable when "a" is typed
    handler.installOnKeyTyped(KeyEvent.VK_A, mockRunnable);

    // nothing should change
    assertEquals(mockRunnable.getValue(), 0);

    // type a key that is not the key in the keylistener
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_Z));

    // nothing should change
    assertEquals(mockRunnable.getValue(), 0);

    // type a key that is in the keylistener
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));

    // runnable should be incremented by 1
    assertEquals(mockRunnable.getValue(), 1);

    // install the same runnable when "k" or "r" is typed
    handler.installOnKeyTyped(KeyEvent.VK_K, mockRunnable);
    handler.installOnKeyTyped(KeyEvent.VK_R, mockRunnable);

    // make sure pressing the keys does not invoke the runnable
    handler.keyPressed(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));
    handler.keyPressed(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_K));
    handler.keyPressed(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_R));

    // nothing should change
    assertEquals(mockRunnable.getValue(), 1);

    // make sure releasing the keys does not invoke the runnable
    handler.keyReleased(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));
    handler.keyReleased(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_K));
    handler.keyReleased(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_R));

    // nothing should change
    assertEquals(mockRunnable.getValue(), 1);

    // type all three keys. runnable should increment 3 times.
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));
    assertEquals(mockRunnable.getValue(), 2);
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_K));
    assertEquals(mockRunnable.getValue(), 3);
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_R));
    assertEquals(mockRunnable.getValue(), 4);
  }

  @Test
  public void testKeyPressed() {
    // make sure the runnable has a value of 0 at start
    assertEquals(mockRunnable.getValue(), 0);

    // install a runnable when "a" is pressed
    handler.installOnKeyPressed(KeyEvent.VK_A, mockRunnable);

    // nothing should change
    assertEquals(mockRunnable.getValue(), 0);

    // press a key that is not the key in the keylistener
    handler.keyPressed(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_Z));

    // nothing should change
    assertEquals(mockRunnable.getValue(), 0);

    // press a key that is in the keylistener
    handler.keyPressed(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));

    // runnable should be incremented by 1
    assertEquals(mockRunnable.getValue(), 1);

    // install the same runnable when "k" or "r" is pressed
    handler.installOnKeyPressed(KeyEvent.VK_K, mockRunnable);
    handler.installOnKeyPressed(KeyEvent.VK_R, mockRunnable);

    // make sure typing the keys does not invoke the runnable
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_K));
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_R));

    // nothing should change
    assertEquals(mockRunnable.getValue(), 1);

    // make sure releasing the keys does not invoke the runnable
    handler.keyReleased(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));
    handler.keyReleased(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_K));
    handler.keyReleased(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_R));

    // nothing should change
    assertEquals(mockRunnable.getValue(), 1);

    // press all three keys. runnable should increment 3 times.
    handler.keyPressed(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));
    assertEquals(mockRunnable.getValue(), 2);
    handler.keyPressed(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_K));
    assertEquals(mockRunnable.getValue(), 3);
    handler.keyPressed(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_R));
    assertEquals(mockRunnable.getValue(), 4);
  }

  @Test
  public void testKeyReleased() throws Exception {
    // make sure the runnable has a value of 0 at start
    assertEquals(mockRunnable.getValue(), 0);

    // install a runnable when "a" is released
    handler.installOnKeyReleased(KeyEvent.VK_A, mockRunnable);

    // nothing should change
    assertEquals(mockRunnable.getValue(), 0);

    // released a key that is not the key in the keylistener
    handler.keyReleased(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_Z));

    // nothing should change
    assertEquals(mockRunnable.getValue(), 0);

    // released a key that is in the keylistener
    handler.keyReleased(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));

    // runnable should be incremented by 1
    assertEquals(mockRunnable.getValue(), 1);

    // install the same runnable when "k" or "r" is released
    handler.installOnKeyReleased(KeyEvent.VK_K, mockRunnable);
    handler.installOnKeyReleased(KeyEvent.VK_R, mockRunnable);

    // make sure typing the keys does not invoke the runnable
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_K));
    handler.keyTyped(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_R));

    // nothing should change
    assertEquals(mockRunnable.getValue(), 1);

    // make sure pressing the keys does not invoke the runnable
    handler.keyPressed(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));
    handler.keyPressed(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_K));
    handler.keyPressed(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_R));

    // nothing should change
    assertEquals(mockRunnable.getValue(), 1);

    // release all three keys. runnable should increment 3 times.
    handler.keyReleased(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_A));
    assertEquals(mockRunnable.getValue(), 2);
    handler.keyReleased(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_K));
    assertEquals(mockRunnable.getValue(), 3);
    handler.keyReleased(new KeyEvent(new Button(), 0, 0, 0, KeyEvent.VK_R));
    assertEquals(mockRunnable.getValue(), 4);
  }

  /**
   * A runnable that carries an int value and increments the value each time it is run.
   * Used for testing.
   */
  private static class MockRunnable implements Runnable {
    private int value;

    public MockRunnable(int value) {
      this.value = value;
    }

    /**
     * Get the value of this MockRunnable
     */
    public int getValue() {
      return this.value;
    }

    /**
     * Increment this MockRunnable's value for testing
     */
    @Override
    public void run() {
      this.value += 1;
    }
  }

}