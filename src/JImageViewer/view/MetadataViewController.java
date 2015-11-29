package JImageViewer.view;


import JImageViewer.MainApp;
import JImageViewer.model.MetadataModel;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

/**
 * StatusBar holds information about the pixel that is being hovered over, file information, button information.
 */

public class MetadataViewController {

    ObservableList<MetadataModel> currentImageMetadata = FXCollections.observableArrayList();

    @FXML
    private TableView<MetadataModel> tableView;

    @FXML
    private TableColumn<MetadataModel, String> key;

    @FXML
    private TableColumn<MetadataModel, String> value;

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
    private void initialize() {
        key.setCellValueFactory(param -> param.getValue().tagProperty());
        value.setCellValueFactory(param -> param.getValue().descriptionProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) throws ImageProcessingException, IOException {
        this.mainApp = mainApp;
        currentImageMetadata.clear();
        // Parse metadata of the image file.
        if (mainApp.getImageData().getImageFile()==null)
            System.out.println("No image, ignoring...");
        else {
            Metadata imageMetadata = ImageMetadataReader.readMetadata(mainApp.getImageData().getImageFile());
            for (Directory directory : imageMetadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    currentImageMetadata.add(new MetadataModel(tag.getTagName(), tag.getDescription()));
                }
            }
        }
        tableView.setItems(currentImageMetadata);

        mainApp.getImageData().imageFileProperty().addListener((observable, oldValue, newValue) -> { try {
            Metadata newImageMetadata = ImageMetadataReader.readMetadata(mainApp.getImageData().getImageFile());
            currentImageMetadata.clear();
            for (Directory directory : newImageMetadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    currentImageMetadata.add(new MetadataModel(tag.getTagName(), tag.getDescription()));
                }
            }
            tableView.setItems(currentImageMetadata);
        } catch (Exception i) {
            System.err.println("New image metadata parsing error: " + i);
        }
        });
    }
}
