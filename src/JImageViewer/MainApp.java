package JImageViewer;

import JImageViewer.model.ImageData;
import JImageViewer.model.PixelInfo;
import JImageViewer.util.SimpleFileTreeItem;
import JImageViewer.view.ImageViewerController;
import JImageViewer.view.RootController;
import JImageViewer.view.StatusBarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    // The current image to be set in the ImageView
    private final ImageData imageData = new ImageData();

    // The information about the pixel that is being hovered over by the mouse
    // in the ImageView
    private final PixelInfo pixelInfo = new PixelInfo();

    public PixelInfo getPixelInfo() {
        return this.pixelInfo;
    }

    public ImageData getImageData() {
        return this.imageData;
    }

    public MainApp() {}

    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JImageViewer");

        initRootLayout();
        showStatusBar();
        showFileExplorerTree();
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
    public void showImageViewer() {
        try {
            // Load ImageViewer.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ImageViewer.fxml"));
            AnchorPane imageViewer = loader.load();

            // Set the ImageViewer into the center of root layout.
            rootLayout.setCenter(imageViewer);

            // Giving the controller access to the main app.
            ImageViewerController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ImageViewer layout loading error.");
        }
    }

    public void hideImageViewer() {
        rootLayout.setCenter(null);
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

        TreeItem<Path> root = new SimpleFileTreeItem(
                Paths.get(System.getProperty("user.dir")));
        root.setExpanded(true);
        TreeView<Path> treeView1 = new TreeView<Path>(root);
        treeView1.setCellFactory(treeView -> new TreeCell<Path>() {
            @Override
            public void updateItem(Path path, boolean empty) {
                super.updateItem(path, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(path.getFileName().toString());
                }
            }
        });

        SplitPane fileExplorerPane = new SplitPane(treeView1);

        rootLayout.setLeft(fileExplorerPane);
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