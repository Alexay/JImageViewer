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
    @FXML
    private void initialize()  {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        imageView.setImage(mainApp.getCurrentImage().getImage());

        // Listener for the current image that sets the current image as the image to be in the ImageView
        mainApp.getCurrentImage().imageProperty().addListener(((observable, oldValue, newValue) ->
                imageView.setImage(newValue)));

    }

    /**
     * This method is used to get the information about a pixel inside the ImageViewer over which the mouse
     * is currently hovering. Which then gets sent to the MainApp to be passed on to the StatusBar
     */

    @FXML
    private void mouseHoverInfo() {

//        ImageViewInternalMouseHoverTracker ivimht = new ImageViewInternalMouseHoverTracker(this.imageView);
//
//        String xPos = ivimht.getX();
//        String yPos = ivimht.getY();
//        String colorRed = ivimht.getR();
//        String colorBlue = ivimht.getG();
//        String colorGreen = ivimht.getB();
//        String hexColor = ivimht.getHex();
//
//        String pixelInfo = "X: " + xPos + " Y: " + yPos + " | "
//                + "r: " + colorRed + " g: " + colorGreen +
//                " b: " + colorBlue + " | " + hexColor;
//
//        mainApp.getPixelInfo().setInfoString(pixelInfo);
    }
}