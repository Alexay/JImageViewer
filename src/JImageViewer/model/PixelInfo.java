package JImageViewer.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Holds information for the StatusBar to display about the pixel over which the mouse is hovering in the ImageView
 */

public class PixelInfo {
    private final StringProperty infoString;

    public PixelInfo(){
        this.infoString = new SimpleStringProperty();
    }

    public String getInfoString() {
        return infoString.get();
    }

    public StringProperty infoStringProperty() {
        return infoString;
    }

    public void setInfoString(String infoString) {
        this.infoString.set(infoString);
    }
}
