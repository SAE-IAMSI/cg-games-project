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

    exports games.project.prehispong.controller;
    exports games.project.prehispong.view;
    exports games.project.prehispong.model;
    exports games.project.prehispong to javafx.graphics;
    opens games.project.prehispong.view to javafx.fxml;

    exports games.project.modules.parametres;
    opens games.project.modules.parametres.controller to javafx.fxml;

    exports games.project.casse_briques;
    exports games.project.casse_briques.controller;
    opens games.project.casse_briques.controller to javafx.fxml;
    opens games.project.casse_briques.view to javafx.fxml;

    exports games.project.motron;

    exports games.project.factory_fall;

    exports games.project.paco_mano;
    exports games.project.paco_mano.view;
    opens games.project.paco_mano.view to javafx.fxml;
    opens games.project.paco_mano.controller to javafx.fxml;

    exports games.project;
    opens games.project.motron.controller to javafx.fxml;

    exports games.project.modules.statistiques;
    exports games.project.modules.statistiques.views;

    exports games.project.galactica;
    exports games.project.galactica.sprite;
    exports games.project.modules.parametres.controller;


    exports games.project.prehispong.sound;

    exports games.project.modules.tournois;
    exports games.project.modules.tournois.controller;
    opens games.project.modules.tournois.controller to javafx.fxml;
    opens games.project.modules.tournois.view to javafx.fxml;
}
