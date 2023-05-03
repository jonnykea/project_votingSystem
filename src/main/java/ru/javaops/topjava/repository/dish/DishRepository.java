package ru.javaops.topjava.repository.dish;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.restaurant.Dish;
import ru.javaops.topjava.repository.BaseRepository;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d where d.restaurant.id=:id order by d.name")
    List<Dish> getAllByRestaurantId(@Param("id") int id);

    Dish getByName(String name);
}