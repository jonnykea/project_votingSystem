package ru.javaops.topjava.web.menu;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava.model.restaurant.Menu;
import ru.javaops.topjava.service.menu.MenuService;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected MenuService service;

    static final String REST_URL = "/api/restaurants/id/menu";

    @GetMapping("/{restaurantId}/with-dishes")
    public Menu getByRestaurantId(@PathVariable int restaurantId) {
        log.info("getAll with dishes with restaurantId{}", restaurantId);
        return service.getByRestaurantId(restaurantId);
    }

    @GetMapping("/{id}")
    public Menu get(@PathVariable int id) {
        log.info("get");
        return service.get(id);
    }
}