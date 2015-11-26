package JImageViewer.model;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import java.io.File;

public class MyImage {
    private final ObjectProperty<File> imageFile;
    private final ObjectProperty<Image> image;

    public MyImage(){
        this.imageFile = new SimpleObjectProperty<>();
        this.image = new SimpleObjectProperty<>();

        //this(null);
    }

    public MyImage(File imageFile) {
        this.imageFile = new SimpleObjectProperty<>(imageFile);
        Image imgToSet = new Image("file:"+imageFile.getAbsolutePath());
        this.image = new SimpleObjectProperty<>(imgToSet);
    }

    public File getImageFile() {
        return imageFile.get();
    }

    public ObjectProperty<File> imageFileProperty() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile.set(imageFile);
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
}
