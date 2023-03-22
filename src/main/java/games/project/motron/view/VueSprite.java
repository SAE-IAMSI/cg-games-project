package games.project.motron.view;

import games.project.motron.Motron;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.LinkedHashMap;
import java.util.Objects;

public class VueSprite {

    private Image skinMoto;
    private ImageView skinMotoView;
    private final int trailSize = 20;
    private Color trailColor;
    private String nom;

    public VueSprite(Image skinMoto, Color trailColor, String nom) {
        this.skinMoto = skinMoto;
        this.skinMotoView = new ImageView(skinMoto);
        this.trailColor = trailColor;
        this.nom = nom;
    }

    public Image getSkinMoto() {
        return skinMoto;
    }

    public void setSkinMoto(Image skinMoto, Color color) {
        this.skinMoto = skinMoto;
        this.skinMotoView = new ImageView(skinMoto);
        this.trailColor = color;
    }

    public ImageView getSkinMotoView() {
        return skinMotoView;
    }

    public int getTrailSize() {
        return trailSize;
    }

    public Color getTrailColor() {
        return trailColor;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static LinkedHashMap<VueSprite, Color> genererSprites() {
        LinkedHashMap<VueSprite, Color> sprites = new LinkedHashMap<>();
        sprites.put(new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/TronLegacyBleu.png")) , 60, 40, true, true), Color.LIGHTBLUE, "Motron Bleu"), Color.LIGHTBLUE);
        sprites.put(new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/TronLegacyJaune.png")), 60, 40, true, true), Color.YELLOW, "Motron Jaune"), Color.YELLOW);
        sprites.put(new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/Batmobile.png")),60,40,true,true),Color.rgb(148,135,83),"La nuit (voiture)"), Color.rgb(148,135,83));
        sprites.put(new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/Batpod.png")),60,40,true,true),Color.rgb(213,80,0),"La nuit (moto)"), Color.rgb(213,80,0));
        sprites.put(new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/MotoAkira.png")),60,40,true,true),Color.rgb(216,24,24),"Vengeance"), Color.rgb(216,24,24));
        sprites.put(new VueSprite(new Image(Objects.requireNonNull(Motron.class.getResourceAsStream("images/moto/SpeederAnakin.png")),60,40,true,true),Color.rgb(245,148,255),"Le Pod"), Color.rgb(245,148,255));
        return sprites;
    }

}
