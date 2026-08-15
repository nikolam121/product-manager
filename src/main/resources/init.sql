CREATE TABLE IF NOT EXISTS USERS (
                                         ID INT AUTO_INCREMENT PRIMARY KEY,
                                         NAME VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS PRODUCTS_METADATA (
                                                 PRODUCTS_METADATA_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 CREATED_TIME DATE NOT NULL,
                                                 TITLE VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS PRODUCTS (
                                        PRODUCTS_METADATA_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        METADATA_ID BIGINT NOT NULL,
                                        NAME VARCHAR(255) NOT NULL,
                                        PRICE DECIMAL(10, 2) NOT NULL,
                                        UNIT VARCHAR(50),
                                        GRADE INT DEFAULT 0,
                                        FOREIGN KEY (METADATA_ID) REFERENCES PRODUCTS_METADATA(PRODUCTS_METADATA_ID) ON DELETE CASCADE
);

INSERT INTO USERS (NAME) VALUES ('Ivan Horvat');
INSERT INTO USERS (NAME) VALUES ('Ana Kovač');
INSERT INTO USERS (NAME) VALUES ('Marko Perić');

INSERT INTO PRODUCTS_METADATA (CREATED_TIME, TITLE) VALUES ('2026-07-24', 'Ponuda 24.7.');
INSERT INTO PRODUCTS_METADATA (CREATED_TIME, TITLE) VALUES ('2026-07-25', 'Ponuda 25.7.');
INSERT INTO PRODUCTS_METADATA (CREATED_TIME, TITLE) VALUES ('2026-07-26', 'Ponuda 26.7.');

INSERT INTO PRODUCTS (METADATA_ID, NAME, PRICE, UNIT, GRADE) VALUES (1, 'Mlijeko 1L', 1.20, 'EUR/kom', 4);
INSERT INTO PRODUCTS (METADATA_ID, NAME, PRICE, UNIT, GRADE) VALUES (2, 'Kruh polubijeli', 0.85, 'EUR/kom', 3);
INSERT INTO PRODUCTS (METADATA_ID, NAME, PRICE, UNIT, GRADE) VALUES (3, 'Jaja M 10/1', 1.99, 'EUR/kom', 5);

