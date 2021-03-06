package serializationSockets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.sun.media.jfxmedia.logging.Logger;

public class SerialTest {
	private  ByteArrayOutputStream baos;
	private ObjectOutputStream oout;
	private ByteArrayInputStream in;
	private ObjectInputStream is;
	public SerialTest() {
		baos = new ByteArrayOutputStream();
		try {
			oout = new ObjectOutputStream(baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public byte[] serialize(SerialClass s){
		try {
			try {
				oout.writeObject(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toByteArray();
		
	}
	public Object deserialize(byte[] b){
		in = new ByteArrayInputStream(b);
	    try {
			ObjectInputStream is = new ObjectInputStream(in);
			return is.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return null;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
		
		
	}
	
public static void main(String[] args){

	SerialClass s = new SerialClass(2500000, "");
	SerialClass s1;
	SerialTest t = new SerialTest();
	byte [] buffer = new byte[100];
	buffer = t.serialize(s);
	 try {
		DatagramSocket datagramSocket = new DatagramSocket();
		InetAddress server = InetAddress.getLocalHost();
		System.out.println("Length: " + buffer.length);
		DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length,server, 12121);
		datagramSocket.send(datagramPacket);


	} catch (SocketException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	s1 = (SerialClass) t.deserialize(buffer);
	System.out.println(s1.getName());
}
}
