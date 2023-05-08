package ru.javaops.topjava.service.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Restaurant;
import ru.javaops.topjava.repository.restaurant.RestaurantRepository;

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

    public List<Object[]> getAllWithMenu() {
//        List<RestaurantTo> list = repository.getRestaurantsWithMenu();
        List<Object[]> list = repository.getRestaurantsWithMenu();
        if(list.isEmpty()){
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

    public Restaurant create(Restaurant r) {
        return repository.save(r);
    }

    private List<Restaurant> getFilteredRestaurants(Predicate<Restaurant> filter) {
        return repository.getAll().stream()
                .filter(filter)
                .toList();
    }
}