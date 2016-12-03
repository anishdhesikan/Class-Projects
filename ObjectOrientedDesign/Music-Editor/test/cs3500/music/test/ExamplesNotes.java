package cs3500.music.test;

import cs3500.music.model.Note;
import cs3500.music.model.NoteFactory;
import cs3500.music.model.PitchFactory;

/**
 * Class for generating note examples.
 */
class ExamplesNotes {
  private final NoteFactory fact;
  private final ExamplesPitches pitchEx;

  ExamplesNotes(NoteFactory noteFactory, PitchFactory pitchFactory) {
    this.fact = noteFactory;
    this.pitchEx = new ExamplesPitches(pitchFactory);
  }

  Note cn1_0_4() {
    return fact.makeNote(pitchEx.cn1(), 0, 4, 100, 1);
  }

  Note b3_0_4() {
    return fact.makeNote(pitchEx.b3(), 0, 4, 100, 1);
  }

  Note c4_0_4() {
    return fact.makeNote(pitchEx.c4(), 0, 4, 100, 1);
  }

  Note c4_2_4() {
    return fact.makeNote(pitchEx.c4(), 2, 4, 100, 1);
  }

  Note c4_3_4() {
    return fact.makeNote(pitchEx.c4(), 3, 4, 100, 1);
  }

  Note c4_4_4() {
    return fact.makeNote(pitchEx.c4(), 4, 4, 100, 1);
  }

  Note cS4_0_4() {
    return fact.makeNote(pitchEx.cS4(), 0, 4, 100, 1);
  }

  Note d4_0_4() {
    return fact.makeNote(pitchEx.d4(), 0, 4, 100, 1);
  }

  Note b4_0_4() {
    return fact.makeNote(pitchEx.b4(), 0, 4, 100, 1);
  }

  Note c5_0_4() {
    return fact.makeNote(pitchEx.c5(), 0, 4, 100, 1);
  }

  Note g9_0_4() {
    return fact.makeNote(pitchEx.g9(), 0, 4, 100, 1);
  }

  Note lateC4() {
    return fact.makeNote(pitchEx.c4(), 20, 4, 100, 1);
  }
}
