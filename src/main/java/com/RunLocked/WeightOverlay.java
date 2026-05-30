package com.RunLocked;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;

public class WeightOverlay extends Overlay {

    @Inject
    private Client client;

    private final PanelComponent panelComponent = new PanelComponent();

    public WeightOverlay() {
        setPosition(OverlayPosition.TOP_LEFT); // Set the default position
        setResizable(true); // Allow the overlay to be resized
        setPreferredSize(new Dimension(300, 100)); // Set preferred size
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        int weight = client.getWeight();
        int weightInLbs = Math.toIntExact(Math.round((weight * 2.20462)));
        String weightStr = weight + " Kg. / " + weightInLbs + "Lbs.";
        panelComponent.getChildren().clear(); // Clear previous content

        // Add content to the overlay
        panelComponent.getChildren().add(TitleComponent.builder()
                .text("Current Weight:")
                .build());
        panelComponent.getChildren().add(TitleComponent.builder()
                .text(weightStr).build());

        return panelComponent.render(graphics); // Render the panel component
    }
}

