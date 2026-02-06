package com.RunLocked;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.Client;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;


@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class RunlockPlugin extends Plugin
{
	@Inject
	private Client client;
	@Inject
	private OverlayManager overlayManager;
	@Inject WeightOverlay weightOverlay;
	@Inject
	private ClientToolbar clientToolbar;
	private NavigationButton navButton;

	private TreadmillPanel pluginPanel;

	private ProcessBuilder processBuilder = new ProcessBuilder();
	static boolean isRunning;
	CommandConfig config = new CommandConfig();
	private static double miles = 0.0;

	TimerTask task = new TimerTask() {
		@Override
		public void run() {

		}
	};

	Thread thread = new Thread();
	Timer timer;
	@Override
	protected void startUp() throws Exception{
		BufferedImage image = ImageIO.read(new File("C:\\Users\\Johnny\\IdeaProjects\\Runelite\\src\\main\\java\\com\\RunLocked\\tmIcon.jpg"));
		isRunning = false;
		initMiles();
		overlayManager.add(weightOverlay);
		pluginPanel = new TreadmillPanel(config);
		overlayManager.add(new MilesOverlay());
		navButton = NavigationButton.builder()
				.tooltip("Treadmill")
				.icon(image)
				.priority(6)
				.panel(pluginPanel)
				.build();

		clientToolbar.addNavigation(navButton);

	}
	@Override
	protected void shutDown() throws Exception{
		overlayManager.remove(weightOverlay);
		thread.interrupt();
		saveMiles();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGIN_SCREEN)
		{
			thread.interrupt();
			saveMiles();
		}
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
				processBuilder.command("cmd.exe", "/c", config.runCommand);
				processBuilder.start();
				thread = new Thread(new RunCommand(config.slowDownCommand, 10));
				thread.start();
			}else{
				processBuilder.command("cmd.exe", "/c", config.walkCommand);
				processBuilder.start();
				thread = new Thread(new RunCommand(config.slowDownCommand, 5));
				thread.start();
			}
		}
	}

	public static void updateMiles(){
		if(isRunning)
			miles += 0.02;
		else
			miles += 0.01;

	}


	public static double getMiles(){
		return miles;
	}

	private void initMiles(){
		String filePath = "C:\\Users\\Johnny\\IdeaProjects\\Runelite\\src\\main\\java\\com\\RunLocked\\miles"; // Replace with your file path

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				miles = Double.parseDouble(line);
			}
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
	}

	private void saveMiles(){
		try{
			FileWriter writer = new FileWriter("C:\\Users\\Johnny\\IdeaProjects\\Runelite\\src\\main\\java\\com\\RunLocked\\miles", false);
			writer.write(miles +"");
			writer.close();
			timer.cancel();
		}catch(Exception meOutsideHowBoutDat){

		}
	}
}

