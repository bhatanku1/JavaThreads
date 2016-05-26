package MultiThreadClientServer;
/*The following is a simple example to serialize and deserialize an integer
 * using ByteArrayInputStream, ByteArrayOutputStream, DataInputStream & 
 * DataOutputStream. The same can be used for class objects as well. This
 * is shown is another example
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TestSerial {
	private int input;
	private int output;
	private byte [] bufferIn = new byte[10];
	
	private ByteArrayOutputStream baos;
    private DataOutputStream daos;
    
    ByteArrayInputStream bais;
    DataInputStream dais;
    public TestSerial(){
    	baos=new ByteArrayOutputStream();
    	daos=new DataOutputStream(baos);
    	input = 5;
    	
    	try {
			daos.writeInt(input);
			daos.close();
			bufferIn = baos.toByteArray();
			daos.writeInt(8);
			daos.close();
			bufferIn = baos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
   
    public int ConverToInt(){
    	bais = new ByteArrayInputStream(bufferIn);
		dais=new DataInputStream(bais);
		try {
			output = dais.readInt();
			output = dais.readInt();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
    	
    }
    public static void main(String[] args){
    	TestSerial t = new TestSerial();
    	int [] ab = new int[3];
    	int a = t.ConverToInt();
    	System.out.println("Value after serialization/deserialization: " + a );
    	System.out.println("Value after serialization/deserialization: " + ab[0] + ab[1] +ab[2] );

    }
}
  