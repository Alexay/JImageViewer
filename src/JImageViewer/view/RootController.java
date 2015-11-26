package JImageViewer.view;

import JImageViewer.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;

public class RootController {
    @FXML
    private MenuItem open;

    @FXML
    private MenuItem delete;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public RootController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    @FXML
    private void openImageFile () {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image types (*.jpg;  *.gif; *.bmp; *.png)", "*.jpg", "*.png", "*.bmp", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File imageFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        // Set the app's current image.
        mainApp.getCurrentImage().setImageFile(imageFile);
        mainApp.getCurrentImage().setImage(new Image(imageFile.toURI().toString()));

        // Show the ImageViewer
        mainApp.showImageViewer();

        // Set the title of the window to the name of the image + the resolution
        mainApp.getPrimaryStage().setTitle(imageFile.getName() + "  " +
                (int)mainApp.getCurrentImage().getImage().getWidth() + " x " +
                (int)mainApp.getCurrentImage().getImage().getHeight());
    }


    @FXML
    private void closeImageView(){
        mainApp.hideImageViewer();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
}