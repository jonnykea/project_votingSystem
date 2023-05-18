package ru.jonnykea.project.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jonnykea.project.model.restaurant.Restaurant;
import ru.jonnykea.project.service.restaurant.RestaurantService;
import ru.jonnykea.project.to.restaurant.RestaurantTo;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    protected RestaurantService service;

    static final String REST_URL = "/api/restaurants";

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    @GetMapping("/by-name{name}")
    public Restaurant get(@PathVariable String name) {
        log.info("getByName {}", name);
        return service.getByName(name);
    }

    @GetMapping("/with-dishes")
    public List<RestaurantTo> getWithDishes() {
        log.info("getAll with menu");
        return service.getAllWithMenuToday();
    }
}