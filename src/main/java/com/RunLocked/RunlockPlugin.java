package com.RunLocked;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;

import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.Client;
import net.runelite.api.events.VarbitChanged;
import java.io.IOException;


@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class RunlockPlugin extends Plugin
{
	@Inject
	private Client client;

	private Player myPlayer;
	private int runAnimation;
	private int walkAnimation;
	private ProcessBuilder processBuilder = new ProcessBuilder();
	boolean leftClicked ;
	boolean isRunning;
	private boolean ctrlHeld ;

	String runCommand = "adb shell input tap 660 1650" ;
	String walkCommand = "adb shell input tap 420 1650";
	String slowDownCommand = "adb shell input tap 192 1650";
	String speedUpCommand = "adb shell input tap 900 1650";

	Thread thread = new Thread();


	@Override
	protected void startUp() {
		isRunning = false;

	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged event) throws IOException {
		if (event.getVarpId() == 173)
		{
			// Run toggled
			int[] varps = client.getVarps();
			isRunning = varps[173] == 1;

			//end the currently running thread.
			thread.interrupt();

			if(isRunning){
				processBuilder.command("cmd.exe", "/c", runCommand);
				processBuilder.start();
				thread = new Thread(new RunCommand(slowDownCommand, 10));
				thread.start();
			}else{
				processBuilder.command("cmd.exe", "/c", walkCommand);
				processBuilder.start();
				thread = new Thread(new RunCommand(slowDownCommand, 5));
				thread.start();
			}
		}
	}


}

