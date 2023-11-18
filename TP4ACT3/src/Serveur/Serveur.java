package Serveur;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Serveur {
	private static final int PORT = 1234;
	private static final List<InetSocketAddress> clients = new ArrayList<>();

	public static void main(String[] args) {
		try (DatagramSocket serverSocket = new DatagramSocket(new InetSocketAddress("localhost", PORT))) {
			System.out.println("Serveur de chat démarré sur le port " + PORT);

			while (true) {
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);

				InetSocketAddress clientAddress = new InetSocketAddress(receivePacket.getAddress(),
						receivePacket.getPort());
				if (!clients.contains(clientAddress)) {
					clients.add(clientAddress);
					System.out.println("Nouveau client connecté: " + clientAddress);
				}

				// Traiter le message reçu et le transmettre à tous les clients
				String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
				broadcastMessage(message, clientAddress);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void broadcastMessage(String message, InetSocketAddress sender) {
		for (InetSocketAddress client : clients) {
			if (!client.equals(sender)) {
				sendMessageToClient(message, client);
			}
		}
	}

	private static void sendMessageToClient(String message, InetSocketAddress client) {
		try (DatagramSocket socket = new DatagramSocket()) {
			// Utilisation d'un seul DatagramSocket pour l'ensemble du processus d'envoi
			DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.length(), client);
			socket.send(sendPacket);
		} catch (Exception e) {
			e.printStackTrace();
			// Gérer l'exception de manière appropriée, par exemple, en supprimant le client
			// de la liste
		}
	}
}
