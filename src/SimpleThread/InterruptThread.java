package SimpleThread;

public class InterruptThread implements Runnable {

	@Override
	public void run() {
		System.out.println("Child Thread Started with Priority " + Thread.currentThread().getPriority());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Inturrepted");
			e.printStackTrace();
		}
	}
	
public static void main(String [] args) {
	InterruptThread interruptThread = new InterruptThread();
	Thread t1 = new Thread(interruptThread);
	t1.setPriority(8);
	t1.start();
	System.out.println("About to inturrpt with the priority " + Thread.currentThread().getPriority());
	System.out.println("Check if inturrupted  " + t1.isInterrupted() );

	t1.interrupt();
	System.out.println("Done with interruption");
	System.out.println("Check if inturrupted  " + t1.isInterrupted() );

}
}
