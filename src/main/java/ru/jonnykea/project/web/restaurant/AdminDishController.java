package ru.jonnykea.project.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jonnykea.project.model.restaurant.Dish;
import ru.jonnykea.project.service.restaurant.DishService;
import ru.jonnykea.project.to.restaurant.DishTo;
import ru.jonnykea.project.util.DishUtil;

import java.net.URI;

import static ru.jonnykea.project.util.validation.ValidationUtil.assureIdConsistent;
import static ru.jonnykea.project.util.validation.ValidationUtil.checkNew;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {

    protected DishService dishService;
    static final String REST_URL = "/api/admin/dishes";

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish with id{}", id);
        dishService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody DishTo dishTo) {
        log.info("create dish {}", dishTo);
        checkNew(dishTo);
        int restaurantId = dishTo.getRestaurantId();
        Dish created = dishService.save(DishUtil.createNewFromTo(dishTo), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("update {} with id={}", dishTo, id);
        assureIdConsistent(dishTo, id);
        dishTo.setId(id);
        int restaurantId = dishTo.getRestaurantId();
        dishService.update(dishTo, restaurantId);
    }
}