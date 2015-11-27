package JImageViewer.view;

import JImageViewer.MainApp;
import JImageViewer.util.FileSizeReader;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import org.controlsfx.control.StatusBar;

import java.text.SimpleDateFormat;
import java.util.Date;

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

        // Initializing labels and date formatter for the date label
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Label fileSize = new Label();
        Label creationDate = new Label();

        // Adding the labels to the right of the StatusBar
        statusBar.getRightItems().addAll(fileSize, new Separator(Orientation.VERTICAL), creationDate);

        // Initializing pixel info listener and updater
        this.mainApp.getPixelInfo().infoStringProperty().addListener((observable, oldValue, newValue) -> {
            statusBar.setText(newValue);
        });

        // Initializing date and file size info listener and updater
        this.mainApp.getImageData().imageFileProperty().addListener(((observable, oldValue, newValue) -> {
            fileSize.setText(FileSizeReader.readableFileSize(newValue.length()));
            creationDate.setText(" " + simpleDateFormat.format(new Date(newValue.lastModified())));
        }));

    }
}
