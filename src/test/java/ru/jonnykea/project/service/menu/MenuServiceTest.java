package ru.jonnykea.project.service.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Menu;
import ru.jonnykea.project.service.restaurant.RestaurantTestData;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.jonnykea.project.service.menu.MenuTestData.MENU_MATCHER;
import static ru.jonnykea.project.service.menu.MenuTestData.menuMealVillage;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MenuServiceTest {

    @Autowired
    private MenuService service;

    @Test
    void getById() {
        Menu actual = service.getByRestaurantId(RestaurantTestData.RESTAURANT_ID);
        MENU_MATCHER.assertMatch(actual, menuMealVillage);
    }

    @Test
    void getNotFoundById() {
        assertThrows(NotFoundException.class, () -> service.get(MenuTestData.NOT_FOUND));
    }

    @Test
    void getActualByRestaurantId() {
        Menu actual = service.getByRestaurantId(RestaurantTestData.RESTAURANT_ID);
        MENU_MATCHER.assertMatch(actual, menuMealVillage);
    }

    @Test
    void getNotFoundByIdRestaurant() {
        assertThrows(NotFoundException.class,
                () -> service.getByRestaurantId(MenuTestData.NOT_FOUND));
    }

    @Test
    void createWithDishes() {
        Menu newMenu = MenuTestData.getNew();
        Menu created = service.create(newMenu, RestaurantTestData.RESTAURANT_NEW_ID);
        int newId = created.id();
        Menu newRest = MenuTestData.getNew();
        newRest.setId(newId);
        MENU_MATCHER.assertMatch(created, newRest);
        MENU_MATCHER.assertMatch(service.getByRestaurantId(RestaurantTestData.RESTAURANT_NEW_ID), newRest);
    }

    @Test
    void createDuplicate() {
        assertThrows(DataIntegrityViolationException.class, ()
                -> service.create(new Menu(null, "меню мясной деревни"), RestaurantTestData.RESTAURANT_ID));
    }

    @Test
    void update() {
        Menu updated = MenuTestData.getUpdated();
        service.create(updated, RestaurantTestData.RESTAURANT_ID);
        MENU_MATCHER.assertMatch(updated, service.getByRestaurantId(RestaurantTestData.RESTAURANT_ID));
    }

    @Test
    void updateNotOwn() {
        assertThrows(NotFoundException.class,
                () -> service.create(MenuTestData.getUpdated(), MenuTestData.NOT_FOUND));
    }

    @Test
    void delete() {
        service.delete(MenuTestData.MENU_ID);
        assertThrows(NotFoundException.class,
                () -> service.getByRestaurantId(MenuTestData.MENU_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.delete(MenuTestData.NOT_FOUND));
    }
}