package ru.javaops.topjava.repository.menu;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.restaurant.Menu;
import ru.javaops.topjava.repository.BaseRepository;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Override
    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:id")
    List<Menu> getAllByRestaurantId(int id);


    @Query(value = """
            select * FROM Menu m
                     left join Dish d on m.RESTAURANT_ID= d.RESTAURANT_ID
            WHERE m.DATE = CAST(now() as date)
            group by m.NAME
            """, nativeQuery = true)
    List<Menu> getAllByRestaurantIdWithDishes(int id);
}