package JImageViewer.view;

import JImageViewer.MainApp;
import JImageViewer.util.FilePathTreeItem;
import JImageViewer.util.RotateImage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class RootController {

    private TreeView<String> treeView;

    @FXML
    private VBox vbox;

    @FXML
    private ToolBar toolBar;

    @FXML
    private MenuItem open;

    @FXML
    private MenuItem openDir;

    @FXML
    private MenuItem saveAs;

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
    private MenuItem thumbnailView;

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
        //mainApp.getPrimaryStage().setTitle(imageFile.getName() + " - " +
        //        (int)mainApp.getImageData().getImage().getWidth() + " x " +
        //        (int)mainApp.getImageData().getImage().getHeight());

        mainApp.getImageData().refresh();

        // Show the ImageViewer.
        mainApp.showImageViewer();
        mainApp.showStatusBar();
    }

    @FXML
    private void openImageDir () throws Exception {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        // Show choose directory dialog.
        File imageDir = directoryChooser.showDialog(mainApp.getPrimaryStage());

        mainApp.getImageData().setImageFile(imageDir);
        mainApp.getImageData().refresh();
        mainApp.showImageViewer();
        mainApp.showStatusBar();
    }

    @FXML
    private void saveImageAs(){
        FileChooser fileChooser = new FileChooser();

        // Set extension filters.
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG image (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilterGIF = new FileChooser.ExtensionFilter("GIF image (*.gif)", "*.gif");
        fileChooser.getExtensionFilters().addAll(extFilterPNG, extFilterGIF);

        // Show save file dialog.
        File savedFile = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        // Convert the image to a Swing image and use ImageIO to write it to the savedFile.
        try {
            BufferedImage bufferedImage = new BufferedImage((int)mainApp.getImageData().getImage().getWidth(), (int)mainApp.getImageData().getImage().getHeight(), BufferedImage.TYPE_INT_RGB);
            ImageIO.write(SwingFXUtils.fromFXImage(mainApp.getImageData().getImage(), bufferedImage),
                    savedFile.toString().substring(savedFile.toString().length()-3,savedFile.toString().length()), savedFile);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void setSortByFilename(){
        mainApp.getImageData().setSortByFilename(true);
        mainApp.getImageData().setSortByDateCreated(false);
        mainApp.getImageData().setSortByDateModified(false);
        mainApp.getImageData().reSort();
    }

    @FXML
    private void setSortByDateCreated(){
        mainApp.getImageData().setSortByFilename(false);
        mainApp.getImageData().setSortByDateCreated(true);
        mainApp.getImageData().setSortByDateModified(false);
        mainApp.getImageData().reSort();
    }

    @FXML
    private void setSortByDateModified(){
        mainApp.getImageData().setSortByFilename(false);
        mainApp.getImageData().setSortByDateCreated(false);
        mainApp.getImageData().setSortByDateModified(true);
        mainApp.getImageData().reSort();
    }

    @FXML
    private void setSortByAscending(){
        mainApp.getImageData().setSortByAscending(true);
        mainApp.getImageData().setSortByDescending(false);
        mainApp.getImageData().reSort();
    }

    @FXML
    private void setSortByDescending(){
        mainApp.getImageData().setSortByAscending(false);
        mainApp.getImageData().setSortByDescending(true);
        mainApp.getImageData().reSort();
    }

    @FXML
    private void setRecursiveScanning(){
        mainApp.getImageData().setRecursiveScanning(scanFolderRecursively.isSelected());
        mainApp.getImageData().refresh();
    }

    @FXML
    private void reloadImageFile(){
        mainApp.getImageData().refresh();
    }

    @FXML
    private void previousImage(){
        int prevImageIndex = mainApp.getImageData().getCurrentListIndex()-1;
        if (mainApp.getImageData().getCurrentListIndex()==0)
            prevImageIndex = mainApp.getImageData().getImageArray().length-1;
        mainApp.getImageData().setImage(mainApp.getImageData().getImageArray()[prevImageIndex]);
        mainApp.getImageData().setImageFile(mainApp.getImageData().getPathList().get(prevImageIndex).toFile());
        mainApp.getImageData().setCurrentListIndex(prevImageIndex);
    }

    @FXML
    private void nextImage(){
        int nextImageIndex = mainApp.getImageData().getCurrentListIndex()+1;
        if (mainApp.getImageData().getCurrentListIndex()==mainApp.getImageData().getImageArray().length-1)
            nextImageIndex = 0;
        mainApp.getImageData().setImage(mainApp.getImageData().getImageArray()[nextImageIndex]);
        mainApp.getImageData().setImageFile(mainApp.getImageData().getPathList().get(nextImageIndex).toFile());
        mainApp.getImageData().setCurrentListIndex(nextImageIndex);
    }

    @FXML
    private void exitProgram(){
        System.exit(0);
    }

    @FXML
    private void imageRotateCounterClockwise() throws Exception{
        BufferedImage buffImg = SwingFXUtils.fromFXImage(mainApp.getImageData().getImage(), null);
        buffImg = RotateImage.getRotatedImage(buffImg, -1); // The "-1" will make Math.PI/2*(-1) go counter clockwise.
        mainApp.getImageData().setImage(SwingFXUtils.toFXImage(buffImg, null));
    }

    @FXML
    private void imageRotateClockwise(){
        BufferedImage buffImg = SwingFXUtils.fromFXImage(mainApp.getImageData().getImage(), null);
        buffImg = RotateImage.getRotatedImage(buffImg, 1); // The "1" will make Math.PI/2 go clockwise.
        mainApp.getImageData().setImage(SwingFXUtils.toFXImage(buffImg, null));
    }

    @FXML
    private void copyImage(){
        mainApp.getClipboardContent().putImage(mainApp.getImageData().getImage());
    }

    @FXML
    private void pasteImage(){
        mainApp.getImageData().getImageView().setImage(mainApp.getClipboardContent().getImage());
    }

    @FXML
    private void engageFullscreen(){}

    @FXML
    private void engageThumbnailView(){
        mainApp.hideImageViewer();
        mainApp.showThumbnailView();
    }

    @FXML
    private void imageZoomIn(){}

    @FXML
    private void imageZoomOut(){}

    @FXML
    private void toggleToolbar() {
        if (toolbar.isSelected())
            vbox.getChildren().add(toolBar);
        else
            vbox.getChildren().remove(toolBar);
    }

    @FXML
    private void toggleStatusbar(){
        if (statusbar.isSelected())
            mainApp.showStatusBar();
        else
            mainApp.hideStatusBar();
    }

    @FXML
    private void toggleFileExplorer(){
        if (fileExplorer.isSelected()) {
            treeView = mainApp.getTreeView();
            //populate file browser
            String hostName=null;
            try{hostName= InetAddress.getLocalHost().getHostName();}catch(UnknownHostException x){ System.out.println(x);} //TODO error dialog
            assert hostName != null : "Unable to get local host name.";
            TreeItem<String> rootNode=new TreeItem<>(hostName,new ImageView(new Image("file:./computer.png")));
            Iterable<Path> rootDirectories= FileSystems.getDefault().getRootDirectories();
            for(Path name:rootDirectories){
                FilePathTreeItem treeNode=new FilePathTreeItem(new File(name.toString()));
                rootNode.getChildren().add(treeNode);
            }
            rootNode.setExpanded(true);
            treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            treeView.setRoot(rootNode);
            mainApp.showFileExplorerTree();
        }
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