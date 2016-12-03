# OOD-hw06
Homework 7 (OOD) - Anish Dhesikan &amp; Kevin McDonough

HOW TO RUN:
- Run with 2 args, the first being the file name and the second being the type of view
- For the composite view, the second arg should be "composite"

HOW TO USE:
* Navigation *
- Press "P" to play/pause
- Press "N" to move the beatMarker left one beat
- Press "M" to move the beatMarker right one beat
- Press left and right arrow keys to scroll
- Press home and end to scroll to the end and beginning

* Editing *
- Left click to select a cell
- Once selected, press "R" to remove a note (if a note exists in that cell)
- Once selected, press any number from 1-9 to add a note of that length at that cell
- Once selected, right click to move that note to the new position



Design:
- ViewFactory creates a view and passes it off to a controller
- CompositeController takes in a CompositeView from the ViewFactory
- CompositeView has a GuiView and a MidiView. All views implement to View interface
- There is an interface for GuiView and MidiView for extensibility and organization
- CompositeController installs KeyHandlers and MouseHandlers for when the application is runnning

Model Changes:
NOTE: Rather than taking either of our models and improving them, we mostly rewrote from scratch.
  Regardless, here's a summary of the large-scale changes we made:
 - Created Playable interface that both Note and Song implement, defining the basic methods for
 getting notes at a beat and notes continuing at a beat.
 - Added Pitch class to represent all pitches, using a public enum PitchClass and an octave
 - Added Factory interfaces for Note and Pitch, with implementations on our implementation classes.
 This allows clients to create notes and pitches without having to tightly couple to a specific
 implementation.
 - Took getLength() off Song interface, and moved responsibility to new getEndingBeat() method on
 Playable interface.
 - Gave Songs the ability to set a specific ending beat, though default behavior still
 automatically sets the ending beat to first beat with no notes playing.

Model Design:
 There is a Playable interface that contains both Songs and Notes.
 Song is an interface with its implementation SongImpl under it.
 Note is also an interface, with its implementation NoteImpl under it.
 There is a Pitch interface outside of Playable that holds the PitchClass
 and octave information for a note.
 Both Pitches and Notes have their own factories so that clients are not
 limited to any specific implementation of the interfaces.

