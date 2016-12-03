package cs3500.music.model;

/**
 * Represents a musical pitch class. (i.e. A, B, C#)
 */
public enum PitchClass {
  C (0),
  C_Sharp (1),
  D (2),
  D_Sharp (3),
  E (4),
  F (5),
  F_Sharp (6),
  G (7),
  G_Sharp (8),
  A (9),
  A_Sharp (10),
  B (11);

  private int value;

  PitchClass(int value) {
    this.value = value;
  }

  /**
   * Get the value of this PitchClass enumeration
   *
   * @return the first PitchClass enumeration that has the given value
   */
  public int getValue() {
    return value;
  }

  /**
   * Get the first PitchClass enumeration that has the given value
   *
   * @return the first PitchClass enumeration that has the given value
   * @throws IllegalArgumentException if there is no PitchClass with the given value
   */
  public static PitchClass getPitchClass(int value) {
    for (PitchClass curPitch : PitchClass.values()) {
      if (curPitch.value == value) {
        return curPitch;
      }
    }
    throw new IllegalArgumentException("PitchClass of value " + value +
            " not found");
  }

  /**
   * Get the highest int value of the enumerations for PitchClass
   *
   * @return the highest int value of the enumerations for PitchClass
   * @throws IllegalStateException if there are no enumerations for PitchClass
   */
  public static int getHighestValue() {
    if (PitchClass.values().length == 0) {
      throw new IllegalStateException("No enumerations for PitchClass");
    }

    int highestValue = PitchClass.values()[0].getValue();

    for (PitchClass curPitch : PitchClass.values()) {
      if (curPitch.getValue() > highestValue) {
        highestValue = curPitch.getValue();
      }
    }

    return highestValue;
  }
}