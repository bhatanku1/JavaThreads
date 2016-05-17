package SimpleThread;


public class ExtendThread {
	public static class AnkurThread extends Thread{
		public void run(){
			System.out.println("Hello");
		}
	}
	public static void main(String [] args) {
		Thread myThread = new AnkurThread();
		myThread.start();
	}
}
