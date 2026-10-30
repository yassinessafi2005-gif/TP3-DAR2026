package clientPackage;

import commun.ReponseCalcul;
import commun.RequeteCalcul;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client3_2 {
    private static final String SERVEUR = "localhost";
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVEUR, PORT);
                ObjectOutputStream sortie =
                        new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entree =
                        new ObjectInputStream(socket.getInputStream());
                Scanner clavier = new Scanner(System.in)
        ) {
            sortie.flush();

            System.out.println("Connecté au serveur.");
            System.out.println("Opérations disponibles : +  -  *  /");
            System.out.println("Tapez q pour quitter.");

            while (true) {
                System.out.print("\nPremier nombre : ");
                String saisie = clavier.nextLine();

                if (saisie.equalsIgnoreCase("q")) {
                    break;
                }

                double a = Double.parseDouble(saisie);

                System.out.print("Opération (+, -, *, /) : ");
                char operation = clavier.nextLine().charAt(0);

                System.out.print("Deuxième nombre : ");
                double b = Double.parseDouble(clavier.nextLine());

                RequeteCalcul requete =
                        new RequeteCalcul(a, b, operation);

                sortie.writeObject(requete);
                sortie.flush();
                sortie.reset();

                ReponseCalcul reponse =
                        (ReponseCalcul) entree.readObject();

                if (reponse.isSucces()) {
                    System.out.println("Résultat : " + reponse.getResultat());
                    System.out.println(
                            "Opération numéro : "
                                    + reponse.getNombreOperations());
                } else {
                    System.out.println("Erreur : " + reponse.getMessage());
                }
            }

        } catch (NumberFormatException e) {
            System.err.println("Veuillez saisir un nombre valide.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
