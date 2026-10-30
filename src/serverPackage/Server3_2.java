package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server3_2 {
    private static final int PORT = 1234;

    private static int compteurOperations = 0;

    // Méthode synchronized : un seul thread incrémente le compteur à la fois.
    public static synchronized int incrementerCompteur() {
        compteurOperations++;
        System.out.println("Nombre total d'opérations : " + compteurOperations);
        return compteurOperations;
    }

    public static void main(String[] args) {
        try (ServerSocket serveur = new ServerSocket(PORT)) {
            System.out.println("Serveur démarré sur le port " + PORT);

            while (true) {
                Socket socketClient = serveur.accept();
                System.out.println(
                        "Client connecté : " + socketClient.getInetAddress());

                Thread thread = new Thread(
                        new ClientProcess_3_2(socketClient));
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Erreur du serveur : " + e.getMessage());
        }
    }
}
