package JImageViewer.view;

import JImageViewer.MainApp;
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
    @FXML
    private void initialize() {

        /**
         * This method is used to get the information about a pixel inside the ImageViewer over which the mouse
         * is currently hovering. Which then gets sent to the MainApp to be passed on to the StatusBar
         */
        this.imageView.setOnMouseEntered(event -> { try {

            // Robot to trace pixel information
            Robot robot = new Robot();
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
                    + "r: " + colorRed + " g: " + colorGreen +
                    " b: " + colorBlue + " | " + hexColor;

            // Pass it on to the MainApp
            this.mainApp.getPixelInfo().setInfoString(pixelInfo);

        } catch (Exception ignore){}});
        this.imageView.setOnMouseMoved(event -> { try {

            // Robot to trace pixel information
            Robot robot = new Robot();
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
                    + "r: " + colorRed + " g: " + colorGreen +
                    " b: " + colorBlue + " | " + hexColor;

            // Pass it on to the MainApp
            this.mainApp.getPixelInfo().setInfoString(pixelInfo);

        } catch (Exception ignore){}});

    }

    /**
     * Is called by the main application to give a reference back to itself.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        imageView.setImage(mainApp.getCurrentImage().getImage());

        // Listener for the current image that sets the current image as the image to be in the ImageView
        mainApp.getCurrentImage().imageProperty().addListener(((observable, oldValue, newValue) ->
                imageView.setImage(newValue)));

    }
}