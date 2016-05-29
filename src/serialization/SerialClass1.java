package serialization;

import java.io.Serializable;

public class SerialClass1 implements Serializable{
	private float sal;
	private String lname;
	public SerialClass1(float roll, String lname){
		this.sal = roll;
		this.lname = lname;
	}
	public float getSal() {
		return sal;
	}
	public String getLname() {
		return lname;
	}
	
}
