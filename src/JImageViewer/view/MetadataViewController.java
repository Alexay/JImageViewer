package JImageViewer.view;

import JImageViewer.MainApp;
import com.sun.javafx.collections.MappingChange;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import org.controlsfx.control.GridView;

import javax.swing.table.TableColumn;
import java.util.Map;

/**
 * StatusBar holds information about the pixel that is being hovered over, file information, button information.
 */

public class MetadataViewController {

    @FXML
    private TableView<String> tableView;

    @FXML
    private TableColumn key;

    @FXML
    private TableColumn value;

    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MetadataViewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
