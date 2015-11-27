package JImageViewer.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * Tree view for the file system
 */
public class SimpleFileTreeItem extends TreeItem<Path> {

    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;
    private boolean isLeaf;

    public boolean isDirectory() {
        return Files.isDirectory(getValue());
    }


    public SimpleFileTreeItem(Path f) {
        super(f);
    }

    @Override
    public ObservableList<TreeItem<Path>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;

                /*
                 * First getChildren() call, so we actually go off and determine
                 * the children of the File contained in this TreeItem.
                 */
            super.getChildren().setAll(buildChildren());
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeaf) {
            isFirstTimeLeaf = false;
            isLeaf = Files.exists(getValue()) && ! Files.isDirectory(getValue());
        }
        return isLeaf;
    }

    /**
     * Returning a collection of type ObservableList containing TreeItems,
     * which represent all children of this TreeITem.
     *
     *
     * @return an ObservableList<TreeItem<File>> containing TreeItems, which
     *         represent all children available in this TreeItem. If the
     *         handed TreeItem is a leaf, an empty list is returned.
     */
    private ObservableList<TreeItem<Path>> buildChildren() {
        if (Files.isDirectory(getValue())) {
            try {

                return Files.list(getValue())
                        .map(SimpleFileTreeItem::new)
                        .collect(Collectors.toCollection(() -> FXCollections.observableArrayList()));

            } catch (IOException e) {
                e.printStackTrace();
                return FXCollections.emptyObservableList();
            }
        }

        return FXCollections.emptyObservableList();
    }
}