package ru.javaops.topjava.service.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Dish;
import ru.javaops.topjava.model.restaurant.Menu;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.topjava.service.dish.DishTestData.*;
import static ru.javaops.topjava.service.restaurant.RestaurantTestData.RESTAURANT_ID;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class DishServiceTest {

    @Autowired
    private DishService service;

    @Test
    void getById() {
        Dish actual = service.getById(DISH_ID);
        DISH_MATCHER.assertMatch(actual, dishMealVillage1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.getById(NOT_FOUND));
    }

    @Test
    void getActualAll() {
        List<Dish> actual = service.getActualAll(RESTAURANT_ID);
        DISH_MATCHER.assertMatch(actual, dishesMealVillage);
    }

    @Test
    void getActualAllNotExisted() {
        assertThrows(NotFoundException.class, () -> service.getActualAll(NOT_FOUND));
    }

    @Test
    void getByName() {
        Dish actual = service.getByName("Суп");
        DISH_MATCHER.assertMatch(actual, dishMealVillage1);
    }

    @Test
    void geByNameNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByName("Кисель"));
    }

    @Test
    void create() {
        Dish created = service.create(getNew(), RESTAURANT_ID);
        int newId = created.id();
        Dish newRest = getNew();
        newRest.setId(newId);
        DISH_MATCHER.assertMatch(created, newRest);
        DISH_MATCHER.assertMatch(service.getById(newId), newRest);
    }

    @Test
    void createDuplicate() {
        assertThrows(DataIntegrityViolationException.class, ()
                -> service.create(new Dish(null, "суп", 150), RESTAURANT_ID));
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.create(updated, RESTAURANT_ID);
        DISH_MATCHER.assertMatch(updated, service.getById(DISH_ID));
    }

    @Test
    void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.create(getUpdated(), NOT_FOUND));
    }

    @Test
    void delete() {
        service.delete(DISH_ID);
        assertThrows(NotFoundException.class, () -> service.getById(DISH_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }
}