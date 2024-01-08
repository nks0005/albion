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

CREATE TABLE connect_log
(
    id         INT AUTO_INCREMENT,
    remote_ip   VARCHAR(50)              NOT NULL,
    request_uri   VARCHAR(255)             NOT NULL,
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


CREATE TABLE user_match_info
(
    id          INT AUTO_INCREMENT,
    battle_id   INT                  NOT NULL,

    ip INT DEFAULT 0,

    user_id    int         NOT NULL,

    gearset_id  INT                  NOT NULL,

    match_state ENUM ('win', 'loss') NOT NULL,

    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (battle_id) REFERENCES battlelog_info (battle_id),
    FOREIGN KEY (gearset_id) REFERENCES gearset_info (id),
    FOREIGN KEY (user_id) REFERENCES user_info (id)
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


## 뷰
CREATE VIEW battle_details_view AS
SELECT
    um.battle_id, ui.name AS username, um.ip,
    CASE
        WHEN um.match_state = 'win' THEN 'win'
        ELSE 'loss'
        END AS match_state,
    main_hand_gear.name AS main_hand_name,
    off_hand_gear.name AS off_hand_name,
    head_gear.name AS head_name,
    armor_gear.name AS armor_name,
    shoes_gear.name AS shoes_name,
    cape_gear.name AS cape_name,
    battlelog.match_type AS match_type,
    battlelog.battle_time AS battle_time
FROM
    user_match_info um
        JOIN
    user_info ui ON um.user_id = ui.id
        JOIN
    gearset_info gi ON um.gearset_id = gi.id
        JOIN
    gear_info main_hand_gear ON gi.main_hand_id = main_hand_gear.id
        JOIN
    gear_info off_hand_gear ON gi.off_hand_id = off_hand_gear.id
        JOIN
    gear_info head_gear ON gi.head_id = head_gear.id
        JOIN
    gear_info armor_gear ON gi.armor_id = armor_gear.id
        JOIN
    gear_info shoes_gear ON gi.shoes_id = shoes_gear.id
        JOIN
    gear_info cape_gear ON gi.cape_id = cape_gear.id
        JOIN
    battlelog_info battlelog ON um.battle_id = battlelog.battle_id;



CREATE VIEW user_win_loss_count_per_match_type AS
SELECT
    u.id AS user_id,
    u.name AS user_name,
    b.match_type,
    COUNT(CASE WHEN umi.match_state = 'win' THEN 1 END) AS win_count,
    COUNT(CASE WHEN umi.match_state = 'loss' THEN 1 END) AS loss_count
FROM
    user_info u
        JOIN
    user_match_info umi ON u.id = umi.user_id
        JOIN
    battlelog_info b ON umi.battle_id = b.battle_id
GROUP BY
    u.id, u.name, b.match_type;


