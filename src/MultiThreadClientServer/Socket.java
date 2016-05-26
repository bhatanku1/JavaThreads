package MultiThreadClientServer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
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
	private int count;
	private int [] packetTracker = new int[4];
	DatagramSocket datagramSocket;
	DatagramPacket packet;
	private ByteArrayOutputStream baos;
	private DataOutputStream daos;
	public Socket(int clientPort, InetAddress clientAddress, int value){
		this.clientPort = clientPort;
		this.clientAddress = clientAddress;
		this.value = value;
		this.valueReceived = 0;
		try {
			datagramSocket = new DatagramSocket();
			LOGGER.info("Socket created at the server with port no:" + datagramSocket.getLocalPort());

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		baos=new ByteArrayOutputStream();
		daos=new DataOutputStream(baos);
		daos.writeInt(offset);
		daos.writeInt(length);
		daos.close();
		buffer = baos.toByteArray();
		packet = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
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
			
			for(int i = 0; i< length; i++) {
				byte[] buffer9 = new byte[50];
				packet = new DatagramPacket(buffer9, buffer9.length);
				LOGGER.info("Value of loop i is" + i);
				datagramSocket.receive(packet);
				UpdatePacketTracker(buffer9);

			}
			LOGGER.info("The data response received, nw validating the values received");
			UpdateOffsetAndLength();
			SendDataRequest();
			//LOGGER.info("Received packets from the Client with Address:" + packet.getAddress() + " and port:" + packet.getPort());
			//buffer = "Message".getBytes();
			//packet = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
			//datagramSocket.send(packet);
		}
		
	
	private void UpdateOffsetAndLength() {
		LOGGER.info("Updating the value of offset and length");
		int loopcounter;
		if(value - valueReceived >=4) loopcounter = 4;
		else loopcounter = value - valueReceived;
		LOGGER.info("Value of loopcounter is : "+ loopcounter);
		for (int i = 0; i<loopcounter;i++){
			if(packetTracker[i] == 0){
				offset= i + (4*count);
				length = 1;
				LOGGER.info("Packet at position: " + i + "not received");
				LOGGER.info("OFFSET is: " + offset + "and length is " + length);
				return;
			}
		}
		valueReceived += 4;
		count++;
		offset =  count * 4;
		if (valueReceived >= value) offset = -1;
		if (value - valueReceived >=4) length = 4;
		else length = value - valueReceived;
		LOGGER.info("Data request completed: New OFFSET is: " + offset + "and length is " + length);

		for(int i = 0; i<4;i++) packetTracker[i] = 0;
		LOGGER.info("Values initialized to 0 in packetTracer: " + packetTracker[0] + packetTracker[1] + packetTracker[2] + packetTracker[3]);

		// TODO Auto-generated method stub
		
	}
	private void UpdatePacketTracker(byte[] buffer10) {
		int valueReceived;
		valueReceived = ParsePacket(buffer10);
		LOGGER.info("value received is" + valueReceived);
		try{
			packetTracker[valueReceived - 1 - ((4 * count))] = 1;
			LOGGER.info("Values updated in packetTracer: " + packetTracker[0] + packetTracker[1] + packetTracker[2] + packetTracker[3]);
		}catch(ArrayIndexOutOfBoundsException e){
			LOGGER.info("Dropping this packet");
			e.printStackTrace();
		}
		
	}
	private int ParsePacket(byte[] buffer11) {
		 ByteArrayInputStream bais = new ByteArrayInputStream(buffer11);
		 DataInputStream dais	=new DataInputStream(bais);
		 
		 
			try {
				return dais.readInt();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		// TODO Auto-generated method stub
	}
	
}
