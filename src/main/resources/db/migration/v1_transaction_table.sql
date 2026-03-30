CREATE TABLE transactions(
    id INT PRIMARY KEY AUTO_INCREMENT,
    transaction_type VARCHAR(255),
    transaction_date TIMESTAMP,
    due_date TIMESTAMP
);