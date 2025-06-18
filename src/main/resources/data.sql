INSERT INTO member (email, password, created_at, modified_at) VALUES
    ('test', '1234', NOW(), NOW());

INSERT INTO holiday (date, name, is_holiday, created_at, modified_at) VALUES
    ('2025-12-25', '크리스마스', true, NOW(), NOW());

INSERT INTO room (name, max_number_of_people, created_at, modified_at) VALUES
                                                                          ('회의실 A', 8, NOW(), NOW()),
                                                                          ('회의실 B', 6, NOW(), NOW()),
                                                                          ('회의실 C', 4, NOW(), NOW()),
                                                                          ('회의실 D', 10, NOW(), NOW());

INSERT INTO reservation (reservation_date, start_time, end_time, member_id, room_id, number_of_people, created_at, modified_at) VALUES
    ('2025-06-13', 14, 16, 1, 1, 4, NOW(), NOW());

INSERT INTO canceled_reservation (reservation_date, start_time, end_time, member_id, number_of_people, created_at, modified_at) VALUES
    ('2025-06-11', 12, 14, 1, 3, NOW(), NOW());
