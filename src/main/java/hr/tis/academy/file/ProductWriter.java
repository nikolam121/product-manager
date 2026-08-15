package hr.tis.academy.file;

import hr.tis.academy.model.Product;
import hr.tis.academy.model.ProductsMetadata;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            List<Product> listaProizvoda = productsMetadata.getProducts();
            for (Product p : listaProizvoda) {
                writer.write(String.format("%-100s%10s%-10s%d\n", p.getName(), p.getPrice(), p.getUnit(), p.getGrade()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void main() {
        List<Product> lista = new ArrayList<>();
        lista.add(new Product("Acer laptop", BigDecimal.valueOf(45.6), 5, "EUR"));
        lista.add(new Product("HP laptop", BigDecimal.valueOf(41.6), 2, "EUR"));
        ProductsMetadata productsMetadata = new ProductsMetadata(Long.valueOf(1), LocalDateTime.now(), "Naslov1", lista);

        writeProducts(productsMetadata);
    }
}
