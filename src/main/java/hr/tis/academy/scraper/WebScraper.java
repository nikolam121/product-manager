package hr.tis.academy.scraper;

import hr.tis.academy.model.Product;
import hr.tis.academy.model.ProductsMetadata;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class WebScraper {
    @Value("${base.url}")
    private String baseUrl;
    @Value("${broj.iteracija}")
    private int brojIteracija;

    public ProductsMetadata fetchProducts() {
        ProductsMetadata metadata = null;
        try {
            for (int page = 1; page <= brojIteracija; page++) {
                Document doc = Jsoup.connect(baseUrl + "?page=" + page).userAgent("Mozilla/5.0").get();

                if (metadata == null) {
                    Element title = doc.select("title").first();
                    metadata = new ProductsMetadata(1L, LocalDateTime.now(), title.text(), new ArrayList<>());
                }

                for (Element el : doc.select("div[data-ga-type=productImpression]")) {

                    String name = el.attr("data-ga-name");
                    String currency = el.attr("data-ga-currency");

                    String rawPrice = el.attr("data-ga-price").replace("€", "").replace(",", ".").trim();
                    BigDecimal price = new BigDecimal(rawPrice);

                    metadata.getProducts().add(new Product(name, price, 1, currency));
                }
            }

        } catch (IOException e) {
            System.err.println("Greška: " + e.getMessage());
        }

        return metadata;
    }
}