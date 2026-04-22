CREATE TABLE books(
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(500),
  genre VARCHAR(255),
  published_year INT,
  available_copies INT,
  updated_at TIMESTAMP,
  created_at TIMESTAMP,
  description VARCHAR(500)
);