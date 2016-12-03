package cs3500.music.view.gui;

/**
 * An enumeration for the state of a note on a particular beat.
 * Used by CellView.
 */
enum NoteState {
  Starting, // A note is starting on this beat
  Continuing, // A note is continuing (not starting) on this beat
  Empty // No note in this cell
}