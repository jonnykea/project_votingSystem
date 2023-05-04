package ru.javaops.topjava.repository.menu;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.error.NotFoundException;
import ru.javaops.topjava.model.restaurant.Dish;
import ru.javaops.topjava.model.restaurant.Menu;
import ru.javaops.topjava.repository.BaseRepository;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query("SELECT m FROM Menu m WHERE m.name = LOWER(:name)")
    Optional<Menu> findByNameIgnoreCase(String name);

    @Query("SELECT m FROM Menu m WHERE m.id=:id")
    Optional<Menu> findByByRestaurantId(int id);

    default Menu getByRestaurantId(int id) {
        return findByByRestaurantId(id).orElseThrow(() -> new NotFoundException("Menu with restaurant.id = " + id + " not found"));
    }

    default Menu getExistedByName(String name) {
        return findByNameIgnoreCase(name).orElseThrow(() -> new NotFoundException("Menu with name = " + name + " not found"));
    }
}