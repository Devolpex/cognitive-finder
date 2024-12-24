CREATE DATABASE IF NOT EXISTS traccar_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE traccar_db;

CREATE USER IF NOT EXISTS 'traccar_user'@'%' IDENTIFIED BY 'traccar_password';
GRANT ALL PRIVILEGES ON traccar_db.* TO 'traccar_user'@'%';
FLUSH PRIVILEGES;
