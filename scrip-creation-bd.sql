-- @author ouziri
-- @version 0.1

-- Création de la base bankdb
CREATE DATABASE IF NOT EXISTS bankdb;
USE bankdb;

-- Création de la table Users
CREATE TABLE IF NOT EXISTS users (
    email VARCHAR(100) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    roles VARCHAR(20) NOT NULL
);

-- Création de la table Accounts
CREATE TABLE IF NOT EXISTS accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    owner_email VARCHAR(100) NOT NULL,
    balance DOUBLE(10,2) DEFAULT 0,
    FOREIGN KEY (owner_email) REFERENCES users(email)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Insertion dadu compte admin
INSERT INTO users (email, name, password, roles)
VALUES ('admin@efrei', 'Admin', 'a123', 'ADMIN');
