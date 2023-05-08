package ru.javaops.topjava.repository.restaurant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Restaurant;
import ru.javaops.topjava.repository.BaseRepository;
import ru.javaops.topjava.to.RestaurantTo;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r WHERE r.name = LOWER(:name)")
    Optional<Restaurant> findByNameIgnoreCase(String name);

    @Query("SELECT r FROM Restaurant r ORDER BY r.registered DESC")
    List<Restaurant> getAll();

    default Restaurant getExistedByName(String name) {
        return findByNameIgnoreCase(name).orElseThrow(() -> new NotFoundException("Restaurant with name =" + name + " not found"));
    }

    @Query("""
            SELECT new ru.javaops.topjava.to.RestaurantTo(r.name,r.registered,r.description,r.address,m)
            FROM Menu m
            LEFT JOIN m.restaurant r
            WHERE r.registered = CAST(now() as date)
            """)
    List<RestaurantTo> getRestaurantsWithMenu();
}