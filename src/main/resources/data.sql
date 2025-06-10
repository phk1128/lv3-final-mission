INSERT INTO member (email, password) VALUES
('test', '1234');

INSERT INTO holiday (date, name, is_holiday) VALUES 
('2025-12-25', '크리스마스', true);

INSERT INTO reservation (reservation_date, start_time, end_time, member_id, number_of_people) VALUES 
('2025-06-13', 14, 16, 1, 4);

INSERT INTO canceled_reservation (reservation_date, start_time, end_time, member_id, number_of_people) VALUES 
('2025-06-11', 12, 14, 1, 3);
