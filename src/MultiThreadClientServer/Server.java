package MultiThreadClientServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Logger;

public class Server {
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName()); 
	private DatagramSocket datagramSocket;
	private DatagramPacket datagramPacket;
	private byte[] buffer;
	public Server() {
		try {
			datagramSocket = new DatagramSocket(2514);
			buffer = new byte[10];
			datagramPacket = new DatagramPacket(buffer, buffer.length);
			while(true){
				datagramSocket.receive(datagramPacket);
				//Whenever a message is received on this thread, Server initiates a new Connection 
				//with the client through a new socket.
				HandleClientConnection();
			}
		} catch (SocketException e) {
			LOGGER.severe("SocketExeption: " + e.getMessage());
		} 
		catch (IOException e){
			LOGGER.severe("IOEXCEPTION" + e.getMessage());
		}
	}
	private void HandleClientConnection() {
		Thread threadForClient = new Socket(datagramPacket.getPort(), datagramPacket.getAddress());
		threadForClient.start();
		LOGGER.info("Starting the Child Thread to initiate the connection");
	}

}
