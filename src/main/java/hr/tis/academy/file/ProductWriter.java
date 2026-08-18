package hr.tis.academy.file;

import hr.tis.academy.model.Product;
import hr.tis.academy.model.ProductsMetadata;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ProductWriter {
    public static void writeProducts(ProductsMetadata productsMetadata) {
        String fileName = String.format("%d_%s_%s.txt", productsMetadata.getId(), productsMetadata.getCreatedTime(), productsMetadata.getTitle()).replace(":", "$");

        try {
            Files.createDirectories(FileSystemConfiguration.PRODUCTS_FILES_FOLDER_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                FileSystemConfiguration.PRODUCTS_FILES_FOLDER_PATH.resolve(fileName))) {
            List<Product> products = productsMetadata.getProducts();
            for (Product p : products) {
                writer.write(String.format("%-100s%10s%-10s%d\n", p.getName(), p.getPrice(), p.getUnit(), p.getGrade()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
