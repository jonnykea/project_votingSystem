package ru.jonnykea.project.service.menu;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Menu;
import ru.jonnykea.project.repository.menu.MenuRepository;
import ru.jonnykea.project.repository.restaurant.RestaurantRepository;
import ru.jonnykea.project.service.dish.DishService;

import java.util.List;

@AllArgsConstructor
@Service
public class MenuService {

    private final MenuRepository repository;
    private final DishService dishService;
    private final RestaurantRepository restaurantRepository;

    public Menu get(int id) {
        return repository.getExisted(id);
    }

    @Cacheable("menu")
    public Menu getByRestaurantId(int restaurantId) {
        List<Menu> list = repository.getByRestaurantId(restaurantId);
        Menu menu = list.stream().
                findFirst().
                orElseThrow(() -> new NotFoundException("Menu with restaurant.id = " + restaurantId + " not found"));
        menu.setDishes(dishService.getActualAll(restaurantId));
        return menu;
    }
    @CacheEvict("menu")
    public void delete(int id) {
        repository.deleteExisted(id);
    }
    @CacheEvict("menu, restaurants")
    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        menu.setDishes(dishService.getActualAll(restaurantId));
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return repository.save(menu);
    }
}