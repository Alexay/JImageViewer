package JImageViewer.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MetadataModel {
    private final StringProperty tag;
    private final StringProperty description;

    public MetadataModel() {
        tag = new SimpleStringProperty();
        description = new SimpleStringProperty();
    }

    public MetadataModel(String tag, String description) {
        this.tag = new SimpleStringProperty(tag);
        this.description = new SimpleStringProperty(description);
    }

    public String getTag() {
        return tag.get();
    }

    public StringProperty tagProperty() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag.set(tag);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
