package games.project.factory_fall.logique;

public enum Forme {
    NULL(" "),
    I("I"),
    O("O"),
    T("T"),
    S("S"),
    Z("Z"),
    J("J"),
    L("L");

    private final String nom;

    Forme(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

}
