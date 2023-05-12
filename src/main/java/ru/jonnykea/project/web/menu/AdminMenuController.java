package ru.jonnykea.project.web.menu;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jonnykea.project.model.restaurant.Dish;
import ru.jonnykea.project.model.restaurant.Menu;
import ru.jonnykea.project.service.dish.DishService;
import ru.jonnykea.project.service.menu.MenuService;
import ru.jonnykea.project.to.restaurant.DishTo;
import ru.jonnykea.project.to.restaurant.MenuTo;
import ru.jonnykea.project.util.DishUtil;
import ru.jonnykea.project.util.MenuUtil;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.jonnykea.project.util.validation.ValidationUtil.*;

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
    public void deleteMenu(@PathVariable int id) {
        log.info("delete menu with id{}", id);
        service.delete(id);
    }

    @DeleteMapping("/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable int id) {
        log.info("delete dish with id{}", id);
        dishService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createMenu(@Valid @RequestBody MenuTo menuTo) {
        log.info("create menu {}", menuTo);
        checkNew(menuTo);
        int restaurantId = menuTo.getRestaurantId();
        Menu created = service.create(MenuUtil.createNewFromTo(menuTo), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDish(@Valid @RequestBody DishTo dishTo) {
        log.info("create dish {}", dishTo);
        checkNew(dishTo);
        int restaurantId = dishTo.getRestaurantId();
        Dish created = dishService.create(DishUtil.createNewFromTo(dishTo), restaurantId);
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
        int restaurantIdFrom = menuTo.getRestaurantId();
        Menu menuInside = service.get(id);
        int restaurantIdInside = menuInside.getRestaurant().getId();
        checkOwner(restaurantIdFrom, restaurantIdInside);
        service.create(MenuUtil.updateFromTo(menuTo), restaurantIdFrom);
    }

    @Transactional
    @PutMapping(value = "/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDish(@Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("update {} with id={}", dishTo, id);
        assureIdConsistent(dishTo, id);
        int restaurantIdFrom = dishTo.getRestaurantId();
        Dish dishInside = dishService.get(id);
        int restaurantIdInside = dishInside.getRestaurant().getId();
        checkOwner(restaurantIdFrom, restaurantIdInside);
        dishService.create(DishUtil.updateFromTo(dishTo), restaurantIdFrom);
    }
}