package ru.javaops.topjava.repository.menu;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Menu;
import ru.javaops.topjava.repository.BaseRepository;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:id")
    Optional<Menu> findByRestaurantId(int id);

    default Menu getByRestaurantId(int id) {
        return findByRestaurantId(id).orElseThrow(() -> new NotFoundException("Menu with restaurant.id = " + id + " not found"));
    }
}