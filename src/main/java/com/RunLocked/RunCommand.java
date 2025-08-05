package com.RunLocked;

import java.io.IOException;

public class RunCommand implements Runnable {
	private String command;
	private int times;

	public RunCommand(String message, int times) {
		command = message; this.times = times;
	}
	private ProcessBuilder processBuilder = new ProcessBuilder();


	@Override
	public void run() {
		adjustSpeed(command, times);
	}

	private void adjustSpeed(String command, int times){
		processBuilder.command("cmd.exe", "/c", command);
		try {
			for(int i =0; i < times; i++){
				Thread.sleep(2000); // Pause .25 seconds to let the command process.
				processBuilder.start();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // Restore the interrupted status
			System.out.println("Thread was interrupted: " + e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
