package ru.jonnykea.project.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jonnykea.project.model.restaurant.Menu;
import ru.jonnykea.project.service.restaurant.MenuService;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    protected MenuService service;
    static final String REST_URL = "/api/restaurants/id/menus";

    @GetMapping("/by-restaurantId/with-dishes/{restaurantId}")
    public Menu getByRestaurantId(@PathVariable int restaurantId) {
        log.info("getAll with dishes with restaurantId{}", restaurantId);
        return service.getByToday(restaurantId);
    }
}