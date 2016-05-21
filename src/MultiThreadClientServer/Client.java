package MultiThreadClientServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {
	private final static Logger LOGGER = Logger.getLogger(Client.class.getName()); 
	private DatagramSocket  datagramSocket;
	private DatagramPacket datagramPacket;
	private static int PORT = 2519;
	private Scanner scanner;
	private String reply;
	private InetAddress serverAddress;
	byte [] buffer;
	public Client() {
		try {
			datagramSocket = new DatagramSocket();
			buffer = new byte[50];
			serverAddress = InetAddress.getLocalHost();
			datagramPacket = new DatagramPacket(buffer, buffer.length,serverAddress, PORT);
			datagramSocket.send(datagramPacket);
			scanner = new Scanner(System.in);
			while(true){
				datagramSocket.receive(datagramPacket);
				LOGGER.info("Received the packet from the port: " + datagramPacket.getPort());
				buffer = scanner.nextLine().getBytes();
				datagramPacket = new DatagramPacket(buffer, buffer.length,serverAddress, datagramPacket.getPort());
				datagramSocket.send(datagramPacket);

			}
			
		} catch (SocketException e) {
			LOGGER.severe("Socket not Created" + e.getMessage());
		} catch (UnknownHostException e) {
			LOGGER.severe("UnKnownHost: " + e.getMessage());
		} catch (IOException e) {
			LOGGER.severe("IOExeption: " + e.getMessage());
		}
	}
	


}
