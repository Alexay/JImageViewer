package JImageViewer.util;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.image.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Used by ImageData to read a certain directory and sort it given the current user settings
 */
public class ImageFileReader {
    public static Image[] read(Path selectedPath, boolean options[]) {

        List<Path> pathsForSorting = new ArrayList<>();

        // If the user-given path is not a directory - parse it's parent.
        final Path parsedPath = Files.isDirectory(selectedPath) ? selectedPath : selectedPath.getParent();

        // Filter only for images.
        final PathMatcher filter = parsedPath.getFileSystem().getPathMatcher("glob:**/*.{jpg,bmp,gif,png}");

        // Check settings for recursivity and read the paths from the stream.
        if (options[0]) {
            try (final Stream<Path> stream = Files.walk(parsedPath)) {
                stream.filter(filter::matches)
                        .forEach(pathsForSorting::add);
            } catch (IOException e) {
                System.err.println("Recursive reading failed: " + e);
            }
        } else {
            try (final Stream<Path> stream = Files.list(parsedPath)) {
                stream.filter(filter::matches)
                        .forEach(pathsForSorting::add);
            } catch (IOException e) {
                System.err.println("Non-recursive reading failed: " + e);
            }
        }

        // Sorting based on user settings.
        // By Filename.
        if (options[1]) {
            if (options[4])
                pathsForSorting.sort(Comparator.<Path>naturalOrder());
            else
                pathsForSorting.sort(Comparator.<Path>reverseOrder());
        }

        // By Date Created.
        else if (options[2]) {
            pathsForSorting.sort((p1, p2) -> {
                try {
                    DosFileAttributes attr1 = Files.readAttributes(p1, DosFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                    DosFileAttributes attr2 = Files.readAttributes(p2, DosFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                    if (options[4])
                        return  (attr1.creationTime().compareTo(attr2.creationTime()));
                    else
                        return (attr2.creationTime().compareTo(attr1.creationTime()));
                } catch (IOException x) {
                    System.err.println("Sorting by creation date failed: " + x);
                }
                return 0;
            });
        }

        // By Date Created.
        else if (options[3]) {
            pathsForSorting.sort((p1, p2) -> {
                try {
                    DosFileAttributes attr1 = Files.readAttributes(p1, DosFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                    DosFileAttributes attr2 = Files.readAttributes(p2, DosFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                    if (options[4])
                        return  (attr1.lastModifiedTime().compareTo(attr2.lastModifiedTime()));
                    else
                        return (attr2.lastModifiedTime().compareTo(attr1.lastModifiedTime()));
                } catch (IOException x) {
                    System.err.println("Sorting by last modified date failed: " + x);
                }
                return 0;
            });
        }

        // Initialize output object.
        Image[] parsedImages = new Image[pathsForSorting.size()];

        for (int i = 0; i< parsedImages.length; i++){
            parsedImages[i] = new Image(pathsForSorting.get(i).toUri().toString());
        }

        return parsedImages;
    }
}