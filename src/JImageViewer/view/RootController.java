package JImageViewer.view;

import JImageViewer.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class RootController {
    @FXML
    private MenuItem open;

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
        mainApp.menuOpenImageFile();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
}