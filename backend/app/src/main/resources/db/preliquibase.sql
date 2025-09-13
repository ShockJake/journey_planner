-- Pre-Liquibase initialization script for MySQL
-- Ensure that the database default charset and collation are set to UTF-8

-- Replace 'journey_planner' with your actual database name if different
ALTER DATABASE mydatabase CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Ensure session defaults also use UTF-8
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;