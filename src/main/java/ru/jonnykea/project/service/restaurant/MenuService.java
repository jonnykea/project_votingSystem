package ru.jonnykea.project.service.restaurant;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Menu;
import ru.jonnykea.project.repository.restaurant.MenuRepository;
import ru.jonnykea.project.repository.restaurant.RestaurantRepository;
import ru.jonnykea.project.to.restaurant.MenuTo;
import ru.jonnykea.project.util.MenuUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.jonnykea.project.util.validation.ValidationUtil.checkOwner;

@AllArgsConstructor
@Service
public class MenuService {

    private final MenuRepository repository;
    private final DishService dishService;
    private final RestaurantRepository restaurantRepository;

    public Menu get(int id) {
        return repository.getExisted(id);
    }

    @Cacheable(value = "menu", key = "#restaurantId")
    public Menu getByToday(int restaurantId) {
        List<Menu> list = repository.getByRestaurantId(restaurantId);
        Menu menu = list.stream()
                .filter(m -> m.getCreated().isEqual(LocalDate.now()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Menu with restaurant.id = " + restaurantId + " not found"));
        menu.setDishes(dishService.getAllByDate(restaurantId, LocalDate.now()));
        return menu;
    }

    @CacheEvict(value = "menu", allEntries = true)
    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @CachePut(value = "menu", key = "#restaurantId")
    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        menu.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return repository.save(menu);
    }

    @CachePut(value = "menu", key = "#restaurantId")
    @Transactional
    public Menu update(MenuTo menuTo, int restaurantId) {
        Menu menuFromData = get(menuTo.getId());
        int restaurantIdFromData = menuFromData.getRestaurant().getId();
        checkOwner(restaurantId, restaurantIdFromData);
        Menu menu = MenuUtil.updateFromTo(menuTo);
        menu.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return repository.save(menu);
    }
}