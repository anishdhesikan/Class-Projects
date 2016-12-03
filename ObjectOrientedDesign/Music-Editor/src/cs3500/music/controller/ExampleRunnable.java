package cs3500.music.controller;

/**
 * Created by Anish on 11/19/15.
 */
public class ExampleRunnable implements Runnable {
  private final String message;

  public ExampleRunnable(String message) {
    this.message = message;
  }

  @Override
  public void run() {
    System.out.println(message);
  }
}
