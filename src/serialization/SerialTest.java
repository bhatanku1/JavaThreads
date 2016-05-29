package serialization;

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

public class SerialTest {
	private  ByteArrayOutputStream baos;
	private ObjectOutputStream oout;
	private ByteArrayInputStream in;
	private ObjectInputStream is;
	public SerialTest() {
		
		
	}
	
	public byte[] serialize(Object s){
		baos = new ByteArrayOutputStream();
		try {
			oout = new ObjectOutputStream(baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	SerialClass s = new SerialClass(3, "ankur");
	SerialClass1 sc1 = new SerialClass1(3, "bhatia");
Object o;
	SerialClass s1;
	SerialClass1 sc12;
	SerialTest t = new SerialTest();
	byte [] buffer = new byte[100];
	buffer = t.serialize(s);
	
	o =  t.deserialize(buffer);
	buffer = new byte[100];
	buffer = t.serialize(sc1);
	//o =  t.deserialize(buffer);
	//sc12 = (SerialClass1) t.deserialize(buffer);
	//System.out.println(s1.getName());
	System.out.println(o.getClass().getName());
	//System.out.println(sc12.getLname());

}
}
