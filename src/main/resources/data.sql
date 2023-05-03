INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (NAME, DESCRIPTION, ADDRESS, REGISTERED)
VALUES ('МЯСНАЯ ДЕРЕВНЯ', 'специализация блюдо русской кухни', 'ул. Партизанская, дом 56', now()),
       ('ПЕКИН', 'специализация блюдо китайской кухни', 'пр. Интернациональный, дом 51', now()),
       ('СУШИ СИТИ', 'специализация блюдо японской кухни', 'пр. Ленина, дом 10', '2023-05-01');

INSERT INTO MENU (NAME, restaurant_id)
VALUES ('МЕНЮ МЯСНОЙ ДЕРЕВНИ', 1),
       ('МЕНЮ ПЕКИНА', 2);

INSERT INTO DISH (NAME, PRICE, restaurant_id)
VALUES ('суп', 150, 1),
       ('жаркое', 200, 1),
       ('холодец', 250, 1),
       ('салат', 150, 1),
       ('мясо в кисло-сладком соусе', 400, 2),
       ('салат - пекин', 200, 2),
       ('рис', 100, 2);

INSERT INTO VOTE (USER_ID, restaurant_id)
VALUES (3, 1),
       (2, 1),
       (1, 2);