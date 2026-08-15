package hr.tis.academy.file;

import hr.tis.academy.model.Product;
import hr.tis.academy.model.ProductsMetadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductReader {

    public static ProductsMetadata read(String fileName){
        String[] parts = fileName.split("_",3);
        Long id = Long.valueOf(parts[0]);
        LocalDateTime createdTime = LocalDateTime.parse(parts[1].replace("$", ":"));
        String title = parts[2].substring(0, parts[2].length() - ".txt".length());

        List<Product> products = new ArrayList<>();

        try (BufferedReader reader =
                     Files.newBufferedReader(FileSystemConfiguration.PRODUCTS_FILES_FOLDER_PATH.resolve(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String naziv = line.substring(0, 100).trim();
                BigDecimal cijena = new BigDecimal(line.substring(100, 110).trim());
                String mjernaJedinica = line.substring(110, 120).trim();
                Integer ocjena = Integer.parseInt(line.substring(120, 121).trim());

                products.add(new Product(naziv, cijena, ocjena, mjernaJedinica));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ProductsMetadata(id, createdTime, title, products);
    }
}


