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
alter table battlelog_info add column process INT DEFAULT 0;


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


# 2v2, 5v5, 10v10 별도 테이블 구성
CREATE TABLE DuoComps (
    id INT AUTO_INCREMENT PRIMARY KEY,

    A_main_hand_id INT NOT NULL,
    B_main_hand_id INT NOT NULL,

    win INT NOT NULL,
    lose INT NOT NULL,


    FOREIGN KEY (A_main_hand_id) REFERENCES gear_info (id),
    FOREIGN KEY (B_main_hand_id) REFERENCES gear_info (id)
);

CREATE TABLE FiveComps (
                          id INT AUTO_INCREMENT PRIMARY KEY,

                          A_main_hand_id INT NOT NULL,
                          B_main_hand_id INT NOT NULL,
                          C_main_hand_id INT NOT NULL,
                          D_main_hand_id INT NOT NULL,
                          E_main_hand_id INT NOT NULL,

                          win INT NOT NULL,
                          lose INT NOT NULL,


                          FOREIGN KEY (A_main_hand_id) REFERENCES gear_info (id),
                          FOREIGN KEY (B_main_hand_id) REFERENCES gear_info (id)
);
CREATE TABLE TenComps (
                          id INT AUTO_INCREMENT PRIMARY KEY,

                          A_main_hand_id INT NOT NULL,
                          B_main_hand_id INT NOT NULL,
                          C_main_hand_id INT NOT NULL,
                          D_main_hand_id INT NOT NULL,
                          E_main_hand_id INT NOT NULL,
                          F_main_hand_id INT NOT NULL,
                          G_main_hand_id INT NOT NULL,
                          H_main_hand_id INT NOT NULL,
                          I_main_hand_id INT NOT NULL,
                          J_main_hand_id INT NOT NULL,

                          win INT NOT NULL,
                          lose INT NOT NULL,


                          FOREIGN KEY (A_main_hand_id) REFERENCES gear_info (id),
                          FOREIGN KEY (B_main_hand_id) REFERENCES gear_info (id)
);


-- DuoComps에 대한 뷰
CREATE VIEW DuoCompSummary AS
SELECT dc.id, dc.win, dc.lose, (dc.win + dc.lose) AS total_matches,
       A_main_hand_gi.name AS A_main_hand, B_main_hand_gi.name AS B_main_hand
FROM DuoComps dc
         JOIN gear_info A_main_hand_gi ON dc.A_main_hand_id = A_main_hand_gi.id
         JOIN gear_info B_main_hand_gi ON dc.B_main_hand_id = B_main_hand_gi.id;

-- FiveComps에 대한 뷰
CREATE VIEW FiveCompSummary AS
SELECT fc.id, fc.win, fc.lose, (fc.win + fc.lose) AS total_matches,
       A_main_hand_gi.name AS A_main_hand, B_main_hand_gi.name AS B_main_hand,
       C_main_hand_gi.name AS C_main_hand, D_main_hand_gi.name AS D_main_hand,
       E_main_hand_gi.name AS E_main_hand
FROM FiveComps fc
         JOIN gear_info A_main_hand_gi ON fc.A_main_hand_id = A_main_hand_gi.id
         JOIN gear_info B_main_hand_gi ON fc.B_main_hand_id = B_main_hand_gi.id
         JOIN gear_info C_main_hand_gi ON fc.C_main_hand_id = C_main_hand_gi.id
         JOIN gear_info D_main_hand_gi ON fc.D_main_hand_id = D_main_hand_gi.id
         JOIN gear_info E_main_hand_gi ON fc.E_main_hand_id = E_main_hand_gi.id;

-- TenComps에 대한 뷰
CREATE VIEW TenCompSummary AS
SELECT tc.id, tc.win, tc.lose, (tc.win + tc.lose) AS total_matches,
       A_main_hand_gi.name AS A_main_hand, B_main_hand_gi.name AS B_main_hand,
       C_main_hand_gi.name AS C_main_hand, D_main_hand_gi.name AS D_main_hand,
       E_main_hand_gi.name AS E_main_hand, F_main_hand_gi.name AS F_main_hand,
       G_main_hand_gi.name AS G_main_hand, H_main_hand_gi.name AS H_main_hand,
       I_main_hand_gi.name AS I_main_hand, J_main_hand_gi.name AS J_main_hand
FROM TenComps tc
         JOIN gear_info A_main_hand_gi ON tc.A_main_hand_id = A_main_hand_gi.id
         JOIN gear_info B_main_hand_gi ON tc.B_main_hand_id = B_main_hand_gi.id
         JOIN gear_info C_main_hand_gi ON tc.C_main_hand_id = C_main_hand_gi.id
         JOIN gear_info D_main_hand_gi ON tc.D_main_hand_id = D_main_hand_gi.id
         JOIN gear_info E_main_hand_gi ON tc.E_main_hand_id = E_main_hand_gi.id
         JOIN gear_info F_main_hand_gi ON tc.F_main_hand_id = F_main_hand_gi.id
         JOIN gear_info G_main_hand_gi ON tc.G_main_hand_id = G_main_hand_gi.id
         JOIN gear_info H_main_hand_gi ON tc.H_main_hand_id = H_main_hand_gi.id
         JOIN gear_info I_main_hand_gi ON tc.I_main_hand_id = I_main_hand_gi.id
         JOIN gear_info J_main_hand_gi ON tc.J_main_hand_id = J_main_hand_gi.id;
