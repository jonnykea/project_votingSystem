package ru.jonnykea.project.service.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Dish;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.jonnykea.project.service.dish.DishTestData.*;
import static ru.jonnykea.project.service.restaurant.RestaurantTestData.RESTAURANT_ID;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class DishServiceTest {

    @Autowired
    private DishService service;

    @Test
    void getById() {
        Dish actual = service.get(DISH_ID);
        DISH_MATCHER.assertMatch(actual, dishMealVillage1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
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
    void create() {
        Dish created = service.create(getNew(), RESTAURANT_ID);
        int newId = created.id();
        Dish newRest = getNew();
        newRest.setId(newId);
        DISH_MATCHER.assertMatch(created, newRest);
        DISH_MATCHER.assertMatch(service.get(newId), newRest);
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
        DISH_MATCHER.assertMatch(updated, service.get(DISH_ID));
    }

    @Test
    void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.create(getUpdated(), NOT_FOUND));
    }

    @Test
    void delete() {
        service.delete(DISH_ID);
        assertThrows(NotFoundException.class, () -> service.get(DISH_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }
}