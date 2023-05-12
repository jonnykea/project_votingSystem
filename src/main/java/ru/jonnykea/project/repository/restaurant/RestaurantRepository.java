package ru.jonnykea.project.repository.restaurant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.jonnykea.project.error.NotFoundException;
import ru.jonnykea.project.model.restaurant.Restaurant;
import ru.jonnykea.project.repository.BaseRepository;
import ru.jonnykea.project.to.restaurant.RestaurantTo;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r WHERE r.name = LOWER(:name)")
    Optional<Restaurant> findByNameIgnoreCase(String name);

    default Restaurant getExistedByName(String name) {
        return findByNameIgnoreCase(name).orElseThrow(() -> new NotFoundException("Restaurant with name =" + name + " not found"));
    }

    @Query("""
            SELECT new ru.jonnykea.project.to.restaurant.RestaurantTo(r.name,r.description,r.address,listagg (d.name , '; '))
            FROM Dish d
            LEFT JOIN d.restaurant r
            WHERE d.created = CAST(now() as date)
            GROUP BY r.name
            """)
    List<RestaurantTo> getRestaurantsWithMenu();
}