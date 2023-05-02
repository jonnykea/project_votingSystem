INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO MEAL (date_time, description, calories, user_id)
VALUES ('2020-01-30 10:00:00', 'Завтрак', 500, 1),
       ('2020-01-30 13:00:00', 'Обед', 1000, 1),
       ('2020-01-30 20:00:00', 'Ужин', 500, 1),
       ('2020-01-31 0:00:00', 'Еда на граничное значение', 100, 1),
       ('2020-01-31 10:00:00', 'Завтрак', 500, 1),
       ('2020-01-31 13:00:00', 'Обед', 1000, 1),
       ('2020-01-31 20:00:00', 'Ужин', 510, 1),
       ('2020-01-31 14:00:00', 'Админ ланч', 510, 2),
       ('2020-01-31 21:00:00', 'Админ ужин', 1500, 2);

INSERT INTO RESTAURANT (NAME, DESCRIPTION)
VALUES ('ресторан русской кухни', 'специализация блюдо русской кухни'),
       ('ресторан китайской кухни', 'специализация блюдо китайской кухни');

INSERT INTO MENU (NAME, PRICE, restaurant_id)
VALUES ('бизнес ланч', '500', 1),
       ('бизнес ланч', '1000', 2);

INSERT INTO DISH (NAME, restaurant_id)
VALUES ('суп', 1),
       ('жаркое', 1),
       ('холодец', 1),
       ('салат', 1),
       ('мясо в кисло-сладком соусе', 2),
       ('салат - пекин', 2),
       ('рис', 2);
