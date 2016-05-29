package serializationSockets;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
/*Extremely important to note that the size of the byte array has to big enough 
 * to fit the entire packet received in the socket. else a eofException
 * is raised. 
 */
public class Server {
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName()); 
	private DatagramSocket datagramSocket;
	private DatagramPacket datagramPacket;
	private ByteArrayInputStream bais;
	private ObjectInputStream dais;
	private byte[] buffer;
	private int value = 5;
	SerialClass s;
	private Executor pool = Executors.newFixedThreadPool(5);
	public Server() {
		try {
			datagramSocket = new DatagramSocket(12121);
			buffer = new byte[107];
			datagramPacket = new DatagramPacket(buffer, buffer.length);
			
			while(true){
				datagramSocket.receive(datagramPacket);
				LOGGER.info("Received");
				bais = new ByteArrayInputStream(buffer);
				dais=new ObjectInputStream(bais);
				s =  (SerialClass) dais.readObject();
				//System.out.println(s.getName());
				LOGGER.info("Value received from the client is: " + s.getName() + s.getRoll());

				//Whenever a message is received on this thread, Server initiates a new Connection 
				//with the client through a new socket.
				//HandleClientConnection(value);
			}
		} catch (SocketException e) {
			LOGGER.severe("SocketExeption: " + e.getMessage());
		} 
		catch (IOException e){
			LOGGER.severe("IOEXCEPTION" + e.getMessage());
			try {
				dais.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String [] args){
		Server s = new Server();
	}

}
