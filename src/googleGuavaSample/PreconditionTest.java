package googleGuavaSample;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;


public class PreconditionTest {
	private int a;
	private String name;
	public PreconditionTest(int a, String name){
		this.name = checkNotNull(name);
		checkArgument(a>0, "a should be greater than 0");
		this.a = a;		
	}
	public static void main(String[] args){
		PreconditionTest t = new PreconditionTest(0, "");
	}

}
