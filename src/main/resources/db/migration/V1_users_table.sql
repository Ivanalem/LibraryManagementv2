CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    status VARCHAR(255),
    updated_at TIMESTAMP,
    created_at TIMESTAMP,
    role VARCHAR(255)
);