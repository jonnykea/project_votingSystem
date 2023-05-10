package ru.javaops.topjava.web.menu;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava.model.restaurant.Menu;
import ru.javaops.topjava.service.menu.MenuService;
import ru.javaops.topjava.to.MenuTo;
import ru.javaops.topjava.util.MenuUtil;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javaops.topjava.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected MenuService service;
    static final String REST_URL = "/api/admin/restaurants/id/menu";

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody MenuTo menuTo) {
        log.info("create {}", menuTo);
        checkNew(menuTo);
        int restaurantId = menuTo.getRestaurantId();
        Menu created = service.create(MenuUtil.createNewFromTo(menuTo), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable int id) {
        log.info("update {} with id={}", menuTo, id);
        assureIdConsistent(menuTo, id);
        int restaurantId = menuTo.getRestaurantId();
        service.create(MenuUtil.updateFromTo(menuTo), restaurantId);
    }
}