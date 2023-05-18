INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest'),
       ('NewUser', 'new@gmail.com', '{noop}new');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANTS (NAME, DESCRIPTION, ADDRESS, REGISTERED)
VALUES ('мясная деревня', 'специализация блюдо русской кухни', 'ул. Партизанская, дом 56', now()),
       ('пекин', 'специализация блюдо китайской кухни', 'пр. Интернациональный, дом 51', now()),
       ('помидор', 'специализация быстрой кухни', 'пр. Ленина, дом 1', now()),
       ('суши сити', 'специализация блюдо японской кухни', 'пр. Ленина, дом 10', '2023-05-01');

INSERT INTO MENUS (NAME, CREATED, restaurant_id)
VALUES ('меню мясной деревни', now(), 1),
       ('еще одно меню мясной деревни', '2023-05-01', 1),
       ('меню пекина', now(), 2),
       ('меню суши сити', now(), 4);

INSERT INTO DISHES (NAME, PRICE, MENU_ID)
VALUES ('суп', 150, 1),
       ('жаркое', 200, 1),
       ('холодец', 250, 1),
       ('салат', 150, 1),
       ('борщ', 300, 2),
       ('сало', 150, 2),
       ('мясо в кисло-сладком соусе', 400, 3),
       ('салат - пекин', 200, 3),
       ('рис', 100, 3),
       ('набор запеченных ролл', 2000, 4),
       ('салат - япония', 500, 4),
       ('бурый рис', 150, 4);
      /* ('пицца бургер', 450, 3),
       ('пицца 4 сыры', 1000, 3);*/


INSERT INTO VOTES (USER_ID, restaurant_id)
VALUES (3, 1),
       (2, 1),
       (1, 2);
