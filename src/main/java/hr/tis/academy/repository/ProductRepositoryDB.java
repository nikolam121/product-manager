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

    public String fetchUser(Long id) {
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
        String productSQL = "INSERT INTO PRODUCTS (NAME, PRICE, UNIT, GRADE, PRODUCTS_METADATA_ID) VALUES (?, ?, ?, ?, ?)";
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
                productStmt.setString(3, product.getUnit());
                productStmt.setInt(4, product.getGrade());
                productStmt.setLong(5, recordId);
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
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement metadataStatement = connection.prepareStatement("SELECT * FROM PRODUCTS_METADATA\n" +
                     "WHERE PRODUCTS_METADATA.CREATED_TIME = ?;")) {
            metadataStatement.setDate(1, Date.valueOf(createdDate));
            ResultSet metadataRs = metadataStatement.executeQuery();

            if (!metadataRs.next()) {
                return null;
            }

            Long id = metadataRs.getLong("PRODUCTS_METADATA_ID");
            LocalDateTime createdTime = metadataRs.getObject("CREATED_TIME", LocalDateTime.class);
            String title = metadataRs.getString("TITLE");

            return new ProductsMetadata(id, createdTime, title, fetchProductsForMetadata(connection, id));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public ProductsMetadata fetchProductsMetadata(Long id) {
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement metadataStatement = connection.prepareStatement("SELECT * FROM PRODUCTS_METADATA\n" +
                     "WHERE PRODUCTS_METADATA.PRODUCTS_METADATA_ID = ?;")) {
            metadataStatement.setLong(1, id);
            ResultSet metadataRs = metadataStatement.executeQuery();

            if (!metadataRs.next()) {
                return null;
            }

            LocalDateTime createdTime = metadataRs.getObject("CREATED_TIME", LocalDateTime.class);
            String title = metadataRs.getString("TITLE");

            return new ProductsMetadata(id, createdTime, title, fetchProductsForMetadata(connection, id));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    private List<Product> fetchProductsForMetadata(Connection connection, Long metadataId) throws SQLException {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement productStatement = connection.prepareStatement(
                "SELECT * FROM PRODUCTS WHERE PRODUCTS.PRODUCTS_METADATA_ID = ?;")) {
            productStatement.setLong(1, metadataId);
            ResultSet productRs = productStatement.executeQuery();
            while (productRs.next()) {
                products.add(new Product(productRs.getString("NAME"), productRs.getBigDecimal("PRICE"), productRs.getInt("GRADE"), productRs.getString("UNIT")));
            }
        }
        return products;
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
        List<ProductsMetadata> metadataList = new ArrayList<>();
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement metadataStatement = connection.prepareStatement("SELECT * FROM PRODUCTS_METADATA\n" +
                     "WHERE PRODUCTS_METADATA.CREATED_TIME = ?;")) {
            metadataStatement.setDate(1, Date.valueOf(createdDate));

            ResultSet metadataRs = metadataStatement.executeQuery();
            while (metadataRs.next()) {
                Long id = metadataRs.getLong("PRODUCTS_METADATA_ID");
                LocalDateTime createdTime = metadataRs.getObject("CREATED_TIME", LocalDateTime.class);
                String title = metadataRs.getString("TITLE");

                metadataList.add(new ProductsMetadata(id, createdTime, title, fetchProductsForMetadata(connection, id)));
            }
            return metadataList;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
