package ServeurPackage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Serveur {
	// na3mlouhom static khtr houma statiquement aal classe w manestha9sh nkharjhom
	// bara m serveur
	// parametre propre pour le classe serveur
	private static final int PORT = 1234;
	private static byte[] buffer = new byte[1024];

	public static void main(String[] args) throws IOException {
//reservation du port w 3la  kol client 		
		DatagramSocket socket = new DatagramSocket(1234);
		System.out.println("demarrage de serveur ");
		while (true) {
			/* f act 2  nahi user naame w nhot socket.receive(packet); w nh7ot datagram feha time.getbyte() , 
			 * time.length() w nbadl usernale eli f datagram b packet */
			DatagramPacket userNamePacket = new DatagramPacket(buffer, buffer.length);
			socket.receive(userNamePacket);// 9rit beha donne mn aand client
//Transformation m octet l string 
//.getData taatini tab de donne w naaytlha b esm packet w indice debut w indice de fin mtaa transformation 
//m ema indice bdit tekhou 
			String userName = new String(userNamePacket.getData(), 0, userNamePacket.getLength());
//System.out.println("le client " + userName + " est connecté ") ou avec adresse n

			System.out.println(userNamePacket.getAddress() + " : " + userName);
			String userName1 = "Bienvenu" + userName;
		
			DatagramPacket msgtoSend = new DatagramPacket(userName1.getBytes(), userName1.length(),
					userNamePacket.getAddress(), userNamePacket.getPort());

			socket.send(msgtoSend);
		}
	}

}
