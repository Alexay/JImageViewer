package JImageViewer.view;

import JImageViewer.MainApp;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.control.StatusBar;
import org.controlsfx.control.cell.ImageGridCell;

/**
 * StatusBar holds information about the pixel that is being hovered over, file information, button information.
 */

public class ThumbnailViewController {

    @FXML
    private GridView<Image> gridView;

    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ThumbnailViewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        gridView.setCellFactory(param -> new ImageGridCell());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        gridView.setItems(mainApp.getImageData().getImageObservableList());
        gridView.setCellHeight(150);
        gridView.setCellWidth(150);
    }
}
