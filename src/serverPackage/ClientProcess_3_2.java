package serverPackage;

import commun.ReponseCalcul;
import commun.RequeteCalcul;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientProcess_3_2 implements Runnable {
    private final Socket socket;

    public ClientProcess_3_2(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream sortie =
                        new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entree =
                        new ObjectInputStream(socket.getInputStream())
        ) {
            sortie.flush();

            while (true) {
                Object objetRecu = entree.readObject();

                if (!(objetRecu instanceof RequeteCalcul)) {
                    break;
                }

                RequeteCalcul requete = (RequeteCalcul) objetRecu;

                try {
                    double resultat = calculer(
                            requete.getA(),
                            requete.getB(),
                            requete.getOperation());

                    int compteur =
                            Server3_2.incrementerCompteur();

                    ReponseCalcul reponse = new ReponseCalcul(
                            true,
                            resultat,
                            compteur,
                            "Calcul effectué");

                    sortie.writeObject(reponse);
                    sortie.flush();
                    sortie.reset();

                } catch (IllegalArgumentException e) {
                    ReponseCalcul reponse = new ReponseCalcul(
                            false,
                            0,
                            0,
                            e.getMessage());

                    sortie.writeObject(reponse);
                    sortie.flush();
                    sortie.reset();
                }
            }

        } catch (EOFException e) {
            System.out.println("Client déconnecté.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur avec le client : " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    private double calculer(double a, double b, char operation) {
        switch (operation) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new IllegalArgumentException(
                            "Division par zéro impossible.");
                }
                return a / b;
            default:
                throw new IllegalArgumentException(
                        "Opération inconnue : " + operation);
        }
    }
}
