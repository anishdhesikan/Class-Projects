package cs3500.music.model;

/**
 * Implementation of a musical pitch
 */
public final class PitchImpl implements Pitch {
  /**
   * The pitch class of this pitch
   */
  final PitchClass pitchClass;

  /**
   * The octave number of this pitch
   */
  final int octave;

  /**
   * Default constructor. Constructs a PitchImpl with the given PitchClass and octave number.
   * @param pitchClass the pitch class of this pitch on the chromatic scale
   * @param octave the octave of the pitch
   */
  public PitchImpl(PitchClass pitchClass, int octave) {
    this.pitchClass = pitchClass;
    this.octave = octave;
  }

  @Override
  public PitchClass getPitchClass() {
    return this.pitchClass;
  }

  @Override
  public int getOctave() {
    return this.octave;
  }

  @Override
  public int getMidiNumber() {
    return this.getOctave() * 12 + 12 + this.getPitchClass().getValue();
  }

  @Override
  public int compareTo(Pitch other) {
    if (other.getOctave() > this.getOctave()) {
      return -1;
    } else if (other.getOctave() < this.getOctave()) {
      return 1;
    } else {
      // octaves are the same. check PitchClasses

      if (other.getPitchClass().getValue() > this.getPitchClass().getValue()) {
        return -1;
      } else if (other.getPitchClass().getValue() < this.getPitchClass().getValue()) {
        return 1;
      } else {
        //octaves and pitches are the same.
        return 0;
      }
    }
  }

  @Override
  public String toString() {
    String result = "";
    switch(this.getPitchClass()) {
      case A:
        result =  "A";
        break;
      case A_Sharp:
        result =  "A#";
        break;
      case B:
        result =  "B";
        break;
      case C:
        result =  "C";
        break;
      case C_Sharp:
        result =  "C#";
        break;
      case D:
        result =  "D";
        break;
      case D_Sharp:
        result =  "D#";
        break;
      case E:
        result =  "E";
        break;
      case F:
        result =  "F";
        break;
      case F_Sharp:
        result =  "F#";
        break;
      case G:
        result =  "G";
        break;
      case G_Sharp:
        result =  "G#";
        break;
      default:
        throw new IllegalStateException("PitchClass does not have a string conversion!");
    }

    result += octave;
    return result;
  }

  @Override
  public Pitch nextPitch() {
    Factory pitchFactory = new Factory();
    return pitchFactory.makePitch(this.getMidiNumber() + 1);
  }

  public static class Factory implements PitchFactory {
    @Override
    public Pitch makePitch(PitchClass pitchClass, int octave) {
      return new PitchImpl(pitchClass, octave);
    }

    @Override
    public Pitch makePitch(int midiNumber) {
      int pitchClassNumber = midiNumber % 12;
      PitchClass pitchClass = PitchClass.getPitchClass(pitchClassNumber);
      int octave = midiNumber / 12 - 1;

      return new PitchImpl(pitchClass, octave);
    }
  }

  @Override
  public boolean equals(Object otherPitch) {
    if (!(otherPitch instanceof PitchImpl)) {
      return false;
    } else {
      return this.getMidiNumber() == ((PitchImpl) otherPitch).getMidiNumber();
    }
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(this.getMidiNumber());
  }
}