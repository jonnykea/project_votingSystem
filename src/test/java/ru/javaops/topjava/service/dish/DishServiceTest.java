package ru.javaops.topjava.service.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Dish;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.topjava.service.dish.DishTestData.*;
import static ru.javaops.topjava.service.restaurant.RestaurantTestData.NOT_FOUND;
import static ru.javaops.topjava.service.restaurant.RestaurantTestData.RESTAURANT_ID;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class DishServiceTest {

    @Autowired
    private DishService service;

    @Test
    void get() {
        Dish actual = service.get(DISH_ID);
        Dish_MATCHER.assertMatch(actual, dishMealVillage1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getActualAll() {
        List<Dish> actual = service.getActualAll(RESTAURANT_ID);
        Dish_MATCHER.assertMatch(actual, dishesMealVillage);
    }

    @Test
    void getByName() {
        Dish actual = service.getByName("суп");
        Dish_MATCHER.assertMatch(actual, dishMealVillage1);
    }

    @Test
    void create() {
        Dish created = service.create(getNew(), RESTAURANT_ID);
        int newId = created.id();
        Dish newRest = getNew();
        newRest.setId(newId);
        Dish_MATCHER.assertMatch(created, newRest);
        Dish_MATCHER.assertMatch(service.get(newId), newRest);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.create(updated, RESTAURANT_ID);
        Dish_MATCHER.assertMatch(updated, service.get(DISH_ID));
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