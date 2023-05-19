package ru.jonnykea.project.service.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Dish;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.jonnykea.project.service.restaurant.DishTestData.*;
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
        List<Dish> actual = service.getAllByDate(RESTAURANT_ID, LocalDate.now());
        DISH_MATCHER.assertMatch(actual, dishesMealVillage);
    }

    @Test
    void getActualAllNotExisted() {
        assertThrows(NotFoundException.class, () -> service.getAllByDate(NOT_FOUND, LocalDate.now()));
    }

    @Test
    void create() {
        Dish created = service.save(getNew(), RESTAURANT_ID);
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.save(updated, RESTAURANT_ID);
        DISH_MATCHER.assertMatch(updated, service.get(DISH_ID));
    }

    @Test
    void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.save(getUpdated(), NOT_FOUND));
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