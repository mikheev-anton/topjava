DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE meals_id_seq RESTART WITH 1;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO users (name, email, password)
VALUES ('Nikita', 'litvinkov@gmail.com', 'password');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100002);

INSERT INTO meals (date_time, description, calories, owner_id) VALUES
  ('2016-09-24 11:00:00', 'Завтрак', 700, 100000),
  ('2016-09-24 13:00:00', 'Обед', 1000, 100000),
  ('2016-09-24 16:00:00', 'Ужин', 1000, 100000),
  ('2016-09-24 10:00:00', 'Полдник', 500, 100001),
  ('2016-09-24 12:00:00', 'Дожер', 700, 100001),
  ('2016-09-24 15:00:00', 'Чипсы', 500, 100001),
  ('2016-09-24 11:00:00', 'Хороший борщ с капусткой, но не красный', 700, 100002),
  ('2016-09-24 13:00:00', 'Сосисачки', 700, 100002),
  ('2016-09-24 16:00:00', 'Какой-то непонятный салатик', 300, 100002),
  ('2016-09-24 20:00:00', 'Чай, утоляющий жажду', 50, 100002);
