package Client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    private static final int PORT = 1234;
    private static final String SERVER_ADDRESS = "localhost";

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Entrez votre nom d'utilisateur : ");
            String username = scanner.nextLine();

            System.out.println("Connecté au serveur. Commencez à taper vos messages.");

            // Thread pour recevoir les messages du serveur
            new Thread(() -> receiveMessages(socket)).start();

            while (true) {
                System.out.print(username + " : ");
                String message = scanner.nextLine();
                sendMessageToServer(socket, message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void receiveMessages(DatagramSocket socket) {
        try {
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Serveur : " + receivedMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendMessageToServer(DatagramSocket socket, String message) {
        try {
            DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.length(),
                    InetAddress.getByName(SERVER_ADDRESS), PORT);
            socket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
