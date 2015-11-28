package JImageViewer.model;


import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.stream.Stream;

public class ImageData {
    private final ListProperty<Image> imageList;
    private final ObjectProperty<File> imageFile;
    private final ObjectProperty<Image> image;
    private final ObjectProperty<ImageView> imageView;
    private final BooleanProperty recursiveScanning;
    private final BooleanProperty sortByDateCreated;
    private final BooleanProperty sortByDateModified;
    private final BooleanProperty sortByFilename;
    private final BooleanProperty sortByAscending;
    private final BooleanProperty sortByDescending;

    public ImageData(){
        imageList = new SimpleListProperty<>();
        imageFile = new SimpleObjectProperty<>();
        image = new SimpleObjectProperty<>();
        imageView = new SimpleObjectProperty<>();
        recursiveScanning = new SimpleBooleanProperty(false);
        sortByFilename = new SimpleBooleanProperty(true);
        sortByDateCreated = new SimpleBooleanProperty(false);
        sortByDateModified = new SimpleBooleanProperty(false);
        sortByAscending = new SimpleBooleanProperty(true);
        sortByDescending = new SimpleBooleanProperty(false);
    }


    /**
     * Must be called any time the "Open" or Open Directory" application functions are used or if sorting setting
     * are changed. This method parses the sorting setting and generates a list of images for the image viewer's
     * displaying.
     */
    public void refresh() {
        // Putting all the user settings into an array to pass on to the ImageFileReader
        final boolean[] userSettings = {recursiveScanning.get(), sortByFilename.get(), sortByDateCreated.get(),sortByDateModified.get(), sortByAscending.get(), sortByDescending.get()};

    }

//    public ImageData(File imageFile) {
//        this.imageFile = new SimpleObjectProperty<>(imageFile);
//        Image imgToSet = new Image("file:"+imageFile.getAbsolutePath());
//        image = new SimpleObjectProperty<>(imgToSet);
//    }

    public File getImageFile() {
        return imageFile.get();
    }

    public ObjectProperty<File> imageFileProperty() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile.set(imageFile);
    }

    public boolean getRecursiveScanning() {
        return recursiveScanning.get();
    }

    public BooleanProperty recursiveScanningProperty() {
        return recursiveScanning;
    }

    public void setRecursiveScanning(boolean recursiveScanning) {
        this.recursiveScanning.set(recursiveScanning);
    }

    public boolean getSortByDateCreated() {
        return sortByDateCreated.get();
    }

    public BooleanProperty sortByDateCreatedProperty() {
        return sortByDateCreated;
    }

    public void setSortByDateCreated(boolean sortByDateCreated) {
        this.sortByDateCreated.set(sortByDateCreated);
    }

    public boolean getSortByDateModified() {
        return sortByDateModified.get();
    }

    public BooleanProperty sortByDateModifiedProperty() {
        return sortByDateModified;
    }

    public void setSortByDateModified(boolean sortByDateModified) {
        this.sortByDateModified.set(sortByDateModified);
    }

    public boolean getSortByFilename() {
        return sortByFilename.get();
    }

    public BooleanProperty sortByFilenameProperty() {
        return sortByFilename;
    }

    public void setSortByFilename(boolean sortByFilename) {
        this.sortByFilename.set(sortByFilename);
    }

    public boolean getSortByAscending() {
        return sortByAscending.get();
    }

    public BooleanProperty sortByAscendingProperty() {
        return sortByAscending;
    }

    public void setSortByAscending(boolean sortByAscending) {
        this.sortByAscending.set(sortByAscending);
    }

    public boolean getSortByDescending() {
        return sortByDescending.get();
    }

    public BooleanProperty sortByDescendingProperty() {
        return sortByDescending;
    }

    public void setSortByDescending(boolean sortByDescending) {
        this.sortByDescending.set(sortByDescending);
    }

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    public ObservableList<Image> getImageList() {
        return imageList.get();
    }

    public ListProperty<Image> imageListProperty() {
        return imageList;
    }

    public void setImageList(ObservableList<Image> imageList) {
        this.imageList.set(imageList);
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public ObjectProperty<ImageView> imageViewProperty() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView.set(imageView);
    }
}
