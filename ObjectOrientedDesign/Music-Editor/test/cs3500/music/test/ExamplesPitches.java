package cs3500.music.test;

import cs3500.music.model.Pitch;
import cs3500.music.model.PitchClass;
import cs3500.music.model.PitchFactory;

/**
 * Class for creating Pitch Examples
 */
class ExamplesPitches {
  PitchFactory pf;

  ExamplesPitches(PitchFactory pitchFactory) {
    this.pf = pitchFactory;
  }

  /**
   * Creates a C-1 pitch
   */
  Pitch cn1() {
    return pf.makePitch(PitchClass.C, -1);
  }

  /**
   * Creates a B3 pitch
   */
  Pitch b3() {
    return pf.makePitch(PitchClass.B, 3);
  }

  /**
   * Creates a C4 pitch
   */
  Pitch c4() {
    return pf.makePitch(PitchClass.C, 4);
  }

  /**
   * Creates a C#4 pitch
   */
  Pitch cS4() {
    return pf.makePitch(PitchClass.C_Sharp, 4);
  }

  /**
   * Creates a D4 pitch
   */
  Pitch d4() {
    return pf.makePitch(PitchClass.D, 4);
  }

  /**
   * Creates a B4 pitch
   */
  Pitch b4() {
    return pf.makePitch(PitchClass.B, 4);
  }

  /**
   * Creates a C5 pitch
   */
  Pitch c5() {
    return pf.makePitch(PitchClass.C, 5);
  }

  /**
   * Creates a G9 pitch
   */
  Pitch g9() {
    return pf.makePitch(PitchClass.G, 9);
  }
}
