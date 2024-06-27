package io.rizwan.chapter03.performanceOptimization.section01.imageProcessingAlgorithm;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String SOURCE_FILE = "src/main/resources/many-flowers.jpg";
    public static final String DESTINATION_FILE = "./target/many-flowers.jpg";

    public static void main(String[] args) throws IOException {
        // The buffered image represents the image data such as pixels, colors, dimensions
        // and provides us with the methods to manipulate them.
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage =
                new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();
        recolorMultiThreaded(originalImage, resultImage, 2);
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);

        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);

        System.out.println(duration);
    }

    public static void recolorMultiThreaded(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads) {
        List<Thread> threads = new ArrayList<>();

        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            int threadMultiplier = i;

            Thread thread = new Thread(() -> {
               int leftCorner = 0;
               int topCorner = height * threadMultiplier;

               recolorImage(originalImage, resultImage, leftCorner, topCorner, width, height);
            });

            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0,
                originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner,
                                    int width, int height) {
        for (int x = leftCorner; x < (leftCorner + width) && x < (originalImage.getWidth()); x++) {
            for (int y = topCorner; y < (topCorner + height) && y < originalImage.getHeight(); y++) {
                recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }

    // take the original and result image and recolor the give pixel
    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        if (isShadowOfGray(red, green, blue)) {
            red = Math.min(255, red + 10);
            green = Math.max(0, green - 80);
            blue = Math.max(0, blue - 20);
        }

        int newRGB = createRGBFromColors(red, green, blue);
        setRGB(resultImage, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    // check if the color is shade of gray.
    public static boolean isShadowOfGray(int red, int green, int blue) {
        return Math.abs(red - green) < 30 && Math.abs(green - blue) < 30 && Math.abs(blue - red) < 30;
    }

    // return the color after mixing rgb.
    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;

        rgb |= red << 16;
        rgb |= green << 8;
        rgb |= blue;

        rgb |= 0xFF000000;

        return rgb;
    }

    // take the rgb value and extract only the specific color value.
    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }
}
