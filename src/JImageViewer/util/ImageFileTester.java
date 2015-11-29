package JImageViewer.util;

import java.io.File;
import java.nio.file.PathMatcher;

/**
 * Checks whether this file is an image or not to display the appropriate icon in the File Tree
 */

public class ImageFileTester {
    public static boolean test(File fileToTest) {
        final PathMatcher filter = fileToTest.toPath().getFileSystem().getPathMatcher("glob:**/*.{jpg,bmp,gif,png}");
        if (filter.matches(fileToTest.toPath()))
            return true;
        return false;
    }
}
