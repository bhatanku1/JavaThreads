package serialization;

import java.io.Serializable;

public class SerialClass implements Serializable {
	private int roll;
	private String name;
	public SerialClass(int roll, String name){
		this.roll = roll;
		this.name = name;
	}
	public int getRoll() {
		return roll;
	}
	public String getName() {
		return name;
	}
	

}
