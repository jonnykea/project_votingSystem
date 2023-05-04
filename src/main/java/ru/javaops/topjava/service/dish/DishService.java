package ru.javaops.topjava.service.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.restaurant.Dish;
import ru.javaops.topjava.repository.dish.DishRepository;
import ru.javaops.topjava.repository.restaurant.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

@Service
public class DishService {
    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public DishService(DishRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    public Dish getById(int id) {
        return repository.getExisted(id);
    }

    public List<Dish> getActualAll(int RestaurantId) {
        List<Dish> list = getFilteredByRestaurant(d -> d.getCreated().isEqual(LocalDate.now()), RestaurantId);
        return list;
    }

    public Dish getByName(String name) {
        return repository.getExistedByName(name);
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Transactional
    public Dish create(Dish dish, int restaurantId) {
        dish.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return repository.save(dish);
    }

    private List<Dish> getFilteredByRestaurant(Predicate<Dish> filter, int RestaurantId) {
        return repository.getAllByRestaurantId(RestaurantId).stream()
                .filter(filter)
                .toList();
    }
}

