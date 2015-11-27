package JImageViewer.view;

import JImageViewer.MainApp;
import JImageViewer.util.ImageViewPane;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.awt.*;


public class ImageViewerController {

    @FXML
    private ImageView imageView;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ImageViewerController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    private void initialize() throws Exception {

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
            this.mainApp.getPixelInfo().setInfoString(pixelInfo);

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
            this.mainApp.getPixelInfo().setInfoString(pixelInfo);
        });

    }

    /**
     * Is called by the main application to give a reference back to itself.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        imageView.setImage(this.mainApp.getImageData().getImage());

        // Listener for the current image that sets the current image as the image to be in the ImageView
        this.mainApp.getImageData().imageProperty().addListener(((observable, oldValue, newValue) ->
                imageView.setImage(newValue)));

    }
}