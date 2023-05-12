package ru.jonnykea.project.service.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Restaurant;
import ru.jonnykea.project.repository.restaurant.RestaurantRepository;
import ru.jonnykea.project.to.restaurant.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

@Service
public class RestaurantService {
    private final RestaurantRepository repository;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant get(int id) {
        return repository.getExisted(id);
    }

    public List<Restaurant> getActualAll() {
        List<Restaurant> list = getFilteredRestaurants(r -> r.getRegistered().isEqual(LocalDate.now()));
        return list;
    }

    public List<RestaurantTo> getAllWithMenu() {
        List<RestaurantTo> list = repository.getRestaurantsWithMenu();
        if (list.isEmpty()) {
            throw new NotFoundException("Restaurants not found");
        }
        return repository.getRestaurantsWithMenu();
    }

    public Restaurant getByName(String name) {
        return repository.getExistedByName(name);
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Transactional
    public Restaurant create(Restaurant r) {
        if (!r.isNew()) {
            if (!repository.findById(r.getId()).isPresent()) {
                throw new NotFoundException("Restaurant with id=" + r.getId() + " not found");
            }
        }
        return repository.save(r);
    }

    private List<Restaurant> getFilteredRestaurants(Predicate<Restaurant> filter) {
        return repository.getAll().stream()
                .filter(filter)
                .toList();
    }
}