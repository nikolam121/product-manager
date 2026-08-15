package hr.tis.academy.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ImageService {
    ResponseEntity<byte[]> buildImageResponseEntity(String text, int width, int height, int red, int green, int blue);
}
