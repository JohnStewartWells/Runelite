package com.RunLocked;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.PluginPanel;

import java.util.Timer;
import java.util.TimerTask;

import static com.RunLocked.RunlockPlugin.updateMiles;

@Slf4j
public class TreadmillPanel extends PluginPanel {

    static boolean isStopped = true;
    static Timer timer;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            System.out.println("Task executed" );
            updateMiles();
        }
    };
    private TimerTask getNewTask(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Task executed" );
                updateMiles();
            }
        };
        return task;
    }

    public TreadmillPanel(CommandConfig config) {
        super();

        Dimension buttonSize = new Dimension(150, 80);

        JButton slowDownButton = getButton("Slower", config.slowDownCommand);
        JButton speedUpButton = getButton("Faster", config.speedUpCommand);
        JButton stopButton = getButton("Stop", config.pauseCommand);
        JButton startButton = getButton("Start", config.playCommand);

        slowDownButton.setPreferredSize(buttonSize);
        add(slowDownButton);
        speedUpButton.setPreferredSize(buttonSize);
        add(speedUpButton);

        stopButton.setPreferredSize(buttonSize);
        add(stopButton);
        startButton.setPreferredSize(buttonSize);
        add(startButton);

    }


    private JButton getButton(String text, String command){
        JButton button = new JButton(text);
        button.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("cmd.exe", "/c", command);
                try {
                    processBuilder.start();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if(text.equals("Stop")){
                    isStopped = true;
                    if(timer != null)
                        timer.cancel();
                }
                else if(text.equals("Start")){
                    isStopped = false;
                    //in case we accidentally hit start more than once, we only want 1 timer.
                    if(timer != null)
                        timer.cancel();
                    createTimer();
                }
            });
        });
        return button;
    }

    public void createTimer() {
        timer = new Timer();
        timer.schedule(getNewTask(), 14400, 14400);
    }

}



