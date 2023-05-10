package ru.javaops.topjava.repository.menu;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.restaurant.Menu;
import ru.javaops.topjava.repository.BaseRepository;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query(value = """
            SELECT m
            FROM Menu m
            WHERE m.created = CAST(now() as date)
              AND m.restaurant.id =:id   
            """)
    List<Menu> getByRestaurantId(int id);
}