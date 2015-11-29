package JImageViewer.view;

import JImageViewer.MainApp;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.controlsfx.control.GridView;
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
        gridView.setCellFactory ( param -> {
            ImageGridCell imageGridCell = new ImageGridCell();
            imageGridCell.setOnMouseClicked(event -> {
                try {
                    mainApp.getImageData().setImage(imageGridCell.getItem());
                    mainApp.getImageData().reSort();
                    mainApp.showImageViewer();
                } catch (Exception i) {
                    System.err.println(i + " ImageGridCell event failure");
                }
            });
            imageGridCell.setOnMouseEntered(event ->{
                Glow glow = new Glow();
                glow.setLevel(1);
                imageGridCell.setEffect(glow);
                final Timeline timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.setAutoReverse(true);
                final KeyValue kv = new KeyValue(glow.levelProperty(), 0.3);
                final KeyFrame kf = new KeyFrame(Duration.millis(700), kv);
                timeline.getKeyFrames().add(kf);
                timeline.play();
            });
            imageGridCell.setOnMouseExited(event ->{
                imageGridCell.setEffect(null);
            });
            return imageGridCell;
        });
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
