package ru.javaops.topjava.service.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Menu;

import static org.junit.jupiter.api.Assertions.*;
import static ru.javaops.topjava.service.dish.DishTestData.dishMealVillage1;
import static ru.javaops.topjava.service.menu.MenuTestData.*;
import static ru.javaops.topjava.service.restaurant.RestaurantTestData.NOT_FOUND;
import static ru.javaops.topjava.service.restaurant.RestaurantTestData.RESTAURANT_ID;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MenuServiceTest {
    @Autowired
    private MenuService service;

    @Test
    void getById() {
        Menu actual = service.getById(MENU_ID + 1);
        MENU_MATCHER.assertMatch(actual, menuMealPekin);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.getById(NOT_FOUND));
    }

    @Test
    void getByRestaurantId() {
    }

    @Test
    void getByName() {
        Menu actual = service.getByName("меню мясной деревни");
        MENU_WITH_RESTARAUNT_AND_DISHES_MATCHER.assertMatch(actual, menuMealVillage);
    }

    @Test
    void getByNameWithDishes() {
        Menu actual = service.getByRestaurantIdWithDishes(RESTAURANT_ID);
        MENU_MATCHER.assertMatch(actual, menuMealVillage);
    }

    @Test
    void geByNameNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByName("шанс"));
    }

    @Test
    void delete() {
    }

    @Test
    void create() {
    }

    @Test
    void createWithDishes() {
    }
}