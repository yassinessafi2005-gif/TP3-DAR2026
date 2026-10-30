package commun;

import java.io.Serializable;

public class ReponseCalcul implements Serializable {
    private static final long serialVersionUID = 1L;

    private final boolean succes;
    private final double resultat;
    private final int nombreOperations;
    private final String message;

    public ReponseCalcul(
            boolean succes,
            double resultat,
            int nombreOperations,
            String message) {
        this.succes = succes;
        this.resultat = resultat;
        this.nombreOperations = nombreOperations;
        this.message = message;
    }

    public boolean isSucces() {
        return succes;
    }

    public double getResultat() {
        return resultat;
    }

    public int getNombreOperations() {
        return nombreOperations;
    }

    public String getMessage() {
        return message;
    }
}

