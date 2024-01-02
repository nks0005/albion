
CREATE DATABASE test_schema;
use test_schema;

CREATE TABLE user_info (
                           id INT AUTO_INCREMENT,
                           username VARCHAR(50) NOT NULL,
                           password VARCHAR(255) NOT NULL,  /* 비밀번호를 암호화하여 저장 */
                           role ENUM('admin', 'client') NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           deleted_at TIMESTAMP,
                           PRIMARY KEY(id)
);