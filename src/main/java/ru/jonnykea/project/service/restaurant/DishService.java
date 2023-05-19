package ru.jonnykea.project.service.restaurant;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Dish;
import ru.jonnykea.project.repository.restaurant.DishRepository;
import ru.jonnykea.project.repository.restaurant.MenuRepository;
import ru.jonnykea.project.to.restaurant.DishTo;
import ru.jonnykea.project.util.DishUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.jonnykea.project.util.validation.ValidationUtil.checkOwner;

@AllArgsConstructor
@Service
public class DishService {

    private final DishRepository repository;
    private final MenuRepository menuRepository;

    public Dish get(int id) {
        return repository.getExisted(id);
    }

    public List<Dish> getAllByDate(int restaurantId, LocalDate date) {
        List<Dish> list = repository.getAllByDate(restaurantId, date);
        if (list.isEmpty()) {
            throw new NotFoundException("Dishes with restaurant.id = " + restaurantId + " not found");
        }
        return list;
    }
    @CacheEvict(value = {"restaurants", "menu"}, allEntries = true)
    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @CacheEvict(value = {"restaurants", "menu"}, allEntries = true)
    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        dish.setMenu(menuRepository.getExisted(restaurantId));
        return repository.save(dish);
    }

    @CacheEvict(value = {"restaurants", "menu"}, allEntries = true)
    @Transactional
    public Dish update(DishTo dishTo, int restaurantId) {
        Dish dishFromData = get(dishTo.getId());
        int restaurantIdFromData = dishFromData.getMenu().getRestaurant().getId();
        checkOwner(restaurantId, restaurantIdFromData);
        Dish dish = DishUtil.updateFromTo(dishTo);
        dish.setMenu(menuRepository.getExisted(restaurantId));
        return repository.save(dish);
    }
}