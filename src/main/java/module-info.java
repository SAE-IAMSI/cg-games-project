module games.project.launcher {
    requires javafx.base;
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires javafx.media;

    exports games.project.koala_rock;
    exports games.project.koala_rock.View;
    exports games.project.koala_rock.Model;
    exports games.project.koala_rock.Sound;
}