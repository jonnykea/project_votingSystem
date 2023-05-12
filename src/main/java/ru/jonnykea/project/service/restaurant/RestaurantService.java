package ru.jonnykea.project.service.restaurant;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Restaurant;
import ru.jonnykea.project.repository.restaurant.RestaurantRepository;
import ru.jonnykea.project.to.restaurant.RestaurantTo;

import java.util.List;

@AllArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public Restaurant get(int id) {
        return repository.getExisted(id);
    }
    @Cacheable("restaurants")
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

    @CacheEvict("restaurants")
    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @CacheEvict("restaurants")
    @Transactional
    public Restaurant create(Restaurant r) {
        if (!r.isNew()) {
            if (!repository.findById(r.getId()).isPresent()) {
                throw new NotFoundException("Restaurant with id=" + r.getId() + " not found");
            }
        }
        return repository.save(r);
    }
}