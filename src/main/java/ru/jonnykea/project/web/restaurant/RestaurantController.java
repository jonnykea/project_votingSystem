package ru.jonnykea.project.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jonnykea.project.model.restaurant.Restaurant;
import ru.jonnykea.project.service.restaurant.RestaurantService;
import ru.jonnykea.project.to.restaurant.RestaurantTo;

import java.util.Comparator;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected RestaurantService service;

    static final String REST_URL = "/api/restaurants";

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    @GetMapping("/by-name")
    public Restaurant get(@PathVariable String name) {
        log.info("getByName {}", name);
        return service.getByName(name);
    }

    @GetMapping("/with-dishes")
    public List<RestaurantTo> getWithDishes() {
        log.info("getAll with menu");
        return service.getAllWithMenu();
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getActualAll().stream()
                .sorted(Comparator.comparing(Restaurant::getName))
                .toList();
    }
}