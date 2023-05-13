package ru.jonnykea.project.service.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Restaurant;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void get() {
        Restaurant actual = service.get(RestaurantTestData.RESTAURANT_ID);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(actual, RestaurantTestData.meal_village);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(RestaurantTestData.NOT_FOUND));
    }


    @Test
    void getByName() {
        Restaurant actual = service.getByName("ПЕКИН");
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(actual, RestaurantTestData.pekin);
    }

    @Test
    void geByNameNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByName("ХАРБИН"));
    }

    @Test
    void create() {
        Restaurant created = service.create(RestaurantTestData.getNew());
        int newId = created.id();
        Restaurant newRest = RestaurantTestData.getNew();
        newRest.setId(newId);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(created, newRest);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(service.get(newId), newRest);
    }

    @Test
    void createDuplicate() {
        assertThrows(DataIntegrityViolationException.class, ()
                -> service.create(new Restaurant(null, "мясная деревня", "специализация блюдо русской кухни", "ул. Партизанская, дом 56")));
    }

    @Test
    void update() {
        Restaurant updated = RestaurantTestData.getUpdated();
        service.create(updated);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(updated, service.get(RestaurantTestData.RESTAURANT_ID + 1));
    }

    @Test
    void delete() {
        service.delete(RestaurantTestData.RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> service.get(RestaurantTestData.RESTAURANT_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(RestaurantTestData.NOT_FOUND));
    }
}