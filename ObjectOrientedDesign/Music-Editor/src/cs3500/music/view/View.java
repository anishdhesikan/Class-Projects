package cs3500.music.view;

import cs3500.music.model.Playable;

/**
 * To represent any View for the {@code Playable} interface
 */
public interface View {
  /**
   * Loads the given playable into this view
   *
   * @param playable the playable to be loaded
   */
  void loadPlayable(Playable playable);

  /**
   * Renders the given Playable using this View
   */
  void renderWhole();

  /**
   * Renders just the given beat of the Playable p, using this View
   *
   * @param beat the rendered beat
   */
  void renderBeat(int beat);
}
