package services.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public interface ImageReaderUtil {
     <T extends Image> Optional<T> readImage (String imagePath);
}
