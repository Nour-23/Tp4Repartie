package ClientPackage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private static final int PORT = 1234;
	private static byte[] buffer = new byte[1024];

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DatagramSocket socket = new DatagramSocket(); // kif tabda datagramsocket fergha yaani raw client
		Scanner scan = new Scanner(System.in);
		String userName = scan.nextLine();
		DatagramPacket send = new DatagramPacket(userName.getBytes(), userName.length(),InetAddress.getByName("localhost"),PORT);
		socket.send(send);
		DatagramPacket receivedPacket = new DatagramPacket(buffer,buffer.length);
		socket.receive(receivedPacket);
		System.out.println("Serveur "+ new String(receivedPacket.getData(),0,receivedPacket.getLength()));
	
	}

}
