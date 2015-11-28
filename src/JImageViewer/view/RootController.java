package JImageViewer.view;

import JImageViewer.MainApp;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class RootController {
    @FXML
    private MenuItem open;

    @FXML
    private MenuItem openDir;

    @FXML
    private MenuItem saveAs;

    @FXML
    private MenuItem rename;

    @FXML
    private MenuItem print;

    @FXML
    private RadioMenuItem scanFolderRecursively;

    @FXML
    private MenuItem sortByFilename;

    @FXML
    private MenuItem sortByDateCreated;

    @FXML
    private MenuItem sortByDateModified;

    @FXML
    private MenuItem sortByAscending;

    @FXML
    private MenuItem sortByDescending;

    @FXML
    private MenuItem reloadFile;

    @FXML
    private MenuItem previousFile;

    @FXML
    private MenuItem nextFile;

    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem copy;

    @FXML
    private MenuItem paste;

    @FXML
    private MenuItem delete;

    @FXML
    private MenuItem rotateCounterClockwise;

    @FXML
    private MenuItem rotateClockwise;

    @FXML
    private MenuItem fullscreen;

    @FXML
    private MenuItem zoomIn;

    @FXML
    private MenuItem zoomOut;

    @FXML
    private RadioMenuItem toolbar;

    @FXML
    private RadioMenuItem statusbar;

    @FXML
    private RadioMenuItem fileExplorer;

    @FXML
    private RadioMenuItem metadataInfo;

    @FXML
    private MenuItem about;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button openButton;

    @FXML
    private Button openDirButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button rotateCounterClockwiseButton;

    @FXML
    private Button rotateClockwiseButton;

    @FXML
    private Button copyButton;

    @FXML
    private Button pasteButton;

    @FXML
    private Button fullscreenButton;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public RootController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    @FXML
    private void openImageFile () throws Exception {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image types (*.jpg;  *.gif; *.bmp; *.png)", "*.jpg", "*.png", "*.bmp", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File imageFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        // Set the app's current imageData.
        mainApp.getImageData().setImageFile(imageFile);
        mainApp.getImageData().setImage(new Image(imageFile.toURI().toString()));
        mainApp.getImageData().setImageView(new ImageView(new Image(imageFile.toURI().toString())));

        // Set the title of the window to the name of the image + the resolution
        mainApp.getPrimaryStage().setTitle(imageFile.getName() + " - " +
                (int)mainApp.getImageData().getImage().getWidth() + " x " +
                (int)mainApp.getImageData().getImage().getHeight());

        // Show the ImageViewer
        mainApp.showImageViewer();
    }

    @FXML
    private void openImageDir () {}

    @FXML
    private void saveImageAs(){}

    @FXML
    private void fileRename(){}

    @FXML
    private void setSortByFilename(){
        mainApp.getImageData().setSortByFilename(true);
        mainApp.getImageData().setSortByDateCreated(false);
        mainApp.getImageData().setSortByDateModified(false);
        mainApp.getImageData().refresh();
    }

    @FXML
    private void setSortByDateCreated(){
        mainApp.getImageData().setSortByFilename(false);
        mainApp.getImageData().setSortByDateCreated(true);
        mainApp.getImageData().setSortByDateModified(false);
        mainApp.getImageData().refresh();
    }

    @FXML
    private void setSortByDateModified(){
        mainApp.getImageData().setSortByFilename(false);
        mainApp.getImageData().setSortByDateCreated(false);
        mainApp.getImageData().setSortByDateModified(true);
        mainApp.getImageData().refresh();
    }

    @FXML
    private void setSortByAscending(){
        mainApp.getImageData().setSortByAscending(true);
        mainApp.getImageData().setSortByDescending(false);
        mainApp.getImageData().refresh();
    }

    @FXML
    private void setSortByDescending(){
        mainApp.getImageData().setSortByAscending(false);
        mainApp.getImageData().setSortByDescending(true);
        mainApp.getImageData().refresh();
    }

    @FXML
    private void setRecursiveScanning(){
        mainApp.getImageData().setRecursiveScanning(scanFolderRecursively.isSelected());
    }

    @FXML
    private void reloadImageFile(){}

    @FXML
    private void previousImage(){}

    @FXML
    private void nextImage(){}

    @FXML
    private void exitProgram(){
        System.exit(0);
    }

    @FXML
    private void imageRotateCounterClockwise(){}

    @FXML
    private void imageRotateClockwise(){}

    @FXML
    private void copyImage(){}

    @FXML
    private void pasteImage(){}

    @FXML
    private void engageFullscreen(){}

    @FXML
    private void imageZoomIn(){}

    @FXML
    private void imageZoomOut(){}

    @FXML
    private void toggleToolbar(){}

    @FXML
    private void toggleStatusbar(){
        if (statusbar.isSelected())
            mainApp.showStatusBar();
        else
            mainApp.hideStatusBar();
    }

    @FXML
    private void toggleFileExplorer(){
        if (fileExplorer.isSelected())
            mainApp.showFileExplorerTree();
        else
            mainApp.hideFileExplorerTree();
    }

    @FXML
    private void toggleMetadataInfo(){}

    @FXML
    private void displayAboutWindow(){}

    /**
     * This is an actually working method that actually prints the current image. I was amazed at how easy it was
     * to implement. Normally, working with printers is a nightmare.
     */
    @FXML
    private void printImage() {
        if (mainApp.getImageData().getImage() == null)
            System.out.println("No image loaded, aborting printing...");

        else {
            // Background printing thread.
            Service<Void> backgroundPrintingThread = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            PrinterJob printerJob = PrinterJob.createPrinterJob();
                            if (printerJob != null) {
                                boolean success = printerJob.printPage(new ImageView(mainApp.getImageData().getImage()));
                                if (success) {
                                    printerJob.endJob();
                                }
                            }
                            return null;
                        }
                    };
                }
            };
            backgroundPrintingThread.start();
        }
    }

    @FXML
    private void closeImageView(){
        mainApp.hideImageViewer();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
}