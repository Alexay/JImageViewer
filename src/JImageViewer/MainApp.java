package JImageViewer;

import JImageViewer.model.ImageData;
import JImageViewer.model.PixelInfo;
import JImageViewer.util.ImageViewPane;
import JImageViewer.util.PanAndZoomPane;
import JImageViewer.view.MetadataViewController;
import JImageViewer.view.RootController;
import JImageViewer.view.StatusBarController;
import JImageViewer.view.ThumbnailViewController;
import com.drew.imaging.ImageProcessingException;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;

import java.io.IOException;

public class MainApp extends Application {

    // Initialize the instance of the system clipboard.
    private final Clipboard clipboard = Clipboard.getSystemClipboard();
    private final ClipboardContent clipboardContent = new ClipboardContent();

    private Stage primaryStage;
    private BorderPane rootLayout;
    private TreeView<String> treeView = new TreeView<>();
    // The current image to be set in the ImageView
    private final ImageData imageData = new ImageData();

    // The information about the pixel that is being hovered over by the mouse
    // in the ImageView
    private final PixelInfo pixelInfo = new PixelInfo();


    public Clipboard getClipboard() {
        return clipboard;
    }

    public ClipboardContent getClipboardContent() {
        return clipboardContent;
    }

    public PixelInfo getPixelInfo() {
        return this.pixelInfo;
    }

    public ImageData getImageData() {
        return this.imageData;
    }

    public TreeView<String> getTreeView() {
        return treeView;
    }

    public MainApp() {}

    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JImageViewer");

        initRootLayout();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Root.fxml"));
            rootLayout = loader.load();


            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Giving the controller access to the main app.
            RootController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Root layout loading error.");
        }
    }

    /**
     * Shows the ImageViewer inside the root layout.
     */
    public void showImageViewer() throws Exception{

        // Initialize all ImageView related stuff.
        ImageViewPane imageViewPane = new ImageViewPane(imageData.getImageView());
        imageViewPane.setMaxHeight(imageData.getImage().getHeight());
        imageViewPane.setMaxWidth(imageData.getImage().getWidth());
        // Listener for the current image that sets the current image as the image to be in the ImageView
        imageData.imageProperty().addListener((observable, oldValue, newValue) -> {
            imageViewPane.setMaxHeight(newValue.getHeight());
            imageViewPane.setMaxWidth(newValue.getWidth());
            imageViewPane.getImageView().setImage(newValue);
        });
        imageViewPane.initializeListeners(this);
        imageViewPane.setMainApp(this);

        rootLayout.setCenter(imageViewPane);
    }

    public void hideImageViewer() {
        rootLayout.setCenter(null);
    }

    public void showZoomPane() throws Exception {
        imageData.refresh();
        ScrollPane scrollPane = new ScrollPane();
        final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0d);
        final DoubleProperty deltaY = new SimpleDoubleProperty(0.0d);

        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Initialize all ImageView related stuff.
        ImageViewPane imageViewPane = new ImageViewPane(imageData.getImageView());
        imageViewPane.setMaxHeight(imageData.getImage().getHeight());
        imageViewPane.setMaxWidth(imageData.getImage().getWidth());
        // Listener for the current image that sets the current image as the image to be in the ImageView
        imageData.imageProperty().addListener((observable, oldValue, newValue) -> {
            imageViewPane.setMaxHeight(newValue.getHeight());
            imageViewPane.setMaxWidth(newValue.getWidth());
            imageViewPane.getImageView().setImage(newValue);
        });
        imageViewPane.initializeListeners(this);
        imageViewPane.setMainApp(this);

        // Create canvas.
        PanAndZoomPane panAndZoomPane = new PanAndZoomPane();
        zoomProperty.bind(panAndZoomPane.myScaleProperty());
        deltaY.bind(panAndZoomPane.deltaY);
        panAndZoomPane.getChildren().add(imageViewPane);

        // Initialize zoom-related events.
        PanAndZoomPane.SceneGestures sceneGestures = new PanAndZoomPane.SceneGestures(panAndZoomPane, this);
        scrollPane.setContent(panAndZoomPane);
        panAndZoomPane.toBack();
        scrollPane.addEventFilter(MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        scrollPane.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scrollPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scrollPane.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        rootLayout.setCenter(scrollPane);

    }

    public void showThumbnailView(){
        try {
            // Load Thumbnail viewer.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ThumbnailView.fxml"));
            VBox gridView = loader.load();

            // Set the ImageViewer into the center of root layout.
            rootLayout.setCenter(gridView);

            // Giving the controller access to the main app.
            ThumbnailViewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ThumbnailView layout loading error.");
        }
    }

    public void hideThumbnailView(){rootLayout.setCenter(null);}

    public void showMetadataView(){
        try {
            // Load MetadataModel layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MetadataView.fxml"));
            AnchorPane metadataView = loader.load();


            // Show the scene containing the root layout.
            rootLayout.setRight(metadataView);

            // Giving the controller access to the main app.
            MetadataViewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Metadata layout loading error.");
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        }
    }

    public void hideMetadataView(){
        rootLayout.setRight(null);
    }

    /**
     * Shows the StatusBar inside the root layout.
     */
    public void showStatusBar() {
        try {
            // Load StatusBar
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/StatusBarView.fxml"));
            StatusBar statusBar = loader.load();

            // Set the StatusBar into the bottom of root layout.
            rootLayout.setBottom(statusBar);

            // Giving the controller access to the main app.
            StatusBarController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("StatusBar layout loading error.");
        }
    }

    public void hideStatusBar() { rootLayout.setBottom(null); }


    public void showFileExplorerTree() {
        rootLayout.setLeft(treeView);
    }

    public void hideFileExplorerTree(){
        rootLayout.setLeft(null);
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) { launch(args); }
}