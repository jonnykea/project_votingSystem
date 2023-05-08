package ru.javaops.topjava.web.restaurant;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava.model.restaurant.Restaurant;
import ru.javaops.topjava.repository.vote.VoteRepository;
import ru.javaops.topjava.service.restaurant.RestaurantService;
import ru.javaops.topjava.to.VoteToRating;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javaops.topjava.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    protected final Logger log = getLogger(getClass());

    @Autowired
    protected RestaurantService service;

    @Autowired
    protected VoteRepository voteRepository;

    static final String REST_URL = "/api/restaurant";

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

/*    @GetMapping("/all with-menu")
    public List<RestaurantTo> getWithMenu() {
        log.info("getAll with menu");
        return service.getAllWithMenu();
    }*/

    @GetMapping("/all")
    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getActualAll().stream()
                .sorted(Comparator.comparing(Restaurant::getName))
                .toList();
    }

    @GetMapping("/all/with-rating")
    public List<VoteToRating> getAllWithRating() {
        log.info("getAll");
        return voteRepository.getRestaurantRating();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = service.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        service.create(restaurant);
    }
}