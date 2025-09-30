package com.RunLocked;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;
import java.text.DecimalFormat;

import static com.RunLocked.RunlockPlugin.getMiles;
import static com.RunLocked.RunlockPlugin.isRunning;


public class MilesOverlay extends Overlay {

    @Inject
    private Client client;
    private DecimalFormat df = new DecimalFormat("#.##");
    private final PanelComponent panelComponent = new PanelComponent();

    public MilesOverlay(double miles) {
        setPosition(OverlayPosition.TOP_LEFT); // Set the default position
        setResizable(true); // Allow the overlay to be resized
        setPreferredSize(new Dimension(300, 100)); // Set preferred size
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        panelComponent.getChildren().clear(); // Clear previous content

        // Add content to the overlay
        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Total Miles: " + df.format(getMiles()))
                .build());
        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Current Speed: " +( isRunning?"5 mph":"2.5 mph" ))
                .build());


        return panelComponent.render(graphics); // Render the panel component
    }
}

