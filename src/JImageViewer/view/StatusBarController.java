package JImageViewer.view;

import JImageViewer.MainApp;
import javafx.fxml.FXML;
import org.controlsfx.control.StatusBar;

/**
 * StatusBar holds information about the pixel that is being hovered over, file information, button information.
 */

public class StatusBarController {

    @FXML
    private StatusBar statusBar;

    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public StatusBarController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.mainApp.getPixelInfo().infoStringProperty().addListener((observable, oldValue, newValue) -> {
            statusBar.setText(newValue);
        });

    }
}
