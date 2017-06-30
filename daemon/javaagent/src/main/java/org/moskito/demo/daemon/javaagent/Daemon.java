package org.moskito.demo.daemon.javaagent;

import java.util.Random;

/**
 * This class emulates a server. It even has a main method! ;-)
 *
 * @author lrosenberg
 * @since 09.04.14 23:12
 */
public class Daemon {
	public static void main(String args[]) throws InterruptedException{
		try {
			DaemonThread daemon = new DaemonThread();
			daemon.start();

			//wait forever.
			daemon.join();
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	final static class DaemonThread extends Thread{
		public void run(){
			System.out.println("Daemon started. Will print something out every 60 seconds or so.");
			Random rnd = new Random(System.nanoTime());
			int sleepCounter = 0;
			long tasks = 0L;
			Worker worker = new Worker();
			while(!Thread.currentThread().isInterrupted()){
				try{
					Thread.sleep(20000);
				}catch(InterruptedException ignored){}

				int tasksThisTurn = rnd.nextInt(10);
				for (int i=0; i<tasksThisTurn; i++){
					worker.doSomeWork();
					tasks++;
				}

				sleepCounter++;
				if (sleepCounter==3){
					sleepCounter = 0;
					System.out.println("I am still alive, and I performed "+tasks+" tasks.");
				}
			}
		}
	}
}
