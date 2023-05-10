package ru.javaops.topjava.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Menu;
import ru.javaops.topjava.repository.menu.MenuRepository;
import ru.javaops.topjava.repository.restaurant.RestaurantRepository;
import ru.javaops.topjava.service.dish.DishService;

import java.util.List;

@Service
public class MenuService {
    private final MenuRepository repository;
    private final DishService dishService;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuService(MenuRepository repository, DishService dishService, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.dishService = dishService;
        this.restaurantRepository = restaurantRepository;
    }

    public Menu get(int id) {
        return repository.getExisted(id);
    }

    public Menu getByRestaurantId(int restaurantId) {
        List<Menu> list = repository.getByRestaurantId(restaurantId);
        Menu menu = list.stream().
                findFirst().
                orElseThrow(() -> new NotFoundException("Menu with restaurant.id = " + restaurantId + " not found"));
        menu.setDishes(dishService.getActualAll(restaurantId));
        return menu;
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        menu.setDishes(dishService.getActualAll(restaurantId));
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return repository.save(menu);
    }
}