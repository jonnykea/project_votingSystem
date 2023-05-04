package ru.javaops.topjava.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.restaurant.Menu;
import ru.javaops.topjava.repository.menu.MenuRepository;
import ru.javaops.topjava.repository.restaurant.RestaurantRepository;
import ru.javaops.topjava.service.dish.DishService;

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

    public Menu getById(int id) {
        return repository.getExisted(id);
    }

    public Menu getByRestaurantId(int id) {
        return repository.getByRestaurantId(id);
    }

    public Menu getByRestaurantIdWithDishes(int restaurantId) {
        Menu menu = repository.getByRestaurantId(restaurantId);
        menu.setDishes(dishService.getActualAll(restaurantId));
        return menu;
    }

    public Menu getByName(String name) {
        return repository.getExistedByName(name);
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        menu.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return repository.save(menu);
    }

    @Transactional
    public Menu createWithDishes(Menu menu, int restaurantId) {
        menu.setDishes(dishService.getActualAll(restaurantId));
        return repository.save(menu);
    }
}