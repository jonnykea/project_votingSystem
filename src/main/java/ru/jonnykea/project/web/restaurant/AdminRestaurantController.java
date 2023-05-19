package ru.jonnykea.project.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jonnykea.project.model.restaurant.Restaurant;
import ru.jonnykea.project.service.restaurant.RestaurantService;
import ru.jonnykea.project.to.restaurant.RestaurantToFrom;
import ru.jonnykea.project.util.RestaurantUtil;

import java.net.URI;
import java.util.List;

import static ru.jonnykea.project.util.validation.ValidationUtil.assureIdConsistent;
import static ru.jonnykea.project.util.validation.ValidationUtil.checkNew;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    protected RestaurantService service;
    static final String REST_URL = "/api/admin/restaurants";

    @GetMapping()
    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody RestaurantToFrom restaurantToFrom) {
        log.info("create {}", restaurantToFrom);
        checkNew(restaurantToFrom);
        Restaurant created = service.save(RestaurantUtil.createNewFromTo(restaurantToFrom));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantToFrom restaurantToFrom, @PathVariable int id) {
        log.info("update {} with id={}", restaurantToFrom, id);
        assureIdConsistent(restaurantToFrom, id);
        service.save(RestaurantUtil.updateFromTo(restaurantToFrom));
    }
}