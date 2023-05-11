package ru.javaops.topjava.service.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Restaurant;
import ru.javaops.topjava.to.restaurant.RestaurantTo;

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
        RESTAURANT_MATCHER.assertMatch(actual, meal_village);
    }

    @Test
    void getWithMenu() {
        List<RestaurantTo> list = service.getAllWithMenu();
        List<RestaurantTo> list1 = service.getAllWithMenu();
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getActualAll() {
        List<Restaurant> actual = service.getActualAll();
        RESTAURANT_MATCHER.assertMatch(actual, restaurants);
    }

    @Test
    void getByName() {
        Restaurant actual = service.getByName("ПЕКИН");
        RESTAURANT_MATCHER.assertMatch(actual, pekin);
    }

    @Test
    void geByNameNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByName("ХАРБИН"));
    }

    @Test
    void create() {
        Restaurant created = service.create(getNew());
        int newId = created.id();
        Restaurant newRest = getNew();
        newRest.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRest);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRest);
    }

    @Test
    void createDuplicate() {
        assertThrows(DataIntegrityViolationException.class, ()
                -> service.create(new Restaurant(null, "мясная деревня", "специализация блюдо русской кухни", "ул. Партизанская, дом 56")));
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.create(updated);
        RESTAURANT_MATCHER.assertMatch(updated, service.get(RESTAURANT_ID + 1));
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }
}