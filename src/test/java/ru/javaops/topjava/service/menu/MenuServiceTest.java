package ru.javaops.topjava.service.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Menu;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.topjava.service.menu.MenuTestData.*;
import static ru.javaops.topjava.service.restaurant.RestaurantTestData.RESTAURANT_ID;
import static ru.javaops.topjava.service.restaurant.RestaurantTestData.RESTAURANT_NEW_ID;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MenuServiceTest {
    @Autowired
    private MenuService service;

    @Test
    void getByRestaurantId() {
        Menu actual = service.getByRestaurantId(MENU_ID + 1);
        MENU_MATCHER.assertMatch(actual, menuPekin);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByRestaurantId(NOT_FOUND));
    }

    @Test
    void createWithDishes() {
        Menu newMenu = getNew();
        Menu created = service.create(newMenu, RESTAURANT_NEW_ID);
        int newId = created.id();
        Menu newRest = getNew();
        newRest.setId(newId);
        MENU_WITH_RESTARAUNT_AND_DISHES_MATCHER.assertMatch(created, newRest);
        MENU_WITH_RESTARAUNT_AND_DISHES_MATCHER.assertMatch(service.getByRestaurantId(RESTAURANT_NEW_ID), newRest);
    }

    @Test
    void update() {
        Menu updated = getUpdated();
        service.create(updated, RESTAURANT_ID);
        MENU_MATCHER.assertMatch(updated, service.getByRestaurantId(RESTAURANT_ID));
    }

    @Test
    void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.create(getUpdated(), NOT_FOUND));
    }

    @Test
    void delete() {
        service.delete(MENU_ID);
        assertThrows(NotFoundException.class, () -> service.getByRestaurantId(MENU_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }
}