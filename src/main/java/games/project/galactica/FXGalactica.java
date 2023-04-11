package games.project.galactica;

import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;

public class FXGalactica extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var startFrame = new StartFrame();
        startFrame.setPreferredSize(new Dimension(1280, 720));
        startFrame.setVisible(true);
    }
}
