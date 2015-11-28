package JImageViewer;

import JImageViewer.model.ImageData;
import JImageViewer.model.PixelInfo;
import JImageViewer.util.FilePathTreeItem;
import JImageViewer.util.ImageViewPane;
import JImageViewer.util.SimpleFileTreeItem;
import JImageViewer.view.RootController;
import JImageViewer.view.StatusBarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainApp extends Application {

    // Initialize the instance of the system clipboard.
    private final Clipboard clipboard = Clipboard.getSystemClipboard();
    private final ClipboardContent clipboardContent = new ClipboardContent();

    private Stage primaryStage;
    private BorderPane rootLayout;
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

        ImageViewPane imageViewPane = new ImageViewPane(imageData.getImageView());
        rootLayout.setCenter(imageViewPane);
        // Listener for the current image that sets the current image as the image to be in the ImageView
        imageData.imageProperty().addListener(((observable, oldValue, newValue) ->
                imageViewPane.getImageView().setImage(newValue)));
        imageViewPane.initializeListeners(this);
//        try {
//            // Load ImageViewer.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApp.class.getResource("view/ImageViewer.fxml"));
//            AnchorPane imageViewer = loader.load();
//
//            // Set the ImageViewer into the center of root layout.
//            rootLayout.setCenter(imageViewer);
//
//            // Giving the controller access to the main app.
//            ImageViewerController controller = loader.getController();
//            controller.setMainApp(this);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("ImageViewer layout loading error.");
//        }
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
//create tree pane
        VBox treeBox=new VBox();
        treeBox.setPadding(new Insets(10,10,10,10));
        treeBox.setSpacing(10);
        //setup the file browser root
        String hostName="computer";
        try{hostName= InetAddress.getLocalHost().getHostName();}catch(UnknownHostException x){}
        TreeItem<String> rootNode=new TreeItem<>(hostName,new ImageView(new Image("file:/../util/computer.png")));
        Iterable<Path> rootDirectories= FileSystems.getDefault().getRootDirectories();
        for(Path name:rootDirectories){
            FilePathTreeItem treeNode=new FilePathTreeItem(name.toFile());
            rootNode.getChildren().add(treeNode);
        }
        rootNode.setExpanded(true);
        //create the tree view
        TreeView<String> treeView = new TreeView<>(rootNode);
        //add everything to the tree pane
        treeBox.getChildren().addAll(new Label("File browser"),treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);

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