package MultiThreadClientServer;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server {
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName()); 
	private DatagramSocket datagramSocket;
	private DatagramPacket datagramPacket;
	private ByteArrayInputStream bais;
	private DataInputStream dais;
	private byte[] buffer;
	private int value = 5;
	private Executor pool = Executors.newFixedThreadPool(5);
	public Server() {
		try {
			datagramSocket = new DatagramSocket(2519);
			buffer = new byte[50];
			datagramPacket = new DatagramPacket(buffer, buffer.length);
			
			while(true){
				datagramSocket.receive(datagramPacket);
				bais = new ByteArrayInputStream(buffer);
				dais=new DataInputStream(bais);
				value = dais.readInt();
				System.out.println(value);
				LOGGER.info("Value received from the client is: " + value);

				//Whenever a message is received on this thread, Server initiates a new Connection 
				//with the client through a new socket.
				HandleClientConnection(value);
			}
		} catch (SocketException e) {
			LOGGER.severe("SocketExeption: " + e.getMessage());
		} 
		catch (IOException e){
			LOGGER.severe("IOEXCEPTION" + e.getMessage());
		}
		finally{
			datagramSocket.close();
		}
	}
	private void HandleClientConnection(int value) {
		Socket threadForClient = new Socket(datagramPacket.getPort(), datagramPacket.getAddress(), value);
		pool.execute(threadForClient);
		//threadForClient.start();
		LOGGER.info("Starting the Child Thread to initiate the connection");
	}

}
