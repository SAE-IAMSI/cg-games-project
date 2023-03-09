module games.project.launcher {
    requires javafx.base;
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires javafx.media;

    exports games.project.koala_rock;
    exports games.project.koala_rock.Client;
    exports games.project.koala_rock.View;
    exports games.project.koala_rock.Model;
    exports games.project.koala_rock.Sound;

    exports games.project.casse_briques;
    exports games.project.casse_briques.controller;
    exports games.project.casse_briques.stockage;
    exports games.project.casse_briques.stockage.sql;
    opens games.project.casse_briques.controller to javafx.fxml;
    opens games.project.casse_briques.view to javafx.fxml;

    exports games.project.motron;

    exports games.project.factory_fall;

    exports games.project;

    exports games.project.equipe6;
}