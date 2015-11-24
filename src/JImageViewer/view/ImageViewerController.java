package JImageViewer.view;

import JImageViewer.MainApp;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


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

        mainApp.getCurrentImage().imageProperty().addListener(((observable, oldValue, newValue) ->
                imageView.setImage(newValue)));
    }
}