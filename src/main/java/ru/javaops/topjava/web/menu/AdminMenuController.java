package ru.javaops.topjava.web.menu;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava.model.restaurant.Dish;
import ru.javaops.topjava.model.restaurant.Menu;
import ru.javaops.topjava.service.dish.DishService;
import ru.javaops.topjava.service.menu.MenuService;
import ru.javaops.topjava.to.DishTo;
import ru.javaops.topjava.to.MenuTo;
import ru.javaops.topjava.util.DishUtil;
import ru.javaops.topjava.util.MenuUtil;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javaops.topjava.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected MenuService service;

    @Autowired
    protected DishService dishService;
    static final String REST_URL = "/api/admin/restaurants/id/menu";

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createMenu(@Valid @RequestBody MenuTo menuTo) {
        log.info("create {}", menuTo);
        checkNew(menuTo);
        int restaurantId = menuTo.getRestaurantId();
        Menu created = service.create(MenuUtil.createNewFromTo(menuTo), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
    @Transactional
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenu(@Valid @RequestBody MenuTo menuTo, @PathVariable int id) {
        log.info("update {} with id={}", menuTo, id);
        assureIdConsistent(menuTo, id);
        int restaurantId = menuTo.getRestaurantId();
        Menu menuForCheck = service.get(id);
        int restaurantIdMenuForUpdate = menuForCheck.getRestaurant().getId();
        checkOwner(restaurantId, restaurantIdMenuForUpdate);
        service.create(MenuUtil.updateFromTo(menuTo), restaurantId);
    }

    @PostMapping(value = "/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDish(@Valid @RequestBody DishTo dishTo) {
        log.info("create {}", dishTo);
        checkNew(dishTo);
        int restaurantId = dishTo.getRestaurantId();
        Dish created = dishService.create(DishUtil.createNewFromTo(dishTo), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Transactional
    @PutMapping(value = "/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDish(@Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("update {} with id={}", dishTo, id);
        assureIdConsistent(dishTo, id);
        int restaurantId = dishTo.getRestaurantId();
        Dish dishForCheck = dishService.get(id);
        int restaurantIdDishForUpdate = dishForCheck.getRestaurant().getId();
        checkOwner(restaurantId, restaurantIdDishForUpdate);
        dishService.create(DishUtil.updateFromTo(dishTo), restaurantId);
    }
}