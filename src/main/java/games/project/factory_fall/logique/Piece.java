package games.project.factory_fall.logique;

/**
 * Les pièces sont définies par un nom
 */
public class Piece {

    private String nom;
    private final int[][] coords;

    public Piece(Forme forme) {
        int[][][] casesDefaut = {
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}}, //NULL
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}}, //I
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}}, //O
                {{0, -1}, {0, 0}, {0, 1}, {1, 0}}, //T
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}}, //S
                {{1, -1}, {1, 0}, {0, 0}, {0, 1}}, //Z
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}}, //J
                {{0, -1}, {0, 0}, {0, 1}, {1, 1}} //L
        };

        this.nom = forme.getNom();
        coords = casesDefaut[forme.ordinal()]; // On attribue la position relative des cases correspondantes au nom
    }

    public String getNom() {
        return this.nom;
    }

    public int[][] getForme() {
        return coords;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Créé une nouvelle pièce, y inscrit des positions de cases tournées par rapport à this. Si la pièce est O,
     * on la retourne tout de suite
     *
     * @param sens : de type char, indique la transformation à appliquer sur this selon le
     *             sens dans lequel on veut tourner
     * @return la nouvelle pièce
     */
    public Piece creerPieceTournee(char sens) {
        if (this.getNom().equals("O")) return this;
        else {
            Piece nouvellePiece = new Piece(Forme.NULL);
            if (sens == 'd') {
                for (int i = 0; i < 4; i++) {
                    nouvellePiece.coords[i][0] = this.coords[i][1] * -1;
                    nouvellePiece.coords[i][1] = this.coords[i][0];
                }
            } else if (sens == 'g') {
                for (int i = 0; i < 4; i++) {
                    nouvellePiece.coords[i][0] = this.coords[i][1];
                    nouvellePiece.coords[i][1] = this.coords[i][0] * -1;
                }
            }
            nouvellePiece.setNom(this.getNom());
            return nouvellePiece;
        }
    }
}
