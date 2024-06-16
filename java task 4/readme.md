# Student Management Application

## Setup Instructions

1. **Install MySQL:**
   - Install MySQL Server if not already installed.
   - Create a new database, e.g., `studentdb`.

2. **Create the Students Table:**
   ```sql
   CREATE DATABASE studentdb;

   USE studentdb;

   CREATE TABLE students (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       age INT NOT NULL,
       grade VARCHAR(10),
       address VARCHAR(255)
   );
