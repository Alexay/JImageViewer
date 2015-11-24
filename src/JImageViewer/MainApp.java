package JImageViewer;

import JImageViewer.model.MyImage;
import JImageViewer.view.ImageViewerController;
import JImageViewer.view.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {
    final File img = new File("C:\\users\\user\\pictures\\1.png");
    private Stage primaryStage;
    private BorderPane rootLayout;
    private final MyImage currentImage = new MyImage(img);


    public MyImage getCurrentImage() {
        return this.currentImage;
    }

    public MainApp() {}

    public void menuOpenImageFile() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image types (*.jpg;  *.gif; *.bmp; *.png)", "*.jpg", "*.png", "*.bmp", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File imageFile = fileChooser.showOpenDialog(primaryStage);
        currentImage.setImageFile(imageFile);
        currentImage.setImage(new Image(imageFile.toURI().toString()));
    }

    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JImageViewer");

        initRootLayout();
        showImageViewer();
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

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) { launch(args); }
}