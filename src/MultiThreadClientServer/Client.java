package MultiThreadClientServer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	byte [] buffer1;
	int offset;
	int length;
	int value;
	private ByteArrayInputStream bais;
	private DataInputStream dais;
	final ByteArrayOutputStream baos=new ByteArrayOutputStream();
    final DataOutputStream daos=new DataOutputStream(baos);
    public Client(int value) {
		try {
			this.value = value;
			datagramSocket = new DatagramSocket();
			buffer = new byte[50];
			serverAddress = InetAddress.getLocalHost();
			datagramPacket = new DatagramPacket(buffer, buffer.length,serverAddress, PORT);
			datagramSocket.send(datagramPacket);
			scanner = new Scanner(System.in);
			datagramSocket.receive(datagramPacket);
			LOGGER.info("Received the packet from the port: " + datagramPacket.getPort() + " " + buffer.toString());
			bais = new ByteArrayInputStream(buffer);
			dais=new DataInputStream(bais);
			offset = dais.readInt();
			length = dais.readInt();
			System.out.println("Value recieved: " + offset + "  " + length);
			
			while(true){
				if(offset == -1 ) break;
				for (int i = offset + 1; i<= length; i++){
					System.out.print("Enter the value to be sent- >");
					value = scanner.nextInt();
					daos.writeInt(value);
					daos.close();
					buffer = baos.toByteArray();
					datagramPacket = new DatagramPacket(buffer, buffer.length,serverAddress, datagramPacket.getPort());
					datagramSocket.send(datagramPacket);
				}
				buffer1 = new byte[50];
				datagramPacket = new DatagramPacket(buffer1, buffer1.length);
				datagramSocket.receive(datagramPacket);
				LOGGER.info("Received the packet from the port: " + datagramPacket.getPort() + " " + buffer.toString());
				bais = new ByteArrayInputStream(buffer);
				dais=new DataInputStream(bais);
				offset = dais.readInt();
				length = dais.readInt();
				LOGGER.info("Value recieved: " + offset + "  " + length);

				
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
