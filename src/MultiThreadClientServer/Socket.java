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
	private int value;
	private int valueReceived;
	private int offset;
	private int length;
	private int [] packetTracker = new int[4];
	DatagramSocket datagramSocket;
	DatagramPacket packet;
	final ByteArrayOutputStream baos=new ByteArrayOutputStream();
    final DataOutputStream daos=new DataOutputStream(baos);
	public Socket(int clientPort, InetAddress clientAddress, int value){
		this.clientPort = clientPort;
		this.clientAddress = clientAddress;
		this.value = value;
		this.valueReceived = 0;
		offset = 0;
		if(value >= 4) {
			length = 4;
		}
		else length = value;
	}
	public void run(){
		try {
			SendDataRequest();
		} catch (IOException e) {
			LOGGER.severe("Error in the Creation of Sockets: " + e.getMessage());
			e.printStackTrace();
		}
	}
	public void SendDataRequest() throws IOException, SocketException{
		datagramSocket = new DatagramSocket();
		
		daos.writeInt(offset);
		daos.writeInt(length);
		daos.close();
		buffer = baos.toByteArray();
		packet = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
		LOGGER.info("Socket created at the server with port no:" + datagramSocket.getLocalPort());
		datagramSocket.send(packet);	
		try{
			ReceiveData();
		}catch(SocketException e) {
			LOGGER.severe("SocketException at CommunicateWithClient: " + e.getMessage());
		}catch(IOException e){
			LOGGER.severe("IOException at CommunicateWithClient: " + e.getMessage());
		}
	}
	public void ReceiveData() throws SocketException, IOException{
		while(valueReceived < value) {
			int count = 1;
			for(int i = 0; i< length; i++) {
				datagramSocket.receive(packet);
				UpdatePacketTracker(packet);

			}
			UpdateOffsetAndLength(count);
			SendDataRequest();
			//LOGGER.info("Received packets from the Client with Address:" + packet.getAddress() + " and port:" + packet.getPort());
			//buffer = "Message".getBytes();
			//packet = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
			//datagramSocket.send(packet);
		}
		
	}
	private void UpdateOffsetAndLength(count) {
		for (int i = offset; i<offset+4;i++){
			if(packetTracker[i] == 0){
				offset= i;
				length = 1;
				return;
			}
		}
		valueReceived += 4;
		offset += 4;
		if (value - valueReceived >=4) length = 4;
		else length = value - valueReceived;
		
		// TODO Auto-generated method stub
		
	}
	private void UpdatePacketTracker(DatagramPacket packet2) {
		int valueReceived;
		valueReceived = ParsePacket(packet2);
		try{
			packetTracker[valueReceived - 1 - offset] = 1;
		}catch(ArrayIndexOutOfBoundsException e){
			LOGGER.info("Dropping this packet");
			e.printStackTrace();
		}
		
	}
	private int ParsePacket(DatagramPacket packet2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
