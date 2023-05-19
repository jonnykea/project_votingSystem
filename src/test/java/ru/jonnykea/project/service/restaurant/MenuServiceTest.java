package ru.jonnykea.project.service.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Menu;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.jonnykea.project.service.restaurant.MenuTestData.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MenuServiceTest {

    @Autowired
    private MenuService service;

    @Test
    void getById() {
        Menu actual = service.getByToday(RestaurantTestData.RESTAURANT_ID);
        MENU_MATCHER.assertMatch(actual, menuMealVillage);
    }

    @Test
    void getNotFoundById() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getActualByRestaurantId() {
        Menu actual = service.getByToday(RestaurantTestData.RESTAURANT_ID);
        MENU_MATCHER.assertMatch(actual, menuMealVillage);
    }

    @Test
    void getNotFoundByIdRestaurant() {
        assertThrows(NotFoundException.class,
                () -> service.getByToday(NOT_FOUND));
    }

    @Test
    void createWithDishes() {
        Menu created = service.save(getNew(), RestaurantTestData.RESTAURANT_NEW_ID);
        int newId = created.id();
        Menu newMenu = getNew();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
    }

    @Test
    void createDuplicate() {
        assertThrows(DataIntegrityViolationException.class, ()
                -> service.save(new Menu(null, "меню мясной деревни"), RestaurantTestData.RESTAURANT_ID));
    }

    @Test
    void update() {
        Menu updated = service.save(getUpdated(), RestaurantTestData.RESTAURANT_ID);
        MENU_MATCHER.assertMatch(getUpdated(), updated);
    }

    @Test
    void updateNotOwn() {
        assertThrows(NotFoundException.class,
                () -> service.save(getUpdated(), MenuTestData.NOT_FOUND));
    }

    @Test
    void delete() {
        service.delete(MenuTestData.MENU_ID);
        assertThrows(NotFoundException.class,
                () -> service.getByToday(MENU_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.delete(NOT_FOUND));
    }
}