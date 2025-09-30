package com.RunLocked;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;

import java.awt.event.MouseAdapter;
import java.util.Timer;
import java.util.TimerTask;

import static com.RunLocked.RunlockPlugin.updateMiles;


@Slf4j
public class TreadmillPanel extends PluginPanel {

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

    public TreadmillPanel(CommandConfig config) throws IOException {
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
                if(text.equals("Stop") && timer != null)
                    timer.cancel();
                if(text.equals("Start"))
                    createTimer();
            });
        });
        return button;
    }

    public void createTimer() {
        timer = new Timer();
        timer.schedule(getNewTask(), 14400, 14400);
    }



}



