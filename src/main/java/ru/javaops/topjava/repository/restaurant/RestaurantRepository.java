package ru.javaops.topjava.repository.restaurant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.restaurant.Restaurant;
import ru.javaops.topjava.repository.BaseRepository;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r ORDER BY r.registered DESC")
    List<Restaurant> getAll();

    Restaurant getByName(String name);
}