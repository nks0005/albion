CREATE
    DATABASE test_schema;
use
    test_schema;

CREATE TABLE admin_info
(
    id         INT AUTO_INCREMENT,
    username   VARCHAR(50)              NOT NULL,
    password   VARCHAR(255)             NOT NULL, /* 비밀번호를 암호화하여 저장 */
    role       ENUM ('admin', 'client') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    PRIMARY KEY (id)
);


CREATE TABLE battlelog_info
(
    id          INT AUTO_INCREMENT,
    battle_id   INT UNIQUE                NOT NULL,
    match_type  ENUM ('22', '55', '1010') NOT NULL,
    battle_time TIMESTAMP                 NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE match_info
(
    id        INT AUTO_INCREMENT,
    battle_id INT NOT NULL,

    user_name VARCHAR(100) NOT NULL,

    weapon_id INT NOT NULL,

    match_state ENUM('win', 'loss') NOT NULL,

    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (battle_id) REFERENCES battlelog_info (battle_id),
    FOREIGN KEY (weapon_id) REFERENCES weapon_id (id)
);

CREATE TABLE weapon_id(
    id INT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,

    PRIMARY KEY (id)
);


CREATE TABLE user_match_info
(
    id          INT AUTO_INCREMENT,
    battle_id   INT                  NOT NULL,

    user_uid    VARCHAR(100)         NOT NULL,

    gearset_id  INT                  NOT NULL,

    match_state ENUM ('win', 'loss') NOT NULL,

    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (battle_id) REFERENCES battlelog_info (battle_id),
    FOREIGN KEY (gearset_id) REFERENCES gearset_info (id),
    FOREIGN KEY (user_uid) REFERENCES user_info (uid)
);

CREATE TABLE user_info
(
    id   INT AUTO_INCREMENT,

    uid  VARCHAR(100) UNIQUE NOT NULL UNIQUE,

    name VARCHAR(255) UNIQUE NOT NULL,


    PRIMARY KEY (id)
);

CREATE TABLE gearset_info
(
    id           INT AUTO_INCREMENT,

    main_hand_id INT NOT NULL,
    off_hand_id  INT,
    head_id      INT,
    armor_id     INT,
    shoes_id     INT,
    cape_id      INT,

    PRIMARY KEY (id),
    FOREIGN KEY (main_hand_id) REFERENCES gear_info (id),
    FOREIGN KEY (off_hand_id) REFERENCES gear_info (id),
    FOREIGN KEY (head_id) REFERENCES gear_info (id),
    FOREIGN KEY (armor_id) REFERENCES gear_info (id),
    FOREIGN KEY (shoes_id) REFERENCES gear_info (id),
    FOREIGN KEY (cape_id) REFERENCES gear_info (id)
);

CREATE TABLE gear_info
(
    id   INT AUTO_INCREMENT,

    name VARCHAR(255) NOT NULL UNIQUE,

    PRIMARY KEY (id)
);