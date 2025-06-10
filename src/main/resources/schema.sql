CREATE TABLE IF NOT EXISTS member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS holiday (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    name VARCHAR(255) NOT NULL,
    is_holiday BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reservation_date DATE NOT NULL,
    start_time INT NOT NULL,
    end_time INT NOT NULL,
    member_id BIGINT NOT NULL,
    number_of_people INT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE IF NOT EXISTS canceled_reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reservation_date DATE NOT NULL,
    start_time INT NOT NULL,
    end_time INT NOT NULL,
    member_id BIGINT NOT NULL,
    number_of_people INT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id)
);
