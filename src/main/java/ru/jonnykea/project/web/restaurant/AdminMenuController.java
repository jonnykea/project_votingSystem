package ru.jonnykea.project.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jonnykea.project.model.restaurant.Menu;
import ru.jonnykea.project.service.restaurant.MenuService;
import ru.jonnykea.project.to.restaurant.MenuTo;
import ru.jonnykea.project.util.MenuUtil;

import java.net.URI;

import static ru.jonnykea.project.util.validation.ValidationUtil.assureIdConsistent;
import static ru.jonnykea.project.util.validation.ValidationUtil.checkNew;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {

    protected MenuService service;
    static final String REST_URL = "/api/admin/menus";

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menu with id{}", id);
        service.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> create(@Valid @RequestBody MenuTo menuTo) {
        log.info("create menu {}", menuTo);
        checkNew(menuTo);
        int restaurantId = menuTo.getRestaurantId();
        Menu created = service.save(MenuUtil.createNewFromTo(menuTo), restaurantId);
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
        menuTo.setId(id);
        int restaurantId = menuTo.getRestaurantId();
        service.update(menuTo, restaurantId);
    }
}