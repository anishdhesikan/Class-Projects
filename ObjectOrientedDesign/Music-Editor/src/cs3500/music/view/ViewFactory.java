package cs3500.music.view;

import java.awt.event.KeyEvent;

import cs3500.music.MusicEditor;
import cs3500.music.controller.CompositeController;
import cs3500.music.controller.CompositeTimedController;
import cs3500.music.controller.TimedController;
import cs3500.music.model.Playable;
import cs3500.music.model.Song;
import cs3500.music.view.composite.CompositeView;
import cs3500.music.view.console.ConsoleController;
import cs3500.music.view.gui.GuiView;
import cs3500.music.view.gui.GuiViewImpl2;
import cs3500.music.view.midi.MidiView;
import cs3500.music.view.midi.MidiViewImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * A factory for creating various types of views
 */
public class ViewFactory extends Application {
  private Playable playable;
  private static Stage mainStage;
  private static boolean startGridView = false;
  private static boolean startMidiView = false;
  private static boolean startGui2View = false;
  private static boolean startCompositeView = false;

  // ViewFactory cannot have arguments because it extends Application
  public ViewFactory() {
    this.playable = MusicEditor.getPlayable();
  }

  /**
   * Produces different views of the MusicEditor's playable based on the given viewType string
   *
   * @param viewType the type of view to output
   */
  public void startView(String viewType) {
    switch (viewType) {
      case "console":
        this.startConsoleView(playable);
        break;
      case "visual":
        this.startGridView = true;
        launch();
        break;
      case "midi":
        this.startMidiView = true;
        launch();
        break;
      case "gui2":
        this.startGui2View = true;
        launch();
        break;
      case "composite":
        this.startCompositeView = true;
        launch();
        break;
    }
  }

  /**
   * Called after launch() because ViewFactory is an Application.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    mainStage = primaryStage;

    Playable renderedSong = playable;
    if (startGridView) {
      System.out.println("OOPS, removed");
    } else if (startMidiView) {
      startMidiView(renderedSong);
    } else if (startGui2View) {
      startGui2View(renderedSong);
    } else if (startCompositeView) {
      startCompositeView(renderedSong);
//      startCompositeTimedView(renderedSong);
    }
  }

  /**
   * Renders a console view of the given playable
   *
   * @param playable the playable music piece of which to render
   */
  private static void startConsoleView(Playable playable) {
    ConsoleController controller = new ConsoleController(playable, System.out);
    controller.run();
  }

  /**
   * Renders a GUI view of the given playable NOTE: launch() must already be called before this
   *
   * @param playable the playable music piece of which to render
   */
  private static void startGui2View(Playable playable) {
    GuiViewImpl2 view = new GuiViewImpl2();
    view.loadPlayable(playable);
    Scene scene = new Scene(new Group(view.root()));

    scene.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ESCAPE) {
        Platform.exit();
      }
    });

    mainStage.initStyle(StageStyle.DECORATED);
    mainStage.setTitle("GUI view 2");
    mainStage.setScene(scene);
    mainStage.show();
  }

  /**
   * Renders a midi view of the given playable NOTE: launch() must already be called before this
   *
   * @param playable the playable music piece of which to render
   */
  private static void startMidiView(Playable playable) {
    MidiView myMidi = new MidiViewImpl();

    myMidi.loadPlayable(playable);
    myMidi.renderWhole();
    myMidi.closeReceiver();
  }

  /**
   * Renders a GUI view and MIDI view of the given playable at the same time
   * NOTE: launch() must already be called before this
   *
   * @param playable the playable music piece of which to render
   */
  private static void startCompositeView(Playable playable) {
    GuiViewImpl2 guiView = new GuiViewImpl2();
    MidiView midiView = new MidiViewImpl();
    CompositeView view = new CompositeView(guiView, midiView);

    CompositeController controller = new CompositeController(view, (Song) playable);

    Scene scene = new Scene(new Group(guiView.root()));

    scene.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ESCAPE) {
        Platform.exit();
      }
    });

    mainStage.initStyle(StageStyle.DECORATED);
    mainStage.setTitle("Timed Composite View");
    mainStage.setScene(scene);
    mainStage.show();
    controller.run();
  }

  /**
   * Renders a GUI view and MIDI view of the given playable at the same time
   * NOTE: launch() must already be called before this
   *
   * @param playable the playable music piece of which to render
   */
  private static void startTimedView(Playable playable) {
    GuiViewImpl2 guiView = new GuiViewImpl2();
    MidiView midiView = new MidiViewImpl();
    CompositeView view = new CompositeView(guiView, midiView);

    TimedController controller = new TimedController(view, playable);
    Scene scene = new Scene(new Group(view.root()));

    scene.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ESCAPE) {
        Platform.exit();
      }
    });

    mainStage.initStyle(StageStyle.DECORATED);
    mainStage.setTitle("Timed Composite View");
    mainStage.setScene(scene);
    mainStage.show();
    controller.run();
  }

  /**
   * Renders a GUI view and MIDI view of the given playable at the same time
   * NOTE: launch() must already be called before this
   *
   * @param playable the playable music piece of which to render
   */
  private static void startCompositeTimedView(Playable playable) {
    GuiView guiView = new GuiViewImpl2();
    MidiView midiView = new MidiViewImpl();

    CompositeTimedController controller = new CompositeTimedController(guiView, midiView, playable);
    Scene scene = new Scene(new Group(guiView.root()));

    scene.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ESCAPE) {
        Platform.exit();
      }
    });

    mainStage.initStyle(StageStyle.DECORATED);
    mainStage.setTitle("Timed Composite View");
    mainStage.setScene(scene);
    mainStage.show();
    controller.run();
  }
}
