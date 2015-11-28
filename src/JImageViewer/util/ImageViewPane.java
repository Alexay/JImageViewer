package JImageViewer.util;

/**
 * A custom Pane that resizes itself automatically with the window to make sure the image occupies
 * as much space as possible.
 */

import JImageViewer.MainApp;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

import java.awt.*;

public class ImageViewPane extends Region {

    private ObjectProperty<ImageView> imageViewProperty = new SimpleObjectProperty<ImageView>();

    public ObjectProperty<ImageView> imageViewProperty() {
        return imageViewProperty;
    }

    public ImageView getImageView() {
        return imageViewProperty.get();
    }

    public void setImageView(ImageView imageView) {
        this.imageViewProperty.set(imageView);
    }

    public ImageViewPane() {
        this(new ImageView());
    }

    @Override
    protected void layoutChildren() {
        ImageView imageView = imageViewProperty.get();
        if (imageView != null) {
            imageView.setFitWidth(getWidth());
            imageView.setFitHeight(getHeight());
            imageView.setPreserveRatio(true);
            layoutInArea(imageView, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
        }
        super.layoutChildren();
    }

    public ImageViewPane(ImageView imageView) {
        imageViewProperty.addListener(new ChangeListener<ImageView>() {

            @Override
            public void changed(ObservableValue<? extends ImageView> arg0, ImageView oldIV, ImageView newIV) {
                if (oldIV != null) {
                    getChildren().remove(oldIV);
                }
                if (newIV != null) {
                    getChildren().add(newIV);
                }
            }
        });
        this.imageViewProperty.set(imageView);
    }

    public void initializeListeners(MainApp mainApp) throws Exception {

        // Robot to trace pixel information
        Robot robot = new Robot();

        /**
         * This method is used to get the information about a pixel inside the ImageViewer over which the mouse
         * is currently hovering. Which then gets sent to the MainApp to be passed on to the StatusBar
         */
        mainApp.getImageData().getImageView().setOnMouseEntered(event -> {
            Color color = robot.getPixelColor((int) event.getScreenX(), (int) event.getScreenY());

            // Initializing pixel info
            String xPos = Integer.toString((int) event.getX());
            String yPos = Integer.toString((int) event.getY());
            String colorRed = Integer.toString(color.getRed());
            String colorBlue = Integer.toString(color.getBlue());
            String colorGreen = Integer.toString(color.getGreen());
            String hexColor = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());

            // Unify and format the information
            String pixelInfo = "X: " + xPos + " Y: " + yPos + " | "
                    + "R: " + colorRed + " G: " + colorGreen +
                    " B: " + colorBlue + " | " + hexColor;

            // Pass it on to the MainApp
            mainApp.getPixelInfo().setInfoString(pixelInfo);

        });
        mainApp.getImageData().getImageView().setOnMouseMoved(event -> {
            Color color = robot.getPixelColor((int) event.getScreenX(), (int) event.getScreenY());

            // Initializing pixel info
            String xPos = Integer.toString((int) event.getX());
            String yPos = Integer.toString((int) event.getY());
            String colorRed = Integer.toString(color.getRed());
            String colorBlue = Integer.toString(color.getBlue());
            String colorGreen = Integer.toString(color.getGreen());
            String hexColor = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());

            // Unify and format the information
            String pixelInfo = "X: " + xPos + " Y: " + yPos + " | "
                    + "R: " + colorRed + " G: " + colorGreen +
                    " B: " + colorBlue + " | " + hexColor;

            // Pass it on to the MainApp
            mainApp.getPixelInfo().setInfoString(pixelInfo);
        });

    }
}