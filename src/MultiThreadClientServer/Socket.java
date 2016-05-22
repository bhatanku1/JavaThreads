package MultiThreadClientServer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Logger;

public class Socket implements Runnable{
	private final static Logger LOGGER = Logger.getLogger(Socket.class.getName()); 
	private int clientPort;
	private InetAddress clientAddress;
	byte [] buffer;
	DatagramSocket datagramSocket;
	DatagramPacket packet;
	final ByteArrayOutputStream baos=new ByteArrayOutputStream();
    final DataOutputStream daos=new DataOutputStream(baos);
	public Socket(int clientPort, InetAddress clientAddress){
		this.clientPort = clientPort;
		this.clientAddress = clientAddress;
	}
	public void run(){
		try {
			InitiateConnectionWithClients();
		} catch (IOException e) {
			LOGGER.severe("Error in the Creation of Sockets: " + e.getMessage());
			e.printStackTrace();
		}
	}
	public void InitiateConnectionWithClients() throws IOException, SocketException{
		datagramSocket = new DatagramSocket();
		daos.writeInt(3);
		daos.writeInt(4);
		
		daos.close();
		buffer = baos.toByteArray();
		//buffer = new byte[10];
		//buffer = "Connected".getBytes();
		packet = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
		LOGGER.info("Socket created at the server with port no:" + datagramSocket.getLocalPort());
		datagramSocket.send(packet);	
		try{
			CommunicateWithClient();
		}catch(SocketException e) {
			LOGGER.severe("SocketException at CommunicateWithClient: " + e.getMessage());
		}catch(IOException e){
			LOGGER.severe("IOException at CommunicateWithClient: " + e.getMessage());
		}
	}
	public void CommunicateWithClient() throws SocketException, IOException{
		while(true) {
			datagramSocket.receive(packet);
			LOGGER.info("Received packets from the Client with Address:" + packet.getAddress() + " and port:" + packet.getPort());
			buffer = "Message".getBytes();
			packet = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
			datagramSocket.send(packet);
		}
	}
	
}
