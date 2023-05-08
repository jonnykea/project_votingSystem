package ru.javaops.topjava.repository.restaurant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Restaurant;
import ru.javaops.topjava.repository.BaseRepository;

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
//(CAST(d.price as STRING ))
    @Query("""
            SELECT  r.name,r.description,r.address,listagg (CAST(d.name as STRING), CAST(r.name as STRING ))
            
            FROM Dish d
            LEFT JOIN d.restaurant r
            WHERE d.created = CAST(now() as date)
            """)
    List<Object[]> getRestaurantsWithMenu();
//    List<RestaurantTo> getRestaurantsWithMenu();
}