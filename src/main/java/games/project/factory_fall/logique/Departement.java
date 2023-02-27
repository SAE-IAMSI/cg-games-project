package games.project.factory_fall.logique;

public class Departement {

    private final String numDepartement;
    private final String nomDepartement;

    public Departement(String numDepartement, String nomDepartement) {
        this.numDepartement = numDepartement;
        this.nomDepartement = nomDepartement;
    }

    public String getNomDepartement() {
        return this.nomDepartement == null ? this.nomDepartement : "Département non renseigné";
    }

    public String getNumDepartement() {
        return this.numDepartement == null ? this.numDepartement : "Département non renseigné";
    }

    @Override
    public String toString() {
        return this.numDepartement + " - " + this.nomDepartement;
    }

}
