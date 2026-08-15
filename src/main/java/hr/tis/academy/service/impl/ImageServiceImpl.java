package hr.tis.academy.service.impl;

import hr.tis.academy.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import hr.tis.academy.service.exception.InvalidImageParametersException;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public ResponseEntity<byte[]> buildImageResponseEntity(String text, int width, int height, int red, int green, int blue) {
        if (checkInput(red, green, blue)) {
            BufferedImage bufferedImage = createImage(text, width, height, red, green, blue);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try {
                ImageIO.write(bufferedImage, "png", out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);
        } else {
            throw new InvalidImageParametersException(
                    String.format("Invalid RGB values: red=%d, green=%d, blue=%d. Each must be between 0 and 255.", red, green, blue));
        }

    }

    private boolean checkInput(int red, int green, int blue) {
        return red <= 255 && red >= 0 && green <= 255 && green >= 0 && blue <= 255 && blue >= 0;
    }

    private BufferedImage createImage(String text, int width, int height, int red, int green, int blue) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = bufferedImage.createGraphics();
        try {
            Font segoe = new Font("Segoe UI", Font.PLAIN, 50);
            FontMetrics metrics = graphics.getFontMetrics(segoe);

            graphics.setPaint(new Color(red, green, blue));
            graphics.fillRect(0, 0, width, height);

            graphics.setColor(Color.WHITE);
            graphics.setFont(segoe);

            String label = text == null ? "" : text;
            int textX = (width - metrics.stringWidth(label)) / 2;
            int textY = (height - metrics.getHeight()) / 2 + metrics.getAscent();

            graphics.drawString(label, textX, textY);
        } finally {
            graphics.dispose();
        }

        return bufferedImage;
    }


}
