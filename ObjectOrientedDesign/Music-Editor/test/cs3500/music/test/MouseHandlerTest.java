package cs3500.music.test;

import org.junit.Before;
import org.junit.Test;

import cs3500.music.controller.MouseHandler;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;

import static org.junit.Assert.*;

/**
 * Created by Anish on 11/25/15.
 */
public class MouseHandlerTest {
  MockRunnable mockRunnable;
  MouseHandler handler = new MouseHandler();

  @Before
  public void setUp() throws Exception {
    mockRunnable = new MockRunnable(0);
    handler = new MouseHandler();
  }

  private MouseEvent makeMouseEvent(MouseButton button) {
    return new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, button, 1,
            true, true, true, true, true, true, true, true, true, true, new PickResult(new
            Pane(), Point3D.ZERO, 0));
  }

  @Test
  public void testMouse() throws Exception {

    // make sure the runnable has a value of 0 at start
    assertEquals(mockRunnable.getValue(), 0);

    // install a runnable when left-clicked
    handler.installOnMouseClicked(MouseButton.PRIMARY, mockRunnable);
    // install a runnable when right-pressed
    handler.installOnMousePressed(MouseButton.SECONDARY, mockRunnable);
    // install a runnable when middle-released
    handler.installOnMouseReleased(MouseButton.MIDDLE, mockRunnable);

    // nothing should change
    assertEquals(mockRunnable.getValue(), 0);

    // click a mouseButton that is not in the listener
    handler.mouseClicked(makeMouseEvent(MouseButton.SECONDARY));

    // nothing should change
    assertEquals(mockRunnable.getValue(), 0);

    // click a mouseButton that is in the listener
    handler.mouseClicked(makeMouseEvent(MouseButton.PRIMARY));

    // runnable should be incremented by 1
    assertEquals(mockRunnable.getValue(), 1);

    // invoke all three installables. runnable should increment 3 times.
    handler.mouseClicked(makeMouseEvent(MouseButton.PRIMARY));
    assertEquals(mockRunnable.getValue(), 2);
    handler.mousePressed(makeMouseEvent(MouseButton.SECONDARY));
    assertEquals(mockRunnable.getValue(), 3);
    handler.mouseReleased(makeMouseEvent(MouseButton.MIDDLE));
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