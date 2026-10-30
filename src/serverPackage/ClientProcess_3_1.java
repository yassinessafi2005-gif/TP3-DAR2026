package serverPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProcess_3_1 extends Thread{
    private Socket socket;
    private int numero;

    public ClientProcess_3_1(Socket socket,int numero){
        this.socket=socket;
        this.numero=numero;
    }
    @Override
    public void run() {
        try (
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);)

// c) Envoyer à chaque client son numéro d'ordre pw.println("Bienvenue, vous êtes le client n°" + numero);
            {
                pw.println("Bienvenue, client numero " + numero);
                String ligne;
// Dialogue indépendant avec ce client (lecture de lignes) String ligne = br.readLine();
            while ((ligne = br.readLine()) != null && !ligne.equals("quit")) {
                System.out.println("[client n°" + numero + "] a envoyé : " + ligne);
                pw.println("Echo : " + ligne);
            }
            System.out.println("Client n°" + numero + " déconnecté");
        } catch (IOException e) {
            System.out.println("Erreur client n°" + numero + " : " + e.getMessage());
        } finally {
            try {
                socket.close(); // chaque thread ferme SA socket } catch (IOException e) {
// rien à faire }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}