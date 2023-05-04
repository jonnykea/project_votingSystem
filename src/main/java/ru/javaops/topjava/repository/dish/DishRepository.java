package ru.javaops.topjava.repository.dish;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Dish;
import ru.javaops.topjava.model.restaurant.Restaurant;
import ru.javaops.topjava.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.name = LOWER(:name)")
    Optional<Dish> findByNameIgnoreCase(String name);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:id order by d.name")
    List<Dish> getAllByRestaurantId(int id);

    default Dish getExistedByName(String name) {
        return findByNameIgnoreCase(name).orElseThrow(() -> new NotFoundException("Dish with name = " + name + " not found"));
    }
}