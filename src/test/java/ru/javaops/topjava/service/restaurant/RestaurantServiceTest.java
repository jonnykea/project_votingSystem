package ru.javaops.topjava.service.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.DataConflictException;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Restaurant;
import ru.javaops.topjava.service.RestaurantService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaops.topjava.service.restaurant.RestaurantTestData.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void get() {
        Restaurant actual = service.get(RESTAURANT_ID);
        Restaurant_MATCHER.assertMatch(actual, meal_village);
    }

    @Test
    void getAll() {
        List<Restaurant> actual = service.getAll();
        Restaurant_MATCHER.assertMatch(actual, restaurants);
    }

    @Test
    void getByName() {
        Restaurant actual = service.getByName("ПЕКИН");
        Restaurant_MATCHER.assertMatch(actual, harbin);
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_ID);
        assertThrows(DataConflictException.class, () -> service.get(RESTAURANT_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        Restaurant_MATCHER.assertMatch(updated, harbin);
    }
}