package games.project.factory_fall.vues.helpers;

import games.project.factory_fall.FactoryFall;
import javafx.scene.control.ComboBox;

import java.util.List;
import java.util.Objects;

/**
 * La boite combinée est un élément graphique permettant de constuire simplement des comboBox (genre de menu déroulant)
 * communes dans le projet
 */
public class BoiteCombinee extends ComboBox {

    /**
     * @param choix    La liste des choix que propose la boîte
     * @param nomBoite Le nom qui apparait dans la cellule de la boîte (la seule visible quand elle n'est pas ouverte)
     */
    private BoiteCombinee(List choix, String nomBoite) {
        super();
        this.setValue(nomBoite);
        this.getItems().addAll(choix);

        styliser();
    }

    /**
     * @param nbChoixVisibles Le nombre de choix visibles lorsqu'on ouvre la boîte. S'il y a plus de choix que cet attribut, une barre de scroll apparait.
     */
    public BoiteCombinee(List choix, String nomBoite, int nbChoixVisibles) {
        this(choix, nomBoite);
        this.setVisibleRowCount(nbChoixVisibles);
    }

    private void styliser() {
        this.getStylesheets().add(Objects.requireNonNull(FactoryFall.class.getResource("css/main.css")).toString());
        this.getStyleClass().add("boite-combinee");
    }

    /**
     * @return Vrai si un des choix proposés a été sélectionné. Faux sinon
     */
    public boolean estVide() {
        return !this.getItems().contains(this.getValue());
    }

}
