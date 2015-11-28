package JImageViewer.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Used to rotate an Image. A lot of black magic going on here that I'm not really sure about.
 */

public class RotateImage {
    public static BufferedImage getRotatedImage(BufferedImage bufferedImage, int direction) {
        BufferedImage rot = new BufferedImage(bufferedImage.getHeight(), bufferedImage.getWidth(), BufferedImage.TYPE_INT_RGB);
        AffineTransform xform = new AffineTransform();
        xform.translate(0.5*bufferedImage.getHeight(), 0.5*bufferedImage.getWidth());
        xform.rotate(Math.PI/2*direction);
        xform.translate(-0.5*bufferedImage.getWidth(), -0.5*bufferedImage.getHeight());
        Graphics2D g = rot.createGraphics();
        g.drawImage(bufferedImage, xform, null);
        g.dispose();

        return rot;
    }
}
