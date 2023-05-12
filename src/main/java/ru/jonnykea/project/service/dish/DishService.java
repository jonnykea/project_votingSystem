package ru.jonnykea.project.service.dish;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Dish;
import ru.jonnykea.project.repository.dish.DishRepository;
import ru.jonnykea.project.repository.restaurant.RestaurantRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class DishService {

    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    public Dish get(int id) {
        return repository.getExisted(id);
    }

    public List<Dish> getActualAll(int restaurantId) {
        List<Dish> list = repository.getAllByRestaurantId(restaurantId);
        if (list.isEmpty()) {
            throw new NotFoundException("Dishes with restaurant.id = " + restaurantId + " not found");
        }
        return list;
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Transactional
    public Dish create(Dish dish, int restaurantId) {
        dish.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return repository.save(dish);
    }
}