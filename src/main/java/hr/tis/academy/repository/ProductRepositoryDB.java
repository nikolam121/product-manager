package hr.tis.academy.repository;

import hr.tis.academy.db.Database;
import hr.tis.academy.db.DatabaseException;
import hr.tis.academy.model.Product;
import hr.tis.academy.model.ProductsMetadata;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("db")
public class ProductRepositoryDB implements ProductRepository {

    public String fetchKorisnik(Long id) {
        String querySQL = "SELECT * FROM USERS WHERE ID = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(querySQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return String.format("%s %s", resultSet.getLong("ID"),
                        resultSet.getString("NAME"));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return null;
    }

    public List<String> fetchAll() {
        String querySQL = "SELECT * FROM USERS";
        List<String> users = new ArrayList<>();
        try (Connection connection = Database.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(querySQL);
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                users.add(String.format("%s %s", id, name));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return users;


    }

    public Long insertProducts(ProductsMetadata productsMetadata) {
        String recordSQL = "INSERT INTO PRODUCTS_METADATA (CREATED_TIME, TITLE) VALUES (?, ?)";
        String productSQL = "INSERT INTO PRODUCTS (NAME, PRICE, PRODUCTS_METADATA_ID) VALUES (?, ?, ?)";
        long recordId;
        try (Connection connection = Database.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement recordStmt = connection.prepareStatement(recordSQL,
                    Statement.RETURN_GENERATED_KEYS)) {
                recordStmt.setDate(1, Date.valueOf(productsMetadata.getCreatedTime().toLocalDate()));
                recordStmt.setString(2, productsMetadata.getTitle());
                recordStmt.executeUpdate();
                try (ResultSet generatedKeys = recordStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        recordId = generatedKeys.getLong(1);
                        setupProductBatch(connection, productsMetadata, productSQL, recordId);
                    } else {
                        throw new DatabaseException("Creating PRODUCTS_METADATA failed, no ID obtained.");
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return recordId;
    }

    private void setupProductBatch(Connection connection, ProductsMetadata productsMetadata, String productSQL, Long recordId) {
        try (PreparedStatement productStmt = connection.prepareStatement(productSQL)) {
            for (Product product : productsMetadata.getProducts()) {
                productStmt.setString(1, product.getName());
                productStmt.setBigDecimal(2, product.getPrice());
                productStmt.setLong(3, recordId);
                productStmt.addBatch();
            }
            productStmt.executeBatch();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public BigDecimal fetchSumOfPrices(LocalDate createdDate) {
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT SUM(PRODUCTS.PRICE) FROM PRODUCTS_METADATA\n" +
                     "JOIN PRODUCTS ON PRODUCTS_METADATA.PRODUCTS_METADATA_ID = PRODUCTS.PRODUCTS_METADATA_ID\n" +
                     "WHERE PRODUCTS_METADATA.CREATED_TIME = ?;")) {
            statement.setDate(1, Date.valueOf(createdDate));
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getBigDecimal("SUM(PRODUCTS.PRICE)");
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public BigDecimal fetchSumOfPrices(Long id) {
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT SUM(PRODUCTS.PRICE) FROM PRODUCTS_METADATA\n" +
                     "JOIN PRODUCTS ON PRODUCTS_METADATA.PRODUCTS_METADATA_ID = PRODUCTS.PRODUCTS_METADATA_ID\n" +
                     "WHERE PRODUCTS_METADATA.PRODUCTS_METADATA_ID = ?;")) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getBigDecimal("SUM(PRODUCTS.PRICE)");
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public ProductsMetadata fetchProductsMetadata(LocalDate createdDate) {
        List<Product> listaProizvoda = new ArrayList<>();

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement metadataStatement = connection.prepareStatement("SELECT * FROM PRODUCTS_METADATA\n" +
                     "WHERE PRODUCTS_METADATA.CREATED_TIME = ?;")) {
            metadataStatement.setDate(1, Date.valueOf(createdDate));
            ResultSet metadataRs = metadataStatement.executeQuery();
            while (metadataRs.next()) {
                try (PreparedStatement productStatement = connection.prepareStatement("SELECT PRODUCTS.* FROM PRODUCTS_METADATA\n" +
                        "JOIN PRODUCTS ON PRODUCTS_METADATA.PRODUCTS_METADATA_ID = PRODUCTS.PRODUCTS_METADATA_ID\n" +
                        "WHERE PRODUCTS_METADATA.CREATED_TIME = ?;")) {
                        productStatement.setDate(1, Date.valueOf(createdDate));
                        ResultSet productRs = productStatement.executeQuery();
                    while (productRs.next()) {
                        listaProizvoda.add(new Product(productRs.getString("NAME"), productRs.getBigDecimal("PRICE"), productRs.getInt("GRADE"), productRs.getString("UNIT")));
                    }
                }
            }
            return new ProductsMetadata(metadataRs.getLong("PRODUCTS_METADATA_ID"), metadataRs.getObject("CREATED_TIME", LocalDateTime.class), metadataRs.getString("TITLE"), listaProizvoda);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public ProductsMetadata fetchProductsMetadata(Long id) {
        List<Product> listaProizvoda = new ArrayList<>();

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement metadataStatement = connection.prepareStatement("SELECT * FROM PRODUCTS_METADATA\n" +
                     "WHERE PRODUCTS_METADATA.PRODUCTS_METADATA_ID = ?;")) {
            metadataStatement.setLong(1, id);
            ResultSet metadataRs = metadataStatement.executeQuery();
            while (metadataRs.next()) {
                try (PreparedStatement productStatement = connection.prepareStatement("SELECT PRODUCTS.* FROM PRODUCTS_METADATA\n" +
                        "JOIN PRODUCTS ON PRODUCTS_METADATA.PRODUCTS_METADATA_ID = PRODUCTS.PRODUCTS_METADATA_ID\n" +
                        "WHERE PRODUCTS_METADATA.PRODUCTS_METADATA_ID = ?;")) {
                    productStatement.setLong(1, id);
                    ResultSet productRs = productStatement.executeQuery();
                    while (productRs.next()) {
                        listaProizvoda.add(new Product(productRs.getString("NAME"), productRs.getBigDecimal("PRICE"), productRs.getInt("GRADE"), productRs.getString("UNIT")));
                    }
                }
            }
            return new ProductsMetadata(metadataRs.getLong("PRODUCTS_METADATA_ID"), metadataRs.getObject("CREATED_TIME", LocalDateTime.class), metadataRs.getString("TITLE"), listaProizvoda);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Integer fetchProductsMetadataCount() {
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(PRODUCTS_METADATA.PRODUCTS_METADATA_ID) AS RECORD_COUNT FROM PRODUCTS_METADATA")) {
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("RECORD_COUNT");
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public List<ProductsMetadata> fetchAllProductsMetadataByDate(LocalDate createdDate) {

        List<ProductsMetadata> listaMetadata = new ArrayList<>();
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement metadataStatement = connection.prepareStatement("SELECT * FROM PRODUCTS_METADATA\n" +
                     "WHERE PRODUCTS_METADATA.CREATED_TIME = ?;")) {
            metadataStatement.setDate(1, Date.valueOf(createdDate));

            ResultSet metadataRs = metadataStatement.executeQuery();
            while (metadataRs.next()) {
                List<Product> listaProizvoda = new ArrayList<>();
                Long id = metadataRs.getLong("PRODUCTS_METADATA_ID");
                try (PreparedStatement productStatement = connection.prepareStatement("SELECT PRODUCTS.* FROM PRODUCTS\n" +
                        "WHERE PRODUCTS.PRODUCTS_METADATA_ID = ?")) {
                    productStatement.setLong(1, id);
                    ResultSet productRs = productStatement.executeQuery();

                    while (productRs.next()) {
                        listaProizvoda.add(new Product(productRs.getString("NAME"), productRs.getBigDecimal("PRICE"), productRs.getInt("GRADE"), productRs.getString("UNIT")));
                    }
                }
                listaMetadata.add(new ProductsMetadata(metadataRs.getLong("PRODUCTS_METADATA_ID"), metadataRs.getObject("CREATED_TIME", LocalDateTime.class), metadataRs.getString("TITLE"), listaProizvoda));

            }
           return listaMetadata;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}