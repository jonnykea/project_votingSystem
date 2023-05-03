package ru.javaops.topjava.repository.menu;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.restaurant.Menu;
import ru.javaops.topjava.repository.BaseRepository;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query("SELECT m FROM Menu m where m.restaurant.id=:id")
    Menu getByRestaurantId(@Param("id") int id);

    Menu getByName(String name);
}