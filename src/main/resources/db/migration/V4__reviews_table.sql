CREATE TABLE reviews (
    id INT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP,
    rating INT,
    comment VARCHAR(255)
);